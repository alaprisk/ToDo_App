package com.rishi.todoapp;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.rishi.todoapp.TodoActivity.User;


	
	public class CustomAdapter extends ArrayAdapter<User> {
		
		private final Context context;
		
	    public CustomAdapter(Context context, ArrayList<User> users) {
	       super(context, 0, users);
	       this.context = context;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	       // Get the data item for this position
	       User user = getItem(position);    
	       // Check if an existing view is being reused, otherwise inflate the view
	       if (convertView == null) {
		       Log.e("from getView, convertView = null","so far so good");
	          convertView = LayoutInflater.from(getContext()).inflate(0, parent, false);
	       }
	       // Lookup view for data population
	       EditText et1 = (EditText) convertView.findViewById(R.id.etNewItem);
	       //ListView lv = (ListView) convertView.findViewById(R.id.lvItems);
	       
	       //EditText et2 = (EditText) convertView.findViewById(R.id.etNewItemDate);
	       
	       Log.e("from getView, just about to return","so far so good");
	       
	       // Populate the data into the template view using the data object
	       et1.setText(user.todostring);
	       //et2.setText(user.duedate);
	       // Return the completed view to render on screen
	       
	       
	       
	       return convertView;
	   }
	}
