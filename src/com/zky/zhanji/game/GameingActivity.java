package com.zky.zhanji.game;

import com.zky.zhanji.R;
import com.zky.zhanji.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Window;
import android.view.WindowManager;

public class GameingActivity extends Activity{

	public static GameingActivity instance;
	public Vibrator mVibrator;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		//����ȫ��
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//��ʾ�Զ����SurfaceView��ͼ
		setContentView(new MySurfaceView(this));	
		}
	
	
}
