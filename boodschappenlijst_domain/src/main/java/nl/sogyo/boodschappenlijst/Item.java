package nl.sogyo.boodschappenlijst;

public class Item {

	private String Name = "";
	
	public void setName(String name) {
		this.Name = name;
	}
	
	public String getName() {
		return this.Name;
	}
	
	private int Amount = 0;
	
	public void setAmount(int amount) {
		this.Amount = amount;
	}
	
	public int getAmount() {
		return this.Amount;
	}
	
	private int Bought = 0;
	
	public void setBought(int bought) {
		this.Bought = bought;
	}
	
	public int getBought() {
		return this.Bought;
	}
	
	private String Shop = "";
	
	public void setShop(String shop) {
//		if (shop == null) {
//			this.Shop = "";
//		} else {
//			this.Shop = shop;
//		}
		this.Shop = shop;
	}
	
	public String getShop() {
		return this.Shop;
	}
	
	private int ItemCode = 0;
	
	public void setItemCode(int itemcode) {
		this.ItemCode = itemcode;
	}
	
	public int getItemCode() {
		return this.ItemCode;
	}

}
