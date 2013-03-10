package com.ecological;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EcologicalActivity extends Activity {
	
	Button menu_add_btn;
	Button menu_view_btn;
	Button takePicture;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
        menu_add_btn = (Button) findViewById(R.id.menu_add);
        menu_add_btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View a) {
				Intent intent_add = new Intent(EcologicalActivity.this, AddData.class);
				startActivity(intent_add);
			}
		});
        
        menu_view_btn = (Button) findViewById(R.id.menu_view);
        menu_view_btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View b) {
				Intent intent_view = new Intent(EcologicalActivity.this, ViewDB.class);
				startActivity(intent_view);
			}
		});
        
        takePicture = (Button) findViewById(R.id.take_picture);
        takePicture.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View c) {
				Intent intent_picture = new Intent(EcologicalActivity.this, Camera.class);
				startActivity(intent_picture);
			}
		});
        
        
        
    }
}