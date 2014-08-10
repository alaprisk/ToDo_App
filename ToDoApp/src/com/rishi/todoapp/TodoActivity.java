package com.rishi.todoapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
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
	//private EditText etNewItemDate;
	private final int REQUEST_CODE = 100;
	//public ArrayList<User> arrayOfUsers;
	//public UsersAdapter adapter;
	
	private MyAppDatabase dbHelper;
	private SQLiteDatabase db;
	
	
	private String[] dbitems;
	
	/*public class User {
	    public String todostring;
	    public String duedate;

	    public User(String todostring, String duedate) {
	       this.todostring = duedate;
	       this.duedate = duedate;
	    }
	}
	
	public class UsersAdapter extends ArrayAdapter<User> {
	    public UsersAdapter(Context context, ArrayList<User> users) {
	       super(context, R.layout.activity_todo, users);
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	       // Get the data item for this position
	       User user = getItem(position);    
	       // Check if an existing view is being reused, otherwise inflate the view
	       if (convertView == null) {
	          convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_todo, parent, false);
	       }
	       // Lookup view for data population
	       EditText et1 = (EditText) convertView.findViewById(R.id.etNewItem);
	       //EditText et2 = (EditText) convertView.findViewById(R.id.etNewItemDate);
	       // Populate the data into the template view using the data object
	       et1.setText(user.todostring);
	       //et2.setText(user.duedate);
	       // Return the completed view to render on screen
	       return convertView;
	   }
	}
	*/
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        lvItems = (ListView) findViewById(R.id.lvItems);
                
		items = new ArrayList<String>();

        
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
        		items);
        
        lvItems.setAdapter(itemsAdapter);
        
        
        /* Construct the data source
        ArrayList<User> arrayOfUsers = new ArrayList<User>();
        // Create the adapter to convert the array to views
        UsersAdapter adapter = new UsersAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        
        //lvItems.setAdapter(adapter);
        
        
        // Add item to adapter
        User newUser = new User("Item testing", "");
        adapter.add(newUser);*/      
        
        dbHelper = new MyAppDatabase(this);
        db = dbHelper.getWritableDatabase();
        
        Cursor c = db.rawQuery("select * from ToDoTable", dbitems);
       		
        while(c.moveToNext()) {        	
        	items.add(c.getString(0));
        }
        
        //Method to Remove contents from the list.   
        setupListViewListener();
        
        //Method to Edit contents from the list.   
        setupListViewEditListener();
    }    
    
    private void setupListViewListener() {
    	
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener(){

    		@Override
    		public boolean onItemLongClick(AdapterView<?> adapter, View view,
    				int position, long id) {
    			// TODO Auto-generated method stub
				
    			String tobedeleted = items.get(position);
    			
    			items.remove(position);			
    			itemsAdapter.notifyDataSetChanged();
    			
    			
    			String whereClause = "todotext"+"=?";
    			String[]whereArgs = new String[] {tobedeleted};
    			
    			try{
    			db.delete("ToDoTable", whereClause,whereArgs);
           		Log.e("ITEM_DELETED",tobedeleted);
           	 	}
    			catch (Exception e) 
    			{
           	       Log.e("ITEM_NOT_DELETED", tobedeleted);
           	 	}
       			return true;
    		}	
    	});
    }
    
    private void setupListViewEditListener() {
    	
    	lvItems.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
			
				//First deleting the entry in database.
				
    			String tobedeleted = items.get(position);
    						
    			String whereClause = "todotext"+"=?";
    			String[]whereArgs = new String[] {tobedeleted};
    			
    			try{
    			db.delete("ToDoTable", whereClause,whereArgs);
           		Log.e("ITEM_DELETED",tobedeleted);
           	 	}
    			catch (Exception e) 
    			{
           	       Log.e("ITEM_NOT_DELETED", tobedeleted);
           	 	}
    			
				
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
		 
     	//Adding items to database
         
     	ContentValues insertValues = new ContentValues();
     	insertValues.put("todotext",name );
     	
     	try 
     	 {
     		db.insert("ToDoTable", "todotext", insertValues);
     		Log.e("ITEM_ADDED",name);
     	 }
     	 catch (Exception e) 
     	 {
     	       Log.e("ERROR_FOUND", e.toString());
     	 }		 

      }
    } 
    
    public void onAddedItem(View v) {
    	String itemText = etNewItem.getText().toString();
    	
    	if( !itemText.isEmpty() ) {
    		
    		//Adding to ToDo only if the input string is not empty.
    		itemsAdapter.add(itemText);
    		
    		// Add item to adapter
            //User newUser = new User(itemText, "a");
            //adapter.add(newUser);
    		
    		
    		
        	etNewItem.setText("");
        	
        	//Adding items to database
            
        	ContentValues insertValues = new ContentValues();
        	insertValues.put("todotext",itemText );
        	
        	try 
        	 {
        		db.insert("ToDoTable", "todotext", insertValues);
        		Log.e("ITEM_ADDED",itemText);
        	 }
        	 catch (Exception e) 
        	 {
        	       Log.e("ERROR_FOUND", e.toString());
        	 }
        	
        	
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
    
    
    public class MyAppDatabase extends SQLiteOpenHelper {

    	private static final int DATABASE_VERSION = 16;
    	
		public MyAppDatabase(Context context) {
			super(context,"database.db" ,null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
			db.execSQL("CREATE TABLE ToDoTable ( todotext string)");
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
			db.execSQL("DROP TABLE IF EXISTS ToDoTable");
			onCreate(db);
			Log.e("UPGRADED_TABLE", "ToDoTable");

		} 	
    }
    
    
}
