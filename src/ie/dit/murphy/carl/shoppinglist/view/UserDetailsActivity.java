package ie.dit.murphy.carl.shoppinglist.view;

import ie.dit.murphy.carl.shoppinglist.R;
import ie.dit.murphy.carl.shoppinglist.model.UserInfo;
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
	
	RadioGroup rgGender;
	Spinner spnJobTitles;
	TextView txtName, txtAge, txtEmail, txtBudget;
	Button btnNext, btnReset;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_details);
		
		txtName = (TextView) findViewById(R.id.tvName);
		rgGender = (RadioGroup) findViewById(R.id.rgGender);
		spnJobTitles = (Spinner) findViewById(R.id.spnJobTitle);	
		txtAge = (TextView) findViewById(R.id.txtAge);
		txtEmail = (TextView) findViewById(R.id.txtEmail);
		txtBudget = (TextView) findViewById(R.id.txtBudget);
		btnNext = (Button) findViewById(R.id.btnUserNext);
		btnReset = (Button) findViewById(R.id.btnReset);
		
		spnJobTitles.setAdapter(new ArrayAdapter<String> 
		  	(this, android.R.layout.simple_spinner_dropdown_item, 
		  	getResources().getStringArray(R.array.jobtitles)));
		
		btnReset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				txtName.setText("");
				rgGender.clearCheck();
				spnJobTitles.setSelection(0);
				txtAge.setText("0");
				txtEmail.setText("");
				txtBudget.setText(getResources().getString(R.string.tenner));
				UserInfo.getInstance().clear();	
			}
		});
		
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//Save to a UserInfo object, then validate
				
				UserInfo info = UserInfo.getInstance(); //shorten reference
												
				// Name, Job Title, Age, Email, Budget
				info.setName(txtName.getText().toString());
				info.setJobTitle(spnJobTitles.getSelectedItem().toString());
				CharSequence age = ((txtAge.getText().length() == 0) ? "0" : txtAge.getText());
				info.setAge(Integer.parseInt(age.toString().trim()));
				info.setEmail(txtEmail.getText().toString());
				info.setBudget(Double.parseDouble(txtBudget.getText().toString()));
				//Gender
				int selectedId = rgGender.getCheckedRadioButtonId();
				if (selectedId == -1) {
					info.setGender("");
				} else {
					RadioButton selectedBtn = (RadioButton) findViewById(selectedId);
					info.setGender(selectedBtn.getText().toString());
				}
							
				//set focus to the first invalid control, if any
				View badView = null; //store the first invalid view, to be focused.
				if (badView == null && !info.nameIsValid()) badView = txtName;
				if (badView == null && !info.genderIsValid()) badView = rgGender;
				if (badView == null && !info.jobTitleIsValid()) badView = spnJobTitles;		
				if (badView == null && !info.ageIsValid()) badView = txtAge;
				if (badView == null && !info.emailIsValid()) badView = txtEmail;
				if (badView == null && !info.budgetIsValid()) badView = txtBudget;			
				if (badView != null) badView.requestFocus();
				//show all the problems in a toast
				try {
					info.getErrors();
				} catch (Exception ex) {
					Toast.makeText(UserDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
					return;
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
		
		txtName.requestFocus();
		
	}

}
