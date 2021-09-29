package nl.sogyo.boodschappenlijst;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Connect2SQL {
	
	public static void main(String[] args) {
	}
	
	public static void removeItem(User user, Item item) {
		removeItem(user, item.getName(), item.getAmount(), item.getShop(), item.getItemCode());
	}
	
	public static void removeItem(User user, String name, int number, int itemcode) {
		removeItem(user, name, number, "", itemcode);
	}
	
	public static void removeItem(User user, String name, int number, String shop, int itemcode) {
		try {
			Connection connection = DatabaseConnection();
			
			String sql = "DELETE FROM Items"
					+ " WHERE ItemName = '" + name + "' AND ItemNumber = '" + number + "' AND ShopName = '" + shop + "' AND UserCode = '" + user.getUserCode() + "'"; 
			
			Statement statement = connection.createStatement();
			
			int rows = statement.executeUpdate(sql);
			
			if (rows == 1) {
				System.out.println("Item has been removed from the database!");
			} else {
				System.out.println("Something went wrong.");
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("Something went wrong.");
			e.printStackTrace();
		}
	}
	
	public static Item setBought(User user, Item item, int number) {
		return setBought(user, item.getName(), number, item.getShop(), item.getItemCode());
	}
	
	public static Item setBought(User user, String name, int number, int itemcode) {
		return setBought(user, name, number, "", itemcode);
	}
	
	public static Item setBought(User user, String name, int number, String shop, int itemcode) {
		int amount = 0;
		int bought = 0;
		try {
			Connection connection = DatabaseConnection();
			
			String sql = "UPDATE Items"
					+ " SET BoughtNumber = BoughtNumber - (-" + number + ")"
					+ " WHERE ItemName = '" + name + "' AND ShopName = '" + shop + "' AND UserCode = '" + user.getUserCode() + "' AND ItemCode = '" + itemcode + "'"; 
			
			Statement statement = connection.createStatement();
			
			int rows = statement.executeUpdate(sql);
			
			if (rows == 1) {
				System.out.println("Item has been updated in the database!");
			} else {
				System.out.println("Something went wrong.");
			}
			
			sql = "SELECT Items.ItemNumber, Items.BoughtNumber"
					+ " FROM Items"
					+ " WHERE ItemName = '" + name + "' AND ShopName = '" + shop + "' AND UserCode = '" + user.getUserCode() + "'";
			
			Statement query = connection.createStatement();
			ResultSet result = query.executeQuery(sql);
			
			while(result.next()) {
				amount = result.getInt("ItemNumber");
				bought = result.getInt("BoughtNumber");
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("Something went wrong.");
			e.printStackTrace();
		}
		Item item = new Item();
		item.setName(name);
		item.setAmount(amount);
		item.setBought(bought);
		item.setShop(shop);
		item.setItemCode(itemcode);
		return item;
	}
	
	public static Item updateItem(User user, Item item) {
		return updateItem(user, item.getName(), item.getAmount(), item.getBought(), item.getShop(), item.getItemCode());
	}
	
	public static Item updateItem(User user, String name, int number, int bought, String shop, int itemcode) {
		try {
			Connection connection = DatabaseConnection();
			
			String sql = "UPDATE Items"
					+ " SET ItemName = '" + name + "', ItemNumber = '" + number + "', ShopName = '" + shop + "', BoughtNumber = '" + bought + "'"
					+ " WHERE ItemCode = '" + itemcode + "' AND UserCode = '" + user.getUserCode() + "'";
			
			Statement statement = connection.createStatement();
			
			int rows = statement.executeUpdate(sql);
			
			if (rows == 1) {
				System.out.println("Item has been updated in the database!");
			} else {
				System.out.println("Something went wrong.");
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("Something went wrong.");
			e.printStackTrace();
		}
		Item item = new Item();
		item.setName(name);
		item.setAmount(number);
		item.setBought(bought);
		item.setShop(shop);
		item.setItemCode(itemcode);
		return item;
	}
	
	public static Item setItem(User user, Item item) {
		return setItem(user, item.getName(), item.getAmount(), item.getShop());
	}
	
	public static Item setItem(User user, String name, int number) {
		return setItem(user, name, number, "");
	}
	
	public static Item setItem(User user, String name, int number, String shop) {
		int itemcode = 0;
		try {
			Connection connection = DatabaseConnection();
			
			String sql = "INSERT INTO Items (ItemName, ItemNumber, ShopName, BoughtNumber, UserCode)"
					+ " VALUES (?, ?, ?, ?, ?)"; 
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			statement.setInt(2, number);
			statement.setString(3, shop);
			statement.setInt(4, 0);
			statement.setInt(5, user.getUserCode());
			
			int rows = statement.executeUpdate();
			
			if (rows == 1) {
				System.out.println("Item has been added to the database!");
			} else {
				System.out.println("Something went wrong.");
			}
			
			sql = "SELECT ItemCode"
					+ " FROM Items"
					+ " WHERE ItemName = '" + name + "' AND ItemNumber = '" + number + "' AND ShopName = '" + shop + "' AND BoughtNumber = '0' AND UserCode = '" + user.getUserCode() + "'";
			
			Statement query = connection.createStatement();
			ResultSet result = query.executeQuery(sql);
			
			while(result.next()) {
				itemcode = result.getInt("ItemCode");
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("Something went wrong.");
			e.printStackTrace();
		}
		Item item = new Item();
		item.setName(name);
		item.setAmount(number);
		item.setBought(0);
		item.setShop(shop);
		item.setItemCode(itemcode);
		return item;
	}
	
	public static ArrayList<Item> getItems(User user) {
		ArrayList<Item> Items = new ArrayList<Item>();
		try {
			Connection connection = DatabaseConnection();
			
			String sql = "SELECT *"
					+ " FROM Items"
					+ " WHERE Items.UserCode = '" + user.getUserCode() + "'";
			
			Statement query = connection.createStatement();
			ResultSet result = query.executeQuery(sql);
			
			while(result.next()) {
				Item item = new Item();
				String name = result.getString("ItemName");
				item.setName(name);
				int amount = result.getInt("ItemNumber");
				item.setAmount(amount);
				String shop = result.getString("ShopName");
				item.setShop(shop);
				int bought = result.getInt("BoughtNumber");
				item.setBought(bought);
				int itemcode = result.getInt("ItemCode");
				item.setItemCode(itemcode);
				Items.add(item);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("Something went wrong.");
			e.printStackTrace();
		}
		return Items;
	}
	
//	public static String getUserName(int usercode) {
//		String name = null;
//		try {
//			Connection connection = DatabaseConnection();
//			
//			String sql = "SELECT Users.UserName"
//					+ " FROM Users"
//					+ " WHERE Users.UserCode = '" + usercode + "'";
//			
//			Statement query = connection.createStatement();
//			ResultSet result = query.executeQuery(sql);
//			
//			while(result.next()) {
//				name = result.getString("UserName");
//			}
//			connection.close();
//		} catch (SQLException e) {
//			System.out.println("Something went wrong.");
//			e.printStackTrace();
//		}
//		 return name;
//	}
	
	public static User LogIn(String name, String password) {		
		User user = new User();
		try {
			Connection connection = DatabaseConnection();
			
			String sql = "SELECT *"
					+ " FROM Users"
					+ " WHERE Users.UserName = '" + name + "' AND Users.UserPassword = '" + password + "' OR Users.UserEmail = '" + name + "' AND Users.UserPassword = '" + password + "'";
			
			Statement query = connection.createStatement();
			ResultSet result = query.executeQuery(sql);
			
			while(result.next()) {
				int usercode = result.getInt("UserCode");
				user.setUserCode(usercode);
				String username = result.getString("UserName");
				user.setName(username);
				String userpassword = result.getString("UserPassword");
				user.setPassword(userpassword);
				String useremail = result.getString("UserEmail");
				user.setEmail(useremail);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("Something went wrong.");
			e.printStackTrace();
		}
		return user;
	}

	public static User makeUser(String name, String password, String email) {
		User user = new User();
		try {
			Connection connection = DatabaseConnection();
			
			String sql = "INSERT INTO Users (UserName, UserPassword, UserEmail)"
					+ " VALUES (?, ?, ?)"; 
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			statement.setString(2, password);
			statement.setString(3, email);
			
			int rows = statement.executeUpdate();
			
			if (rows == 1) {
				System.out.println("User has been added to the database!");
			} else {
				System.out.println("Something went wrong.");
			}
			
			sql = "SELECT *"
					+ " FROM Users"
					+ " WHERE Users.UserName = '" + name + "' AND Users.UserPassword = '" + password + "' AND Users.UserEmail = '" + email + "'";
			
			Statement query = connection.createStatement();
			ResultSet result = query.executeQuery(sql);
			
			while(result.next()) {
				int usercode = result.getInt("UserCode");
				user.setUserCode(usercode);
				String username = result.getString("UserName");
				user.setName(username);
				String userpassword = result.getString("UserPassword");
				user.setPassword(userpassword);
				String useremail = result.getString("UserEmail");
				user.setEmail(useremail);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("Something went wrong.");
			e.printStackTrace();
		}
		return user;
	}
	
	public static void removeUser(User user) {
		removeUser(user.getName(), user.getPassword(), user.getEmail(), user.getUserCode());
	}
	
	public static void removeUser(String name, String password, String email, int usercode) {
		try {
			Connection connection = DatabaseConnection();
			
			String sql = "DELETE FROM Users"
					+ " WHERE Users.UserName = '" + name + "' AND Users.UserPassword = '" + password + "' AND Users.UserEmail = '" + email + "' AND UserCode = '" + usercode + "'";
			
			Statement statement = connection.createStatement();
			
			int rows = statement.executeUpdate(sql);
			
			if (rows == 1) {
				System.out.println("User has been removed from the database!");
			} else {
				System.out.println("Something went wrong.");
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("Something went wrong.");
			e.printStackTrace();
		}
	}
	
	private static Connection DatabaseConnection() throws SQLException {
		return DriverManager.getConnection("...", "...", "...");
	}
}
