package com.rishi.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {

	private ArrayList<String> items;
	private ArrayAdapter<String> itemsAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	private final int REQUEST_CODE = 100;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        lvItems = (ListView) findViewById(R.id.lvItems);
        
        readItems();
        
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
        		items);
        
        lvItems.setAdapter(itemsAdapter);
        
        //Method to Remove contents from the list.   
        setupListViewListener();
        
        //Method to Edit contents from the list.   
        setupListViewEditListener();
    }

    public void readItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try{
    		items = new ArrayList<String>(FileUtils.readLines(todoFile));		
    	} catch (IOException e) {
    		items = new ArrayList<String>();
    	}
    }
    
    private void writeItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");   	
    	try{
    		FileUtils.writeLines(todoFile, items);
    	}catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    
    private void setupListViewListener() {
    	
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener(){

    		@Override
    		public boolean onItemLongClick(AdapterView<?> adapter, View view,
    				int position, long id) {
    			// TODO Auto-generated method stub
				
    			items.remove(position);			
    			itemsAdapter.notifyDataSetChanged();
    			writeItems();
    			return true;
    		}	
    	});
    }
    
    private void setupListViewEditListener() {
    	
    	lvItems.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
			
				 Intent i = new Intent( TodoActivity.this , EditItemActivity.class);
				 i.putExtra("to_be_edited_item",items.get(position));
				 i.putExtra("position", position);
				 				 
				 startActivityForResult(i, REQUEST_CODE); // brings up the EditItem Activity	
			}
    		
    	});    	
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      // REQUEST_CODE is defined above
      if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
         
    	 // Extract name value from result extras
         String name = data.getExtras().getString("newly_edited_text");
         int pos = data.getExtras().getInt("position");

         /*
         // Toast the name to display temporarily on screen
         Toast.makeText(this, name, Toast.LENGTH_SHORT).show();        
         */
         
         items.set(pos, name);
		 itemsAdapter.notifyDataSetChanged();
		 writeItems();

      }
    } 
    
    public void onAddedItem(View v) {
    	String itemText = etNewItem.getText().toString();
    	
    	if( !itemText.isEmpty() ) {
    		
    		//Adding to ToDo only if the input string is not empty.
    		itemsAdapter.add(itemText);
        	etNewItem.setText("");
        	writeItems();
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
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
}
