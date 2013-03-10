package com.ecological;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager {
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_SPECIES = "_species";
	public static final String KEY_NOTE = "_note";
	public static final String KEY_LAT = "_lat";
	public static final String KEY_LON = "_lon";
	
	private static final String DATABASE_NAME = "capturesdb";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "table1";
	
	private DbHelper helply;
	private final Context contextly;
	private SQLiteDatabase databasely;
	
	//Helper class
	private static class DbHelper extends SQLiteOpenHelper{
		
		public DbHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public void onCreate(SQLiteDatabase db)
		{
			db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
					KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					KEY_SPECIES + " TEXT NOT NULL, " +
					KEY_NOTE + " TEXT NOT NULL, " +
					KEY_LAT + " DOUBLE NOT NULL, " +
					KEY_LON + " DOUBLE NOT NULL);"
					);
		}
		
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
		
	}
	
	public DBManager(Context c)
	{
		contextly = c;
	}
	
	public DBManager open() throws SQLException
	{
		helply = new DbHelper(contextly);
		databasely = helply.getWritableDatabase();
		return this;
	}
	
	public void close()
	{
		helply.close();
	}
	
	//may need to change from long when picture is added
	public long createEntry(String species, String note, Double lat, Double lon)
	{
		ContentValues cv = new ContentValues();
		cv.put(KEY_SPECIES, species);
		cv.put(KEY_NOTE, note);
		cv.put(KEY_LAT, lat);
		cv.put(KEY_LON, lon);
		
		return databasely.insert(TABLE_NAME, null, cv);
	}
	
	public Cursor listData()
	{
		return databasely.query(TABLE_NAME, new String[]
				{
					KEY_ROWID,
					KEY_SPECIES,
					KEY_NOTE,
					KEY_LAT,
					KEY_LON},
					
					null,
					null,
					null,
					null,
					KEY_SPECIES + " ASC");
	}
	
	//Used for searching the database for a particular item based on a search term
	public Cursor getSearchResults(String searchQuery) throws SQLException 
	{
	       Cursor search = databasely.query(true, TABLE_NAME, new String[] 
	    		   {
		    		   KEY_ROWID,
		    		   KEY_SPECIES,
		    		   KEY_NOTE,
		    		   KEY_LAT,
		    		   KEY_LON
	    		   }, 
	    		   KEY_SPECIES + " like '%"+searchQuery+"%'", 
	                null,
	                null, 
	                null, 
	                null, 
	                null);
	       if (search != null) 
	       {
	           search.moveToFirst();
	       }
	       
	       return search;
	}

}
