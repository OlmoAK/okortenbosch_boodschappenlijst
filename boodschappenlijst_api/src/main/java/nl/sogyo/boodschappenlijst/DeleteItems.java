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


@Path("delete")
public class DeleteItems {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteItems(
			@Context HttpServletRequest request,
			ArrayList<Item> delete) {
		HttpSession session = request.getSession(false);
		//if (session != null) {
		//	user = (User) session.getAttribute("user");
		//} else {
		//	return Response.status(401, "Session has timed-out, please log in again.").build();
		//}
		User user = Connect2SQL.LogIn("Test", "Test");
		System.out.println("The usercode " + user.getUserCode() + " belongs to the user " + user.getName());
		for (Item item : delete) {
			if (item.getItemCode() == 0) {
				System.out.println("You tried to delete the empty item. This item is here for adding new items to the list and cannot be removed.");
			} else {
				Connect2SQL.removeItem(user, item);
			}
		}
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
