package com.rishi.todoapp;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rishi.todoapp.TodoActivity.User;


	
	public class CustomAdapter extends ArrayAdapter<User> {
		
		//private final Context context;
		
	    public CustomAdapter(Context context, int resource, ArrayList<User> users) {
	       super(context, resource, users);
	       //this.context = context;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	       
	    	// Get the data item for this position
	       User user = getItem(position);    
	       // Check if an existing view is being reused, otherwise inflate the view
	       if (convertView == null) {
		       //Log.e("from getView, convertView = null","so far so good");
	          convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_todo_updatedlist, null);
	       }
	       // Lookup view for data population
	       TextView tasktext = (TextView) convertView.findViewById(R.id.tasktext);
	       TextView taskdate = (TextView) convertView.findViewById(R.id.taskdate);
	       TextView taskpriority = (TextView) convertView.findViewById(R.id.taskpriority);
	       
	       // Populate the data into the template view using the data object
	       tasktext.setText(user.todostring);
	       taskdate.setText(user.date);
	       taskpriority.setText(user.priority);
	       
	       if(user.priority.startsWith("High")){
	    	   taskpriority.setTextColor(Color.RED);
	    	   //tasktext.setTextColor(Color.RED);
	       }
	       else if(user.priority.startsWith("Med")){
	    	   taskpriority.setTextColor(Color.YELLOW);
	    	   //tasktext.setTextColor(Color.YELLOW);
	       }
	       else{
	    	   taskpriority.setTextColor(Color.CYAN);
	    	   //tasktext.setTextColor(Color.CYAN);
	       }
	    	   
	       //et2.setText(user.duedate);
	       // Return the completed view to render on screen
	       
	       
	       
	       return convertView;
	   }
	}