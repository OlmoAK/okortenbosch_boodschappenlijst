package nl.sogyo.boodschappenlijst;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems(@Context HttpServletRequest request) {
    	HttpSession session = request.getSession(true);
		User user = Connect2SQL.LogIn("Test", "Test");
		System.out.println("The usercode " + user.getUserCode() + " belongs to the user " + user.getName());
		ArrayList<Item> items = Connect2SQL.getItems(user);
		if (items.size() < 2) {
			System.out.println("No items found.");
		}
		for (int i = 0; i < items.size(); i++) {
			System.out.println(items.get(i).getName() + ", " + items.get(i).getAmount() + ", " + items.get(i).getBought() + ", " + items.get(i).getShop());
		}
		session.setAttribute("user", user);
		String output = RowData.ItemArrayToJSON(items);
		return Response.status(200).entity(output).build();
    }
}
