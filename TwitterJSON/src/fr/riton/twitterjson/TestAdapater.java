package fr.riton.twitterjson;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TestAdapater extends ArrayAdapter<Event> {

	private Context context;
	private int textViewResourceId;
	private List<Event> data = null;

	public TestAdapater(Context context, int textViewResourceId,
			List<Event> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.textViewResourceId = textViewResourceId;
		this.context = context;
		this.data = objects;

	}

	static class RowLayoutHolder {
		TextView txt;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		/**
		 * convertView will be null if we should create a new View instance
		 * otherwise, it won't and in that case it will be an old instance
		 * that we should reuse (for instance when the user scroll down the list,
		 * first elements won't appear any more on the screen and could be reused
		 */
		
		View row = convertView;
		RowLayoutHolder holder = null; // This holder, allows us to find the View elements
										// without requesting the findViewById() twice
										// and will be stored in the Tag of the View
										// @see setTag() , getTag()
		
		if (row == null) {
			
			Log.d("ADAPTER", "row IS NULL");
			
			/*
			 * We should create a new element
			 */
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			
			/*
			 * Thanks to the holder, we only need to do this once
			 */
			row = inflater.inflate(textViewResourceId, parent, false);
			
			holder = new RowLayoutHolder();
			holder.txt = (TextView) row.findViewById(R.id.txt_key);
			
			row.setTag(holder);
		}
		else {
			
			Log.d("ADAPTER", "row NOT NULL");
			
			/*
			 * Retrieve our elements throw the holder
			 */
			holder = (RowLayoutHolder) row.getTag();
		}
		
		Event the_event = data.get(position);
		holder.txt.setText(the_event.id);
		holder.txt.setBackgroundResource(the_event.getResourceColorId());
		
		return row;
	}
	
	

}
