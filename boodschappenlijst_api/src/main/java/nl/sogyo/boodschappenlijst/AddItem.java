package nl.sogyo.boodschappenlijst;

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


@Path("add")
public class AddItem {
	
	/**
	 * @param request
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItem(
			@Context HttpServletRequest request,
			Item add) {
		HttpSession session = request.getSession(false);
		System.out.println(add.getName() + ", " + add.getAmount() + ", " + add.getBought() + ", " + add.getShop());
		//if (session != null) {
		//	user = (User) session.getAttribute("user");
		//} else {
		//	return Response.status(401, "Session has timed-out, please log in again.").build();
		//}
		User user = Connect2SQL.LogIn("Test", "Test");
		System.out.println("The usercode " + user.getUserCode() + " belongs to the user " + user.getName());
		Connect2SQL.setItem(user, add);
		ArrayList<Item> items = Connect2SQL.getItems(user);
		if (items.size() < 2) {
			System.out.println("No items found.");
		}
		for (Item item : items) {
			System.out.println(item.getName() + ", " + item.getAmount() + ", " + item.getBought() + ", " + item.getShop());
		}
		String output = RowData.ItemArrayToJSON(items);
		return Response.status(200).entity(output).build();
	}
	
}
