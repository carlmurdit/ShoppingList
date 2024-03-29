package ie.dit.murphy.carl.shoppinglist.view;

import ie.dit.murphy.carl.shoppinglist.R;
import ie.dit.murphy.carl.shoppinglist.model.ShopItems;
import ie.dit.murphy.carl.shoppinglist.model.UserInfo;
import ie.dit.murphy.carl.shoppinglist.model.ShopItems.Summary;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ShopItemsActivity extends ListActivity {

	EditText txtSearch;
	ListView list;
	ItemAdapter shopItemAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		// The ListView's arraylist data source is in the singleton class ShopItems
		// load products from Strings.xml
		ShopItems.getInstance().loadProducts(this);  
		// initialise adapter with row.xml and data source 
		shopItemAdapter = new ItemAdapter(this, R.layout.row, 
				ShopItems.getInstance().getShopItemList());

		// Set the listview in the baseclass to use the adapter
		this.setListAdapter(shopItemAdapter);

		txtSearch = (EditText) findViewById(R.id.txtSearch);
		txtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable filterString) {
				shopItemAdapter.getFilter().filter(filterString.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}

		});
		
		Button btnNext = (Button) findViewById(R.id.btnListNext);
		btnNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Summary sel = ShopItems.getInstance().getSummary();
				if (sel.Count == 0) {
					Toast.makeText(ShopItemsActivity.this, "You have not selected any items.", Toast.LENGTH_LONG).show();
					return;
				}
				
				double budget =  UserInfo.getInstance().getBudget();
				if (sel.Total > budget) {
					String msg = "Your total is �%.2f which exceeds your budget of �%.2f.";
					msg = String.format(msg, sel.Total, budget);
					Toast.makeText(ShopItemsActivity.this, msg, Toast.LENGTH_LONG).show();
					return;
				}
				
				Intent intent = new Intent(ShopItemsActivity.this, ConfirmActivity.class);
				startActivity(intent);
			}
		});
		

	}


}
