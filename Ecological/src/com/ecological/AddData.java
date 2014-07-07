package com.ecological;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddData extends Activity implements View.OnClickListener{

	EditText speciesName;
	EditText note;
	EditText lat;
	EditText lon;
	Button add;
	
	FileOutputStream fos;
	String FILENAME = "Image File";
	
	byte[] ba;
	
	private LocationManager lm;
	private LocationListener ll = new MyLocationListener();
	
	Double templat;
	Double templon;
	
	ImageButton btn_image;
	Intent i;
	final static int imageData = 0;
	Bitmap bmp;
	
	DBManager data_add = new DBManager(this);
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_data);
		
		lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		
		speciesName = (EditText) findViewById(R.id.species_name);
		note = (EditText) findViewById(R.id.note);
		lat = (EditText) findViewById(R.id.lat);
		lon = (EditText) findViewById(R.id.lon);
		
		lat.setFocusable(false);
		lon.setFocusable(false);
		
		add = (Button) findViewById(R.id.add_button);
		add.setOnClickListener(this);
		
		btn_image = (ImageButton) findViewById(R.id.add_image);		
		btn_image.setOnClickListener(this);
		
		try {
			fos = openFileOutput(FILENAME, Context.MODE_PRIVATE); // opens file output. //if file doesn'texist it creates and opens it, new line closes it again. //file name will only be viewed in code, not in app
			fos.close(); //closes stream
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}
	
	public void onClick(View v) {
		
		switch (v.getId()){
		case R.id.add_image:
			i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i, imageData);
			
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
			
		break;
		
		case R.id.add_button:
			String species_in = speciesName.getText().toString();
			String note_in = note.getText().toString();
			
			
			if(species_in.matches(""))
			{
				AlertDialog.Builder species_blank = new AlertDialog.Builder(AddData.this);
				species_blank.setMessage("Ensure you enter a species name.")
				
				.setPositiveButton("Ok", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int dne)
					{
						dialog.cancel();
					}
				});
				
				AlertDialog s_n = species_blank.create();
				s_n.show();
			}
			
			else
			{
				try {
					FILENAME = Long.toString(System.currentTimeMillis())+".jpg";
					fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
					fos.write(ba);
					fos.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				data_add.open();
				data_add.createEntry(species_in, note_in, templat, templon);
				data_add.close();
				
				AlertDialog.Builder confirm_add = new AlertDialog.Builder(AddData.this);
				confirm_add.setMessage("Data added.")
				
				.setPositiveButton("Ok", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int dne)
					{
						finish();
					}
				});
				
				AlertDialog c_a = confirm_add.create();
				c_a.show();
			}
		break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK){
			Bundle extras = data.getExtras();
			bmp = (Bitmap) extras.get("data"); // "data" is key reference
			btn_image.setImageBitmap(bmp);
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			
			ba = bos.toByteArray();
		}
	}

	class MyLocationListener implements LocationListener{

		public void onLocationChanged(Location location) {
			if(location != null){
				lm.removeUpdates(ll);
				
				templat = location.getLatitude();
				templon = location.getLongitude();
				lat.setText(String.valueOf(templat));
				lon.setText(String.valueOf(templon));
			}
			
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/*public String writeImage(Context c, Bitmap imageOut)
	{
		String imageName = null;
		
		File path = new File(Environment.getDataDirectory() + "/MyPhotos");
		path.mkdirs();
		
		File image_out  = new File(path, Long.toString(System.currentTimeMillis()) + ".jpg");
		
		try{
			fos = new FileOutputStream(image_out);
			imageOut.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return imageName;
	}*/
	
}
