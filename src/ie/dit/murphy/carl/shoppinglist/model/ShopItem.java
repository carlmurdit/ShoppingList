package ie.dit.murphy.carl.shoppinglist.model;

import java.util.Locale;

import android.content.Context;

public class ShopItem {

	private String name;
	private String desc;
	private int imgid;
	private int qty;
	private float price;

	public ShopItem(String name, String desc, int imgid, int qty, float price) {
		this.setName(name);
		this.setDesc(desc);
		this.setImgid(imgid);
		this.setQty(qty);
		this.setPrice(price);
	}
	
	public static ShopItem parse(Context context, String concatString) throws Exception {
		// Expected: e.g. "Tea Bags|Lyons Tea Bags, 100 pack|teabags|1.99";
		// split on pipe character		
		try {
			String[] parts = concatString.split("\\|"); 
			String name = parts[0];			
			String desc = parts[1];
			String img = parts[2];
			float price = Float.parseFloat(parts[3]);
			// http://stackoverflow.com/questions/9481334/how-to-replace-r-drawable-somestring
			int resID = context.getResources().getIdentifier(img, "drawable", context.getPackageName()); 
			return new ShopItem(name, desc, resID, 0, price);
		} catch (Exception ex) {
			throw new Exception("Parse error. "+
					"Could not parse the string "+concatString+", "+ex.getMessage()+".");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getImgid() {
		return imgid;
	}

	public void setImgid(int imgid) {
		this.imgid = imgid;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public boolean contains(String filterText) {
		filterText = filterText.toLowerCase(Locale.getDefault());		
		String itemText = this.getName().toLowerCase(Locale.getDefault());
		return (itemText.contains(filterText));	
	}

}