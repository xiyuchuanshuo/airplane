package com.zky.zhanji;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private int width;
	private int height;
	private ImageView first;
	private ImageView second;
	private ImageView third;
	private ImageView fourth;
	public MediaPlayer players;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;

		first = (ImageView) findViewById(R.id.begin_first);
		second = (ImageView) findViewById(R.id.begin_second);
		third = (ImageView) findViewById(R.id.begin_third);
		fourth = (ImageView) findViewById(R.id.begin_fourth);

		players = MediaPlayer.create(MainActivity.this,
				R.raw.sound_ship_high_start);
		players.setLooping(false);// 设置循环播放
		players.start();

		Animation animation = new TranslateAnimation(width / 2 - 20,
				width / 2 - 20, height, -height);
		animation.setDuration(5000);

		first.startAnimation(animation);

		final Animation animation2 = new TranslateAnimation(width, 0, -480, 0);
		animation2.setDuration(2000);

		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				players.stop();
				players = MediaPlayer.create(MainActivity.this,
						R.raw.sound_ship_high_stop);
				players.setLooping(false);// 设置循环播放
				players.start();
				first.setVisibility(View.INVISIBLE);
				second.setVisibility(View.VISIBLE);
				second.startAnimation(animation2);

				setFlickerAnimation(third);
			}
		});
		animation2.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				setFlickerAnimation(fourth);

			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int iAction = event.getAction();
		if (iAction == MotionEvent.ACTION_UP) {
			players.stop();
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, ChooseActivity.class);
			startActivity(intent);
			finish();
		}

		return super.onTouchEvent(event);

	}

	private void setFlickerAnimation(ImageView iv_chat_head) {
		final Animation animation = new AlphaAnimation(1, 0); // Change alpha
																// from fully
																// visible to
																// invisible
		animation.setDuration(500); // duration - half a second
		animation.setInterpolator(new LinearInterpolator()); // do not alter
																// animation
																// rate
		animation.setRepeatCount(Animation.INFINITE); // Repeat animation
														// infinitely
		animation.setRepeatMode(Animation.REVERSE); //
		iv_chat_head.setAnimation(animation);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// do something on back.
			players.stop();

			finish();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
