package com.zky.zhanji;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SetActivity extends Activity {
	private ToggleButton bt_music;
	private ToggleButton bt_gravity;
	boolean musicFlag = true,gravityFlag =true;
	SharedPreferences preferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		
		bt_music = (ToggleButton) this.findViewById(R.id.toggleButton1);
		bt_gravity = (ToggleButton) this.findViewById(R.id.toggleButton2);
		
		
		
		bt_music.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (bt_music.isChecked()) {
					
					musicFlag = true;
					preferences = getSharedPreferences("ZKY", Context.MODE_PRIVATE);
					Editor editor = preferences.edit();
					editor.putBoolean("musicFlag", musicFlag);
					editor.commit();
					Toast.makeText(SetActivity.this, "音乐 开", Toast.LENGTH_SHORT)
							.show();
				} else {
					musicFlag = false;
					preferences = getSharedPreferences("ZKY", Context.MODE_PRIVATE);
					Editor editor = preferences.edit();
					editor.putBoolean("musicFlag", musicFlag);
					editor.commit();
					Toast.makeText(SetActivity.this, "音乐 关",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		bt_gravity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (bt_gravity.isChecked()) {
					gravityFlag = true;
					preferences = getSharedPreferences("ZKY", Context.MODE_PRIVATE);
					Editor editor = preferences.edit();
					editor.putBoolean("gravityFlag", gravityFlag);
					editor.commit();
					Toast.makeText(SetActivity.this, "重力 开", Toast.LENGTH_SHORT)
							.show();
				} else {
					gravityFlag = false;
					preferences = getSharedPreferences("ZKY", Context.MODE_PRIVATE);
					Editor editor = preferences.edit();
					editor.putBoolean("gravityFlag", gravityFlag);
					editor.commit();
					Toast.makeText(SetActivity.this, "重力 关",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

}
