package com.zky.zhanji;

import com.zky.zhanji.game.GameingActivity;

import android.R.animator;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ChooseActivity extends Activity {

	private ImageButton bt_Statr;
	private ImageButton bt_Set;
	private ImageButton bt_About;
	private ImageButton bt_exit;

	private MediaPlayer medioplayer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_choose);
        
        medioplayer=MediaPlayer.create(this, R.raw.choose);
        medioplayer.start();

        
        bt_Statr=(ImageButton)findViewById(R.id.choose_begin);
        bt_Set=(ImageButton)findViewById(R.id.choose_set);
        bt_About=(ImageButton)findViewById(R.id.choose_about);
        bt_exit=(ImageButton)findViewById(R.id.choose_exit);
             
        bt_Statr.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				medioplayer.stop();
				Intent intent=new Intent(ChooseActivity.this, GameingActivity.class);
				startActivity(intent);
				finish();
			
			}
		});
        
        
        
        bt_Set.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				Intent intent=new Intent(ChooseActivity.this, SetActivity.class);
				startActivity(intent);
				
			}
		});
        
        
        bt_About.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent=new Intent(ChooseActivity.this, AboutActivity.class);
				startActivity(intent);
				
			}
		});
        
        bt_exit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				medioplayer.stop();
				finish();
				System.exit(0);
			}
		});       
        
   
    }
    
    
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something on back.
        	medioplayer.stop();
        	
        	finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
