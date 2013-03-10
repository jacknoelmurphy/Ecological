package com.ecological;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Camera extends Activity implements View.OnClickListener {
	
	Button btn_cam;
	Button btn_back;
	ImageView iv;
	Bitmap bmp;
	Intent i;
	final static int imageData = 0;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo);
		initialize();
	}

	private void initialize() {
		
		btn_cam = (Button) findViewById (R.id.button1);
		btn_back = (Button) findViewById (R.id.button2);
		iv = (ImageView) findViewById (R.id.captured_picture);
		
		btn_cam.setOnClickListener(this);
		btn_back.setOnClickListener(this);
	}

	public void onClick(View v) {
		
		switch (v.getId()){
		case R.id.button1:
			i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i, imageData);
		break;
		
		case R.id.button2:
			
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
			iv.setImageBitmap(bmp);
		}
	}

}
