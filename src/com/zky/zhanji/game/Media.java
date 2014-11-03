package com.zky.zhanji.game;

import com.zky.zhanji.R;

import android.content.Context;
import android.media.MediaPlayer;

public class Media {

	public static SoundPlay soundPlay;
	public static final int ID_SOUND_BAOZHA = 0;
	public static final int ID_SOUND_BAOZHA2 = 1;
	public static final int ID_SOUND_SHOOT = 2;
	public static final int ID_SOUND_JIZHONG = 3;
	public static final int ID_SOUND_bisha = 4;
	

	public static void initSound(Context context) {
		soundPlay = new SoundPlay();
		soundPlay.initSounds(context);
		soundPlay.loadSfx(context, R.raw.baozha, ID_SOUND_BAOZHA);
		soundPlay.loadSfx(context, R.raw.baozha2, ID_SOUND_BAOZHA2);
		soundPlay.loadSfx(context, R.raw.shoot, ID_SOUND_SHOOT);
		soundPlay.loadSfx(context, R.raw.jizhong, ID_SOUND_JIZHONG);
		soundPlay.loadSfx(context, R.raw.bisha1, ID_SOUND_bisha);
	}

	public static void play(int i) {
		switch (i) {
		case 0:

			soundPlay.play(ID_SOUND_BAOZHA, 0);
			break;
		case 1:

			soundPlay.play(ID_SOUND_BAOZHA2, 0);
			break;
		case 2:

			soundPlay.play(ID_SOUND_SHOOT, 0);
			break;
		case 3:

			soundPlay.play(ID_SOUND_JIZHONG, 0);
			break;
		case 4:

			soundPlay.play(ID_SOUND_bisha, 0);
			break;
		default:
			break;
		}
	}
}
