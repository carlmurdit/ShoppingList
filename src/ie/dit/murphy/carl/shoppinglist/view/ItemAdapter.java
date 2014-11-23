package ie.dit.murphy.carl.shoppinglist.view;

import ie.dit.murphy.carl.shoppinglist.R;
import ie.dit.murphy.carl.shoppinglist.model.ShopItem;
import ie.dit.murphy.carl.shoppinglist.model.ShopItems;
import ie.dit.murphy.carl.shoppinglist.model.ShopItems.Summary;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


public class ItemAdapter extends ArrayAdapter<ShopItem> implements Filterable {
	
	Context context;
	private List<ShopItem> shopItems_orig; 		// Contains all ShopItems, so filtered items can be restored
	private ItemFilter filter;					// the object that will handle filtering by name
	
	// references to the views on the current row
	ImageView img;
	TextView tvName;
	TextView tvDesc;
	TextView tvPrice;
	Button btnDown;
	TextView tvQty;
	Button btnUp;
	
	public ItemAdapter(Context context, int rowResource,
				List<ShopItem> shopItems) {
		super(context, rowResource, shopItems);
		this.context = context;
		this.shopItems_orig = new ArrayList<ShopItem>();
		this.shopItems_orig.addAll(shopItems);
		UpdateSummary();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		 View row = convertView;
		 if (row == null) {
			 LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		 
			 row = inflater.inflate(R.layout.row, parent, false);
			 
			img = (ImageView) row.findViewById(R.id.img);
			tvName = (TextView) row.findViewById(R.id.tvName);
			tvDesc = (TextView) row.findViewById(R.id.tvDesc);
			tvPrice = (TextView) row.findViewById(R.id.tvPrice);
			btnDown = (Button) row.findViewById(R.id.btnDown);
			tvQty = (TextView) row.findViewById(R.id.tvQty);
			btnUp = (Button) row.findViewById(R.id.btnUp);
			
			// Reference: Storing references to views in the row's tag to reduce findViewById calls
			// http://developer.android.com/training/improving-layouts/smooth-scrolling.html
			// http://www.piwai.info/android-adapter-good-practices/
			row.setTag(R.id.img, img);
			row.setTag(R.id.tvName, tvName);
			row.setTag(R.id.tvDesc, tvDesc);
			row.setTag(R.id.tvPrice, tvPrice);
			row.setTag(R.id.btnDown, btnDown);
			row.setTag(R.id.btnUp, btnUp);
			row.setTag(R.id.tvQty, tvQty);
			
		} else {
			
			img = (ImageView) row.getTag(R.id.img);
			tvName = (TextView) row.getTag(R.id.tvName);
			tvDesc = (TextView) row.getTag(R.id.tvDesc);
			tvPrice = (TextView) row.getTag(R.id.tvPrice);
			tvQty = (TextView) row.getTag(R.id.tvQty);
			btnDown = (Button) row.getTag(R.id.btnDown);
			btnUp = (Button) row.getTag(R.id.btnUp);
			// Reference complete
		}
		 
		// Each button needs to be able to find the Qty view in the same row
		btnDown.setTag(tvQty);
		btnUp.setTag(tvQty);

		// get the current ShopItem row
		final ShopItem rwItem = super.getItem(position);

		// set its views 
		img.setImageResource(rwItem.getImgid());
		tvName.setText(rwItem.getName());
		tvDesc.setText(rwItem.getDesc());
		tvPrice.setText(String.format("€ %.2f", rwItem.getPrice()));	
		btnUp.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Button btn = (Button) v;
				TextView tvQty = (TextView) btn.getTag();			
				IncrementQty(rwItem, tvQty);				
			}
		});
		tvQty.setText(Integer.toString(rwItem.getQty()));
		btnDown.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Button btn = (Button) v;
				TextView tvQty = (TextView) btn.getTag();			
				DecrementQty(rwItem, tvQty);
			}
		});
		
		return row;
	}
	
	private void IncrementQty(ShopItem rwItem, TextView tvQty) {
		
		if (rwItem.getQty() == 99) return;
			
		rwItem.setQty(rwItem.getQty()+1);
		tvQty.setText(Integer.toString(rwItem.getQty()));
	
		UpdateSummary();
	}
	
	private void DecrementQty(ShopItem rwItem, TextView tvQty) {
		
		if (rwItem.getQty() == 0) return;
		
		rwItem.setQty(rwItem.getQty()-1);
		tvQty.setText(Integer.toString(rwItem.getQty()));
	
		UpdateSummary();
	}
	
	private void UpdateSummary() {
		
		Summary sel = ShopItems.getInstance().getSummary();
				
		Resources res = context.getResources();
		String text = String.format(res.getString(R.string.listsummary), sel.Count, sel.Total);
		//String text = String.format(res.getString(R.string.welcome_messages), "Carl", 3);
		CharSequence styledText = Html.fromHtml(text);
		TextView tvSummary = (TextView) ((Activity) context).findViewById(R.id.tvSummary);
		tvSummary.setText(styledText);
		
	}
	
	@Override
	public Filter getFilter() {
		if (filter == null) {
			filter = new ItemFilter();
		}
		return filter;
	}

	//Reference: Applying a filter from 
	//http://www.mysamplecode.com/2012/07/android-listview-custom-layout-filter.html
	private class ItemFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {

			FilterResults result = new FilterResults();

			if (constraint != null && constraint.toString().length() > 0) {
				ArrayList<ShopItem> filteredResults = new ArrayList<ShopItem>();
				if (shopItems_orig != null && shopItems_orig.size() > 0) {
					for (ShopItem g : shopItems_orig) {
						if (g.contains(constraint.toString()))
							filteredResults.add(g);
					}
				}
				result.values = filteredResults;
				result.count = filteredResults.size();
			} else {
				result.values = shopItems_orig;
				result.count = shopItems_orig.size();
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {

			List<ShopItem> shopItems_filtered = (ArrayList<ShopItem>) results.values;
			notifyDataSetChanged();
			clear();
			for (int i = 0; i < shopItems_filtered.size(); i++)
				add(shopItems_filtered.get(i));
			notifyDataSetInvalidated();

		}

	}; 	// Reference complete
}
