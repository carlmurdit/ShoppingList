package ie.dit.murphy.carl.shoppinglist.model;

import ie.dit.murphy.carl.shoppinglist.R;
import java.util.ArrayList;
import android.content.Context;
import android.widget.Toast;

public class ShopItems {
	
	private static ShopItems shopItems;	
	private ArrayList<ShopItem> shopItemList;
	
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
	
	public void loadProducts(Context context) {
		shopItemList = new ArrayList<ShopItem>();

		for (String shpitm : context.getResources().getStringArray(R.array.shopitems)) {
			// add each item by parsing its concatenated string
			// e.g. "Tea Bags|Lyons Tea Bags, 100 pack|teabags|1.99";
			try {
				getShopItemList().add(ShopItem.parse(context, shpitm));
			} catch (Exception e) {
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}

	}
	

}
