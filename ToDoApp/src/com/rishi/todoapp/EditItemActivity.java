package com.rishi.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
//import android.widget.ListView;

public class EditItemActivity extends Activity {

	private EditText etOldItem;
	private int position;
	//private int priority;
	private EditText dueDate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
        getActionBar().setTitle("Updating the Task");
		
        etOldItem = (EditText) findViewById(R.id.etOldItem);
        dueDate = (EditText) findViewById(R.id.etDate);
        
        String to_be_edited_item = getIntent().getStringExtra("to_be_edited_item");
        position = getIntent().getIntExtra("position", 0);
        String priorityincoming = getIntent().getStringExtra("priorityincoming");
        String date = getIntent().getStringExtra("date");
        
        //RadioGroup rg = (RadioGroup)findViewById(R.id.radiogroup);
        
        RadioButton rbutton = (RadioButton)findViewById(R.id.radioButton1);
        String rtext = (String)rbutton.getText();
        if(rtext.startsWith(priorityincoming))rbutton.setChecked(true);
        
        rbutton = (RadioButton)findViewById(R.id.radioButton2);
        rtext = (String)rbutton.getText();
        if(rtext.startsWith(priorityincoming))rbutton.setChecked(true);
        
        rbutton = (RadioButton)findViewById(R.id.radioButton3);
        rtext = (String)rbutton.getText();
        if(rtext.startsWith(priorityincoming))rbutton.setChecked(true);           	
        
        etOldItem.setText(to_be_edited_item.toString());
        etOldItem.setSelection(etOldItem.length());
        
        dueDate.setText(date);
        dueDate.setSelection(4);
        		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onSavingItem(View v) {
	
    	String itemText = etOldItem.getText().toString();
    	String date = dueDate.getText().toString();
    	RadioGroup rg = (RadioGroup)findViewById(R.id.radiogroup);
        int priority = rg.getCheckedRadioButtonId();
        RadioButton rbutton = (RadioButton) rg.findViewById(priority);
        Log.v("Selected Radiobutton : " + rbutton.getText(),"");
    	
    	
    	if(!itemText.isEmpty() && !date.isEmpty())
    	{
    		// Prepare data intent 
    		Intent data = new Intent();
    	
    		// Pass relevant data back as a result
    		data.putExtra("newly_edited_text", itemText);
    		data.putExtra("position",position);
    		data.putExtra("priority", rbutton.getText());
    		data.putExtra("date",date);
    		
    	
            //Toast.makeText(this, rbutton.getText(), Toast.LENGTH_LONG).show();
    		// Activity finished ok, return the data
    		setResult(RESULT_OK, data); // set result code and bundle data for response
    		this.finish(); // closes the activity, pass data to parent
    	}
    	else {
    		
            Toast.makeText(this, "Fields cannot be empty, please enter text", Toast.LENGTH_SHORT).show();
    	}
    	
	
	}
	
}
