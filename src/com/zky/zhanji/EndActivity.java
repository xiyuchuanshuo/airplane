package com.zky.zhanji;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.zky.zhanji.tools.IntentUtils;

public class EndActivity extends Activity {

	private ImageView imageView;
	private Button back, fenxiang;

	private String win = "win";
	private String lost = "lost";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_end);

		imageView = (ImageView) findViewById(R.id.end_bg);
		back = (Button) findViewById(R.id.end_back);
		fenxiang = (Button) findViewById(R.id.end_fenxiang);

		Intent intent = getIntent();
		String s = intent.getStringExtra("extra");

		if (s == win || s.equals(win)) {
			imageView.setImageResource(R.drawable.gamewin);
		} else if (s == lost || s.equals(lost)) {
			imageView.setImageResource(R.drawable.gamelost);
		}

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(EndActivity.this, ChooseActivity.class);
				startActivity(intent);
			}
		});

		fenxiang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			//	NoteItem noteItem = mItems.get(menuInfo.position);
				IntentUtils.sendSharedIntent(EndActivity.this, "很有怀旧感觉的游戏，找回童年的记忆，灵魂战机带你找回最初的记忆。");
			}
		});
	}

}
