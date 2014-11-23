package ie.dit.murphy.carl.shoppinglist.view;

import ie.dit.murphy.carl.shoppinglist.R;
import ie.dit.murphy.carl.shoppinglist.model.ShopItem;
import ie.dit.murphy.carl.shoppinglist.model.ShopItems;
import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmActivity extends Activity {
	
	String txt; // Shopping list contents

	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm);
		
		
		TextView tvSummary = (TextView) findViewById(R.id.tvConfirmList);
		
		Double dTotal = 0.0d;
		String sQty, sPrice, sPreVAT, sVAT, sTotal;
		txt = "\n";
		
		sQty = "QTY";
		txt += new String("    ").substring(sQty.length()) + sQty;
		sPrice = "PRICE";
		txt += new String("       ").substring(sPrice.length()) + sPrice;
		txt += "  DESCRIPTION\n\n";		
		
		for (ShopItem itm : ShopItems.getInstance().getShopItemList()) {			
			if (itm.getQty()>0) {
				sQty = Integer.toString(itm.getQty());
				txt += new String("    ").substring(sQty.length()) + sQty;
				sPrice = String.format("€%.2f", itm.getPrice());
				txt += new String("       ").substring(sPrice.length()) + sPrice;
				txt += "  "+itm.getName() +"\n";
				dTotal += itm.getQty() * itm.getPrice();
			}
		}
		
		txt += "\n";
		sPreVAT = "Pre-Vat: ";
		txt += new String("             ").substring(sPreVAT.length()) + sPreVAT;
		txt += String.format("€%.2f\n", dTotal / 1.21);
		sVAT = "VAT @ 21%: ";
		txt += new String("             ").substring(sVAT.length()) + sVAT;
		txt += String.format("€%.2f\n", dTotal - (dTotal / 1.21));
		sTotal = "Total: ";
		txt += new String("             ").substring(sTotal.length()) + sTotal;
		txt += String.format("€%.2f\n", dTotal);
		
		tvSummary.setText(txt);
		
		Button btnEmail = (Button) findViewById(R.id.btnEmail);
		btnEmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ConfirmActivity.this.send("carlmur@gmail.com", "Your Shopping List", txt);
			}
		});
		
		
		
	}

	// Reference: http://developer.android.com/training/sharing/send.html

		public void send(String to, String subject, String body) {
			
			String[] tos = {to};

			Intent emailIntent = new Intent();
			emailIntent.setAction(Intent.ACTION_SEND);	
			emailIntent.setData(Uri.parse("mailto:"));
			emailIntent.setType("text/plain");
			emailIntent.putExtra(Intent.EXTRA_EMAIL, tos);
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
			emailIntent.putExtra(Intent.EXTRA_TEXT, body);

			try {		
				startActivity(Intent.createChooser(emailIntent, 
						getResources().getString(R.string.send_shopping_list)));
			} catch (android.content.ActivityNotFoundException ex) {
				Toast.makeText(this, "There was an error sending the email. " + ex.getMessage(),
						Toast.LENGTH_LONG).show();
			}

		}
		// Reference complete
		
}
