package com.zky.zhanji.game;

import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Vibrator;

public class Shake {

	public static Vibrator getV(Context context) {
		Vibrator v = (Vibrator) context.getApplicationContext()
				.getSystemService(Service.VIBRATOR_SERVICE);
		// TODO Auto-generated method stub
		return v;
	}

	public static SensorManager getG(Context context) {
	//	Sensor sensor = null;
		// 1，获得传感器管理器
		SensorManager sm = (SensorManager) context.getApplicationContext().getSystemService(
				Service.SENSOR_SERVICE);

		return sm;
	}

}
