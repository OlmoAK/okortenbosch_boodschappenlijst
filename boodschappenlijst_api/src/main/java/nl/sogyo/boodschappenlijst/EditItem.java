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


@Path("edit")
public class EditItem {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editItem(
			@Context HttpServletRequest request,
			Item edit) {
		HttpSession session = request.getSession(false);
		//if (session != null) {
		//	user = (User) session.getAttribute("user");
		//} else {
		//	return Response.status(401, "Session has timed-out, please log in again.").build();
		//}
		System.out.println(edit.getName() + ", " + edit.getAmount() + ", " + edit.getBought() + ", " + edit.getShop());
		User user = Connect2SQL.LogIn("Test", "Test");
		System.out.println("The usercode " + user.getUserCode() + " belongs to the user " + user.getName());	
		Connect2SQL.updateItem(user, edit);
		ArrayList<Item> items = Connect2SQL.getItems(user);
		if (items.size() < 2) {
			System.out.println("No items found.");
		}
		for (int i = 0; i < items.size(); i++) {
			System.out.println(items.get(i).getName() + ", " + items.get(i).getAmount() + ", " + items.get(i).getBought() + ", " + items.get(i).getShop());
		}
		String output = RowData.ItemArrayToJSON(items);
		return Response.status(200).entity(output).build();
	}
	
}
