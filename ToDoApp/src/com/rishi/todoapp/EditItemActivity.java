package com.rishi.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
//import android.widget.ListView;

public class EditItemActivity extends Activity {

	private EditText etOldItem;
	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
        etOldItem = (EditText) findViewById(R.id.etOldItem);
        
        String to_be_edited_item = getIntent().getStringExtra("to_be_edited_item");
        position = getIntent().getIntExtra("position", 0);
                
        etOldItem.setText(to_be_edited_item.toString());
        etOldItem.setSelection(etOldItem.length());
        		
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
    	 
    	// Prepare data intent 
    	Intent data = new Intent();
    	
    	// Pass relevant data back as a result
    	data.putExtra("newly_edited_text", itemText);
    	data.putExtra("position",position);
    	
    	// Activity finished ok, return the data
    	setResult(RESULT_OK, data); // set result code and bundle data for response
    	this.finish(); // closes the activity, pass data to parent
	
	}
	
}
