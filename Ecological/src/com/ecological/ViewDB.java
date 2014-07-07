package com.ecological;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

public class ViewDB extends ListActivity implements View.OnClickListener{

	Button back;
	Button search;
	
	EditText searchBar;
	
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
		
		search = (Button) findViewById(R.id.search_button);
		search.setOnClickListener(this);
		searchBar = (EditText) findViewById(R.id.search_bar);
		
	}
	
	public void onClick(View v) {
		
		switch (v.getId()){
		case R.id.search_button:
			String searchTerm = searchBar.getText().toString();
			
			//Checks to make sure the user entered something. Displays an error message if not
			if (searchTerm.matches(""))
			{
				data_view.open();
				Cursor view_list = data_view.listData();
				
				String[] column_data = new String[] {"_species", "_lat", "_lon"};
				int[] row_data = new int[] {R.id.row_species, R.id.row_lat, R.id.row_lon};
				
				SimpleCursorAdapter cursor_adapt = new SimpleCursorAdapter(ViewDB.this, R.layout.row, view_list, column_data, row_data);
				this.setListAdapter(cursor_adapt);
				
				data_view.close();
			}
			else{
				data_view.open();
				
				Cursor resultCursor = data_view.getSearchResults(searchTerm);
				
				String[] column_data = new String[] {"_species", "_lat", "_lon"};
				int[] row_data = new int[] {R.id.row_species, R.id.row_lat, R.id.row_lon};
				
				SimpleCursorAdapter resultAdapter = new SimpleCursorAdapter(ViewDB.this, R.layout.row, resultCursor, column_data, row_data);
				this.setListAdapter(resultAdapter);
				
				data_view.close();
			}
		break;
		}
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
