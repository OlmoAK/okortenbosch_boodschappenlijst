package nl.sogyo.boodschappenlijst;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("items")
public class RowData {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRows(
			@Context HttpServletRequest request,
			User login) {
		HttpSession session = request.getSession(true);
		User user = Connect2SQL.LogIn(login.getName(), login.getPassword());
		ArrayList<Item> items = Connect2SQL.getItems(user);
		session.setAttribute("items", items);
		String output = ItemArrayToJSON(items);
		return Response.status(200).entity(output).build();
	}
	
	public static String ItemArrayToJSON(ArrayList<Item> items) {
		JSONArray rowData = new JSONArray();
		for (Item item : items) {
			rowData.add(ItemToJSON(item));
		}
		rowData.add(ItemToJSON(new Item()));
		System.out.println(rowData.toJSONString());
		return rowData.toJSONString();
	}
	
	public static JSONObject ItemToJSON(Item item) {
		JSONObject itemJSON = new JSONObject();
		itemJSON.put("name", item.getName());
		itemJSON.put("amount", item.getAmount());
		itemJSON.put("bought", item.getBought());
		itemJSON.put("shop", item.getShop());
		itemJSON.put("itemCode", item.getItemCode());
		System.out.println(itemJSON);
		return itemJSON;
	}
	
}