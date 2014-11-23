package ie.dit.murphy.carl.shoppinglist.model;

import ie.dit.murphy.carl.shoppinglist.R;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.widget.Toast;

public class ShopItems {
	
	private static ShopItems shopItems;	
	private ArrayList<ShopItem> shopItemList;
	private ArrayList<ShopItem> buyItemList; // to contain all with qty > 0 
	
	private ShopItems() {}
	
	public static ShopItems getInstance() {
		if ( shopItems == null ) {
			shopItems = new ShopItems();
		}
		return shopItems;
	}
	
	public ArrayList<ShopItem> getShopItemList() {
		return shopItemList;
	}
	
	public ArrayList<ShopItem> getBuyItemList() {
		return buyItemList;
	}
	
	public void loadProducts(Context context) {
		
		String[] shopitemArray = context.getResources().getStringArray(R.array.shopitems);
		Arrays.sort(shopitemArray);

		shopItemList = new ArrayList<ShopItem>();

		for (String shpitm : shopitemArray) {
			// add each item by parsing its concatenated string
			// e.g. "Tea Bags|Lyons Tea Bags, 100 pack|teabags|1.99";
			try {
				getShopItemList().add(ShopItem.parse(context, shpitm));
			} catch (Exception e) {
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
		
		this.buyItemList = new ArrayList<ShopItem>();  //reset selection too

	}
	
	public class Summary {
		public int Count;
		public double Total;
		public Summary(int Count, double Total){
			this.Count = Count;
			this.Total = Total;
		}
	}

	public Summary getSummary() {
		
		int count = 0;
		double total = 0.0f;
		for(ShopItem itm : this.getBuyItemList()) { 
			if (itm.getQty()>0) {
				count += itm.getQty();
				total += itm.getQty() * itm.getPrice();
			}
		}
		return new Summary(count, total);
	}

}
