package ie.dit.murphy.carl.shoppinglist.view;

import ie.dit.murphy.carl.shoppinglist.R;
import ie.dit.murphy.carl.shoppinglist.model.ShopItem;
import java.util.ArrayList;
import android.app.ListActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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

		ArrayList<ShopItem> shopItemList = new ArrayList<ShopItem>();

		for (String shpitm : getResources().getStringArray(R.array.shopitems)) {
			// add each item by parsing its concatenated string
			// e.g. "Tea Bags|Lyons Tea Bags, 100 pack|teabags|1.99";
			try {
				shopItemList
						.add(ShopItem.parse(ShopItemsActivity.this, shpitm));
			} catch (Exception e) {
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}

		shopItemAdapter = new ItemAdapter(this, R.layout.row, shopItemList);

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
		
		//Button btnNext = (Button) findViewById(R.id.)

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Toast.makeText(this, "AAAAAAAaaah!!!", Toast.LENGTH_SHORT).show();
		ShopItem itm = (ShopItem) l.getItemAtPosition(position);
		String s = itm.getName() + " " + itm.getPrice();
		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

	}

}
