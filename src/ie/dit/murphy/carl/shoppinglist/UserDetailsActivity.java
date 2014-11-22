package ie.dit.murphy.carl.shoppinglist;

import ie.dit.murphy.carl.shoppinglist.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UserDetailsActivity extends Activity {
	
	TextView txtName;
	RadioGroup rgGender;
	Spinner spnJobTitles;
	Button btnNext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_details);
		
		txtName = (TextView) findViewById(R.id.tvName);
		rgGender = (RadioGroup) findViewById(R.id.rgGender);
		spnJobTitles = (Spinner) findViewById(R.id.spnJobTitle);		
		btnNext =(Button) findViewById(R.id.btnUserNext);
		
		spnJobTitles.setAdapter(new ArrayAdapter<String> 
		  	(this, android.R.layout.simple_spinner_dropdown_item, 
		  	getResources().getStringArray(R.array.jobtitles)));
		
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//Save to a UserInfo object, then validate
				
				UserInfo info = UserInfo.getInstance(); //shorten reference
				View badView = null; //store the first invalid view, to be focused.
				
				//Name
				info.setName(txtName.getText().toString());
				//Toast.makeText(UserDetailsActivity.this, "Name="+info.getName(),Toast.LENGTH_SHORT).show();
				
				//Gender
				int selectedId = rgGender.getCheckedRadioButtonId();
				if (selectedId == -1) {
					UserInfo.getInstance().setGender("");
				} else {
					RadioButton selectedBtn = (RadioButton) findViewById(selectedId);
					UserInfo.getInstance().setGender(selectedBtn.getText().toString());
				}
				
				//Job Title
				UserInfo.getInstance().setJobTitle(spnJobTitles.getSelectedItem().toString());
				
				//set focus to the first invalid control, if any
				if (badView == null && !info.nameIsValid()) badView = txtName;
				if (badView == null && !info.genderIsValid()) badView = rgGender;
				if (badView == null && !info.jobTitleIsValid()) badView = spnJobTitles;	
				if (badView != null) badView.requestFocus();
				//show all the problems in a toast
				try {
					UserInfo.getInstance().getErrors();
				} catch (Exception ex) {
					Toast.makeText(UserDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
					//return;
				}
				
				//all good - open the list activity
				try {
					Intent intent = new Intent(UserDetailsActivity.this, ShopItemsActivity.class);
					startActivity(intent);
				} catch (Exception ex) {
					Toast.makeText(UserDetailsActivity.this, 
						"Error opening list: "+ex.getMessage(), Toast.LENGTH_LONG).show();
				}

			}
			
		});
		
		
		
	}

}
