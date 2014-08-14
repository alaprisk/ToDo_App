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
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TodoActivity extends Activity {

	private ArrayList<String> items;
	private ArrayAdapter<String> itemsAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	//private EditText etNewItemDate;
	private final int REQUEST_CODE = 100;
	public ArrayList<User> arrayOfUsers;
	public CustomAdapter adapter;
	
	private MyAppDatabase dbHelper;
	private SQLiteDatabase db;
	private String[] dbitems;
	
    Time today = new Time(Time.getCurrentTimezone());
	
	public class User {
	    public String todostring;
	    public String priority = "Med";
	    public String date = Integer.toString(today.month+1) + ":" + Integer.toString(today.monthDay + 1) + ":" + Integer.toString(today.year);

	    public User(String todostring) {
		       this.todostring = todostring;
		}
	    
	    public User(String todostring, String priority, String date) {
	       this.todostring = todostring;
	       this.priority = priority;
	       this.date = date;
	    }
	    
	}
	
	//Method to sort the list according to their priorities.
	public void update_list()
	{
        Cursor c = db.rawQuery("select * from ToDoTable", dbitems);
       		 
                
        int textcolumn = c.getColumnIndex("todotext");
        int prioritycolumn = c.getColumnIndex("priority");
        int datecolumn = c.getColumnIndex("date");
        
        while(c.moveToNext()) {   
    	    /*Log.e("from onCreate : ",c.getString(prioritycolumn));
    	    Log.e("from onCreate : ",c.getString(textcolumn));
    	    Log.e("from onCreate : ",c.getString(datecolumn));*/

        	if(c.getString(prioritycolumn).startsWith("High"))
        	{	
        		//items.add("*** "+c.getString(textcolumn));
                User newuser = new User(c.getString(textcolumn),c.getString(prioritycolumn),c.getString(datecolumn));
        		arrayOfUsers.add(newuser);
                adapter.notifyDataSetChanged();
        	}
        }
        c = db.rawQuery("select * from ToDoTable", dbitems);
        while(c.moveToNext()) {
        	if(c.getString(prioritycolumn).startsWith("Med"))
        	{	
        		//items.add("**  "+c.getString(textcolumn));
                User newuser = new User(c.getString(textcolumn),c.getString(prioritycolumn),c.getString(datecolumn));
        		arrayOfUsers.add(newuser);
                adapter.notifyDataSetChanged();
        	}
        }
        c = db.rawQuery("select * from ToDoTable", dbitems);
        while(c.moveToNext()) {
        	if(c.getString(prioritycolumn).startsWith("Low"))
        	{
        		//items.add("*   "+c.getString(textcolumn));
                User newuser = new User(c.getString(textcolumn),c.getString(prioritycolumn),c.getString(datecolumn));
        		arrayOfUsers.add(newuser);
                adapter.notifyDataSetChanged();
        	}
        }
	}
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        lvItems = (ListView) findViewById(R.id.lvItems);

	    today.setToNow(); 
        //Toast.makeText(this,"Today's Date : " + Integer.toString(today.month+1) + ":" + Integer.toString(today.monthDay + 1) + ":" + Integer.toString(today.year), Toast.LENGTH_LONG).show();
	    
		items = new ArrayList<String>();        
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
        		items);
        
        lvItems.setAdapter(itemsAdapter);
        
        
        // Construct the data source
        arrayOfUsers = new ArrayList<User>();
        // Create the adapter to convert the array to views
        adapter = new CustomAdapter(this, R.layout.activity_todo_updatedlist, arrayOfUsers);
        // Attach the adapter to a ListView
        lvItems.setAdapter(adapter);        
        
        /* Add item to adapter
        User newUser1 = new User("Item testing 1","High", "8:15:2014");
        User newUser2 = new User("Item testing 2");
        User newUser3 = new User("Item testing 3","Low","8:20:2014");
        
        adapter.add(newUser1);
        adapter.add(newUser2);
        adapter.add(newUser3);*/
        
       // adapter.notifyDataSetChanged();
        
	   // Log.e("from onCreate, done with creating user adapter","so far so good");
        
	    //SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	    //String currentDateandTime = sdf.format(new Date(REQUEST_CODE, REQUEST_CODE, REQUEST_CODE));
	    
	    
	    
	            
        dbHelper = new MyAppDatabase(this);
        db = dbHelper.getWritableDatabase();
        
        update_list();
        
        lvItems.setLongClickable(true);
        
        //Method to Remove contents from the list.   
        //setupListViewListener();
        
        //Method to Edit contents from the list.   
       // setupListViewEditListener();
        
        //Method to Remove contents from the Array list.   
        //setupArrayListListener();
        setupOnLongClickListener();
        setupOnClickListener();

 	   //Log.e("from onCreate, done with creating user adapter","so far so good");

    }    
   /* 
   private void setupListViewListener() {
    	
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener(){

    		@Override
    		public boolean onItemLongClick(AdapterView<?> adapter, View view,
    				int position, long id) {
    			// TODO Auto-generated method stub
				
    			String tobedeleted = items.get(position).substring(4);
    			
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
   */
    
    private void setupOnLongClickListener()
    {    	
    	
        //Toast.makeText(TodoActivity.this, "from setupArrayListListener 1", Toast.LENGTH_LONG).show();       	 

    	
    	lvItems.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener() {

    		
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
           		Log.e("hai bye","");

				
	           //Toast.makeText(TodoActivity.this, "from setupArrayListListener 2", Toast.LENGTH_LONG).show();       	 
								
    			User tobedeleted = arrayOfUsers.get(position);
    			
    			arrayOfUsers.remove(position);			
    			adapter.notifyDataSetChanged();
    			
    			
    			String whereClause = "todotext"+"=?";
    			String[]whereArgs = new String[] {tobedeleted.todostring};
    			
    			try{
    			db.delete("ToDoTable", whereClause, whereArgs);
           		//Log.e("ITEM_DELETED from db",tobedeleted.todostring);
           	 	}
    			catch (Exception e) 
    			{
           	       Log.e("ITEM_NOT_DELETED", tobedeleted.todostring);
           	 	}					
				
				return true;
			}
    		
    		
    	});
    }
    
    /*
    private void setupListViewEditListener() {
    	
    	lvItems.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
			
				//First deleting the entry in database.
				
				String priority;
				
				//if(items.get(index))
				
    			String tobedeleted = items.get(position);
				
    			if(tobedeleted.startsWith("*** "))priority = "High";
    			else if(tobedeleted.startsWith("**  "))priority = "Med";
    			else
    				priority = "Low";
    			
				tobedeleted = tobedeleted.substring(4);
    						
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
				 i.putExtra("to_be_edited_item",items.get(position).substring(4));
				 i.putExtra("position", position);
				 i.putExtra("priorityincoming",priority);
				 				 
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
         String priority = data.getExtras().getString("priority");
      
         if(priority.startsWith("High")) {

             priority = "High";

        	 
             // Toast the name to display temporarily on screen
             //Toast.makeText(this, "priority = high", Toast.LENGTH_SHORT).show();       	 
         }
         else if(priority.startsWith("Med")) {
        	 //items.set(pos,"**  "+name);
        	 priority = "Med";
             // Toast the name to display temporarily on screen
             //Toast.makeText(this, "priority = med", Toast.LENGTH_SHORT).show();  
         }	 
         else {
        	 //items.set(pos,"*   "+name);  
        	 priority = "Low";
             // Toast the name to display temporarily on screen
             //Toast.makeText(this, "priority = low", Toast.LENGTH_SHORT).show();  
         }
         
         
		 
     	//Adding items to database
         
     	ContentValues insertValues = new ContentValues();
     	insertValues.put("todotext",name );
     	insertValues.put("priority", priority);
     	
     	try 
     	 {
     		db.insert("ToDoTable", null, insertValues);
     		Log.e("ITEM_ADDED",name);
     		
     		
     		items.clear();
     		update_list();
     		
     		
     		itemsAdapter.notifyDataSetChanged();
     		
     	 }
     	 catch (Exception e) 
     	 {
     	       Log.e("ERROR_FOUND", e.toString());
     	 }		 

      }
    } 
 	*/
    private void setupOnClickListener() {
    	lvItems.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			
				//First deleting the entry in database.
				
				//String priority;
	
				
				//if(items.get(index))
				
    			//String tobedeleted = items.get(position);
				
    			User tobedeleted = arrayOfUsers.get(position);
    			
    			/*
    			if(tobedeleted.startsWith("*** "))priority = "High";
    			else if(tobedeleted.startsWith("**  "))priority = "Med";
    			else
    				priority = "Low";
    			
				tobedeleted = tobedeleted.substring(4);
    			*/			
    			String whereClause = "todotext"+"=?";
    			String[]whereArgs = new String[] {tobedeleted.todostring};
    			
    			try{
    			db.delete("ToDoTable", whereClause,whereArgs);
           	 	}
    			catch (Exception e) 
    			{
           	       Log.e("ITEM_NOT_DELETED", tobedeleted.todostring);
           	 	}
    			
				 Intent i = new Intent( TodoActivity.this , EditItemActivity.class);
				 i.putExtra("to_be_edited_item",tobedeleted.todostring);
				 i.putExtra("position", position);
				 i.putExtra("priorityincoming",tobedeleted.priority);
				 i.putExtra("date", tobedeleted.date);
				 				 
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
         String priority = data.getExtras().getString("priority");
         String date = data.getExtras().getString("date");
      
         if(priority.startsWith("High")) {

             priority = "High";

        	 
             // Toast the name to display temporarily on screen
             //Toast.makeText(this, "priority = high", Toast.LENGTH_SHORT).show();       	 
         }
         else if(priority.startsWith("Med")) {
        	 //items.set(pos,"**  "+name);
        	 priority = "Med";
             // Toast the name to display temporarily on screen
             //Toast.makeText(this, "priority = med", Toast.LENGTH_SHORT).show();  
         }	 
         else {
        	 //items.set(pos,"*   "+name);  
        	 priority = "Low";
             // Toast the name to display temporarily on screen
             //Toast.makeText(this, "priority = low", Toast.LENGTH_SHORT).show();  
         }
         
         
		 
     	//Adding items to database
         
     	ContentValues insertValues = new ContentValues();
     	insertValues.put("todotext",name );
     	insertValues.put("priority", priority);
     	insertValues.put("date", date);
     	
     	try 
     	 {
     		db.insert("ToDoTable", null, insertValues);
     		Log.e("ITEM_ADDED",name);
     		
     		
     		//items.clear();
     		
     		arrayOfUsers.clear();
     		
     		update_list();
     		
     		
     		adapter.notifyDataSetChanged();
     		
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
    		
    		
    		
        	etNewItem.setText("");
        	
        	//Adding items to database
            
        	ContentValues insertValues = new ContentValues();
        	insertValues.put("todotext",itemText );
        	insertValues.put("priority", "Med");
        	insertValues.put("date", Integer.toString(today.month+1) + ":" + Integer.toString(today.monthDay + 1) + ":" + Integer.toString(today.year));
        	
        	try 
        	 {
        		db.insert("ToDoTable", null, insertValues);
        		Log.e("ITEM_ADDED to Db",itemText);
        		
        		arrayOfUsers.clear();
        		adapter.notifyDataSetChanged();
        		
         		//items.clear();
         		update_list();
         		
         		//itemsAdapter.notifyDataSetChanged();
        		
        	 }
        	 catch (Exception e) 
        	 {
        	       Log.e("ERROR_FOUND", e.toString());
        	 }
        	
        	
    	}
    	else {
    		
            Toast.makeText(this, "Task cannot be empty, please enter text", Toast.LENGTH_SHORT).show();
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

    	private static final int DATABASE_VERSION = 47;
    	
		public MyAppDatabase(Context context) {
			super(context,"database.db" ,null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
			try {
			db.execSQL("CREATE TABLE ToDoTable ( todotext STRING , priority STRING , date STRING)");
			}
			catch(Exception e)
			{
				Log.e("Unable to create Table : ", "ToDoTable");
			}
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
