package com.zky.zhanji.tools;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.zky.zhanji.R;

/**
 * Intent管理工具
 * 
 * @author way
 * 
 */
public class IntentUtils {

	public static final void sendSharedIntent(Context context,String item) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/*");

		String titleKey = "追忆分享：";
	

		StringBuilder sb = new StringBuilder();
		sb.append(titleKey).append(item);

		intent.putExtra(Intent.EXTRA_TEXT, sb.toString());
		intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
		context.startActivity(Intent.createChooser(intent,"分享" + ":" + item));
	}

	public static void keepDialog(DialogInterface dialog, boolean isClose) {
		try {
			Field field = dialog.getClass().getSuperclass()
					.getDeclaredField("mShowing");
			field.setAccessible(true);
			field.set(dialog, isClose);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
