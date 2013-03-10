package com.ecological;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

public class ViewDB extends ListActivity {

	Button back;
	
	DBManager data_view = new DBManager(this);
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		data_view.open();
		Cursor view_list = data_view.listData();
		
		String[] column_data = new String[] {"_species", "_lat", "_lon"};
		int[] row_data = new int[] {R.id.row_species, R.id.row_lat, R.id.row_lon};
		
		SimpleCursorAdapter cursor_adapt = new SimpleCursorAdapter(ViewDB.this, R.layout.row, view_list, column_data, row_data);
		this.setListAdapter(cursor_adapt);
		
		data_view.close();
		
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View c) {
				finish();
			}
		});
		
	}
	
	protected void onResume()
	{
		super.onStart();
		
		data_view.open();
		Cursor view_list = data_view.listData();
		
		String[] column_data = new String[] {"_species", "_lat", "_lon"};
		int[] row_data = new int[] {R.id.row_species, R.id.row_lat, R.id.row_lon};
		
		SimpleCursorAdapter cursor_adapt = new SimpleCursorAdapter(ViewDB.this, R.layout.row, view_list, column_data, row_data);
		this.setListAdapter(cursor_adapt);
		
		data_view.close();	
	}
}
