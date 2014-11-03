package com.zky.zhanji.game;

import java.util.Random;
import java.util.Vector;

import com.zky.zhanji.ChooseActivity;
import com.zky.zhanji.EndActivity;
import com.zky.zhanji.R;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.ProgressBar;

/**
 * 
 * @author zky
 * 
 */
public class MySurfaceView extends SurfaceView implements Callback, Runnable {
	private SurfaceHolder sfh;
	private Paint paint;
	private Thread th;
	private boolean flag;
	private Canvas canvas;
	public static int screenW, screenH;
	boolean gGlag = false;

	// �ӵ�����
	public static int VARIETY = 1;
	// ������Ϸ״̬����
	public static final int GAME_MENU = 0;// ��Ϸ�˵�
	public static final int GAMEING = 1;// ��Ϸ��
	public static final int GAME_WIN = 2;// ��Ϸʤ��
	public static final int GAME_LOST = 3;// ��Ϸʧ��
	public static final int GAME_PAUSE = -1;// ��Ϸ�˵�
	// ��ǰ��Ϸ״̬(Ĭ�ϳ�ʼ����Ϸ�˵�����)
	public static int gameState = GAME_MENU;
	// ����һ��Resourcesʵ�����ڼ���ͼƬ
	private Resources res = this.getResources();
	// ������Ϸ��Ҫ�õ���ͼƬ��Դ(ͼƬ����)
	private Bitmap bmpBackGround;// ��Ϸ����
	private Bitmap bmpBackGround_rockR;// ������ʯ
	private Bitmap bmpBackGround_rockL;// ������ʯ
	private Bitmap bmpBoom;// ��ըЧ��
	private Bitmap bmpBoosBoom;// Boos��ըЧ��
	private Bitmap bmpButton;// ��Ϸ��ʼ��ť
	private Bitmap bmpButtonPress;// ��Ϸ��ʼ��ť�����
	private Bitmap bmpEnemyDuck;// ����Ѽ��
	private Bitmap bmpEnemyFly;// �����Ӭ
	private Bitmap bmpEnemyBoos;// ������ͷBoos
	private Bitmap bmpGameWin;// ��Ϸʤ������
	private Bitmap bmpGameLost;// ��Ϸʧ�ܱ���
	private Bitmap bmpPlayer;// ��Ϸ���Ƿɻ�
	private Bitmap bmpPlayerHp;// ���Ƿɻ�Ѫ��
	private Bitmap bmpMenu;// �˵�����
	public static Bitmap bmpBullet;// �ӵ�
	public static Bitmap bmpSkill;// �ӵ�
	public static Bitmap bmpEnemyBullet;// �л��ӵ�
	public static Bitmap bmpBossBullet;// Boss�ӵ�
	// ����һ���˵�����
	private GameMenu gameMenu;
	private GameIng gameIng;
	// ����һ��������Ϸ��������
	private GameBg backGround;
	// �������Ƕ���
	private Player player;
	// ����һ���л�����
	private Vector<Enemy> vcEnemy;
	// ÿ�����ɵл���ʱ��(����)
	private int createEnemyTime = 50;
	private int count;// ������
	// �������飺1��2��ʾ�л������࣬-1��ʾBoss
	// ��ά�����ÿһά����һ�����
	private int enemyArray[][] = { { 1, 2 }, { 1, 1 }, { 1, 3, 1, 2 },
			{ 1, 2 }, { 2, 3 }, { 3, 1, 3 }, { 2, 2 }, { 1, 2 }, { 2, 2 },
			{ 1, 3, 1, 1 }, { 2, 1 }, { 1, 3 }, { 1, 3, 2, 1 }, { 1, 3, 1, 2 },
			{ 1, 2 }, { 2, 3 }, { 3, 1, 3 }, { 2, 2 }, { 1, 2, 3, 3, 2, 1 },
			{ 1, 2 }, { 2, 2 }, { 1, 3, 1, 1 }, { 1, 2, 3, 3, 2, 1 },
			{ 3, 3, 3 }, { 1, 3, 2, 1 }, {}, { -1 } };
	// ��ǰȡ��һά������±�
	private int enemyArrayIndex;
	// �Ƿ����Boss��ʶλ
	private boolean isBoss;
	// ����⣬Ϊ�����ĵл������漴����
	private Random random;
	// �л��ӵ�����
	private Vector<Bullet> vcBullet;
	// ����ӵ��ļ�����
	private int countEnemyBullet;
	// �����ӵ�����
	private static Vector<Bullet> vcBulletPlayer;
	// ����ӵ��ļ�����
	private int countPlayerBullet;
	// ��ըЧ������
	private Vector<Boom> vcBoom;
	// ����Boss
	private Boss boss;
	// Boss���ӵ�����
	public static Vector<Bullet> vcBulletBoss;
	Vibrator v;

	public static Context context;
	protected long lastDown = -1; 
	public final static long DOUBLE_TIME = 500;

	public MediaPlayer players;

	private SensorManager sm = null;
	private Sensor sensor = null;
	// //

	boolean bStart = false;

	Button StartSense;
	ProgressBar VBar, HBar;

	/**
	 * SurfaceView��ʼ������
	 */
	public MySurfaceView(Context context) {
		super(context);
		this.context = context;
		v = Shake.getV(context);
		sm = Shake.getG(context);
		Media.initSound(context);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		setFocusable(true);
		setFocusableInTouchMode(true);
		// ���ñ�������
		this.setKeepScreenOn(true);
		players = MediaPlayer.create(context, R.raw.bg);
		players.setLooping(true);// ����ѭ������
		players.start();
		// 2�����������Ӧ��
		sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		// 3��ע��������Ӧ���ļ�����
		sm.registerListener(sl, sensor, SensorManager.SENSOR_DELAY_GAME);
	}

	/**
	 * SurfaceView��ͼ��������Ӧ�˺���
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		initGame();
		flag = true;
		// ʵ���߳�
		th = new Thread(this);
		// �����߳�
		th.start();
	}
	 private SensorEventListener sl = new SensorEventListener() {

	        @SuppressWarnings("deprecation")
			@Override

	        public void onSensorChanged(SensorEvent event) {

	        	if(bStart){
	        		
		            float x3 = event.values[SensorManager.DATA_X];
		            float y3 = event.values[SensorManager.DATA_Y];
		            float z3 = event.values[SensorManager.DATA_Z];
		
		            //�� x=y=0 ʱ���ֻ�����ˮƽ����״̬��
		
		            //�� x=0 ���� y>0 ʱ���ֻ�������ˮƽλ��Ҫ���ڵײ���Ҳ����һ������绰ʱ�ֻ�������״̬��
		
		            //�� x=0 ���� y<0 ʱ���ֻ�������ˮƽλ��ҪС�ڵײ����ֻ�һ����ٴ�������״̬��
		
		            //�� y=0 ���� x>0 ʱ���ֻ��Ҳ��ˮƽλ��Ҫ������࣬Ҳ�����Ҳ౻̧��
		
		            //�� y=0 ���� x<0 ʱ���ֻ��Ҳ��ˮƽλ��ҪС����࣬Ҳ������౻̧��
		
		            //�� z=0 ʱ���ֻ�ƽ����ˮƽ�洹ֱ��
		
		            //�� z>0 ʱ���ֻ���Ļ���ϡ�
		
		            //�� z<0 ʱ���ֻ���Ļ���¡�
		            Player.logic3(x3,y3,z3);

	        	}
	        }
	 
	        @Override

	        public void onAccuracyChanged(Sensor sensor, int accuracy) {

	        }

	    };
	    
	   
	/*
	 * �Զ������Ϸ��ʼ������
	 */
	private void initGame() {
		// ������Ϸ�����̨���½�����Ϸʱ����Ϸ������!
		// ����Ϸ״̬���ڲ˵�ʱ���Ż�������Ϸ
		if (gameState == GAME_MENU) {
			// ������Ϸ��Դ
			bmpBackGround = BitmapFactory.decodeResource(res,
					R.drawable.background);
			bmpBackGround_rockR = BitmapFactory.decodeResource(res,
					R.drawable.bg_r);
			bmpBackGround_rockL = BitmapFactory.decodeResource(res,
					R.drawable.bg_l);
			bmpBoom = BitmapFactory.decodeResource(res, R.drawable.boom);
			bmpBoosBoom = BitmapFactory.decodeResource(res,
					R.drawable.boos_boom);
			bmpButton = BitmapFactory.decodeResource(res, R.drawable.button);
			bmpButtonPress = BitmapFactory.decodeResource(res,
					R.drawable.button_press);
			bmpEnemyDuck = BitmapFactory.decodeResource(res, R.drawable.enemy2);
			bmpEnemyFly = BitmapFactory.decodeResource(res, R.drawable.enemy1);
			bmpEnemyBoos = BitmapFactory.decodeResource(res, R.drawable.boss);
			bmpGameWin = BitmapFactory.decodeResource(res, R.drawable.gamewin);
			bmpGameLost = BitmapFactory
					.decodeResource(res, R.drawable.gamelost);
			bmpPlayer = BitmapFactory.decodeResource(res, R.drawable.player);
			bmpPlayerHp = BitmapFactory.decodeResource(res, R.drawable.hp);
			bmpMenu = BitmapFactory.decodeResource(res, R.drawable.menu);
			if (VARIETY % 2 == 1) {
				bmpBullet = BitmapFactory.decodeResource(res,
						R.drawable.bullet1);
			} else {
				bmpBullet = BitmapFactory.decodeResource(res,
						R.drawable.bullet2);
			}
			bmpSkill = BitmapFactory.decodeResource(res, R.drawable.skill);
			bmpEnemyBullet = BitmapFactory.decodeResource(res,
					R.drawable.bullet_enemy);
			bmpBossBullet = BitmapFactory.decodeResource(res,
					R.drawable.boosbullet);
			// ��ըЧ������ʵ��
			vcBoom = new Vector<Boom>();
			// �л��ӵ�����ʵ��
			vcBullet = new Vector<Bullet>();
			// �����ӵ�����ʵ��
			vcBulletPlayer = new Vector<Bullet>();
			// �˵���ʵ��
			gameMenu = new GameMenu(bmpMenu, bmpButton, bmpButtonPress);
			// ʵ����Ϸ����
			backGround = new GameBg(bmpBackGround, bmpBackGround_rockL,
					bmpBackGround_rockR);
			// ʵ������
			player = new Player(bmpPlayer, bmpPlayerHp);
			// ʵ���л�����
			vcEnemy = new Vector<Enemy>();
			// ʵ�������
			random = new Random();
			// ---Boss���
			// ʵ��boss����
			boss = new Boss(bmpEnemyBoos);
			// ʵ��Boss�ӵ�����
			vcBulletBoss = new Vector<Bullet>();
		}
	}

	/**
	 * ��Ϸ��ͼ
	 */
	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				// ��ͼ����������Ϸ״̬��ͬ���в�ͬ����
				switch (gameState) {
				case GAME_MENU:
					// �˵��Ļ�ͼ����
					gameMenu.draw(canvas, paint);
					break;
				case GAMEING:
					// ��Ϸ����
					backGround.draw(canvas, paint);
					// ���ǻ�ͼ����
					player.draw(canvas, paint);
					if (isBoss == false) {
						// �л�����
						for (int i = 0; i < vcEnemy.size(); i++) {
							vcEnemy.elementAt(i).draw(canvas, paint);
						}
						// �л��ӵ�����
						for (int i = 0; i < vcBullet.size(); i++) {
							vcBullet.elementAt(i).draw(canvas, paint);
						}
					} else {
						// Boos�Ļ���
						boss.draw(canvas, paint);
						// Boss�ӵ��߼�
						for (int i = 0; i < vcBulletBoss.size(); i++) {
							vcBulletBoss.elementAt(i).draw(canvas, paint);
						}
					}
					// ���������ӵ�����
					for (int i = 0; i < vcBulletPlayer.size(); i++) {
						vcBulletPlayer.elementAt(i).draw(canvas, paint);
					}
					// ��ըЧ������
					for (int i = 0; i < vcBoom.size(); i++) {
						vcBoom.elementAt(i).draw(canvas, paint);
					}
					break;
				case GAME_PAUSE:
					break;
				case GAME_WIN:
					canvas.drawBitmap(bmpGameWin, 0, 0, paint);
					break;
				case GAME_LOST:
					canvas.drawBitmap(bmpGameLost, 0, 0, paint);
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	/**
	 * �����¼�����
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// ���������¼�����������Ϸ״̬��ͬ���в�ͬ����
		switch (gameState) {
		case GAME_MENU:
			// �˵��Ĵ����¼�����
			Bullet.setskilltime(3);
			player.setPlayerHp(3);
			VARIETY = 1;
			gameMenu.onTouchEvent(event);
			break;
		case GAMEING:
			if (!bStart) {
				int iAction = event.getAction();
				if (iAction == MotionEvent.ACTION_MOVE
						|| iAction == MotionEvent.ACTION_DOWN) {

					int eventX = (int) event.getX();
					int eventY = (int) event.getY();

					Player.logic(eventX, eventY);
				}
				if (iAction == MotionEvent.ACTION_DOWN) {
					long nowDown = System.currentTimeMillis();

					if (nowDown - lastDown < DOUBLE_TIME) {
						skill();
						// VARIETY++;
						if (VARIETY % 2 == 1) {
							bmpBullet = BitmapFactory.decodeResource(res,
									R.drawable.bullet1);
						} else {
							bmpBullet = BitmapFactory.decodeResource(res,
									R.drawable.bullet2);
						}

					} else {
						lastDown = nowDown;
					}

				}
			} else {

			}
			break;
		case GAME_PAUSE:
			break;
		case GAME_WIN:
			break;
		case GAME_LOST:

			break;
		}
		return true;
	}

	/**
	 * ���������¼�����
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// ����back���ذ���
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// ��Ϸʤ����ʧ�ܡ�����ʱ��Ĭ�Ϸ��ز˵�
			if (gameState == GAMEING || gameState == GAME_WIN
					|| gameState == GAME_LOST) {
				gameState = GAME_MENU;
				// Boss״̬����Ϊû����
				isBoss = false;
				// ������Ϸ
				initGame();
				// ���ù������
				enemyArrayIndex = 0;
			} else if (gameState == GAME_MENU) {
				// ��ǰ��Ϸ״̬�ڲ˵����棬Ĭ�Ϸ��ذ����˳���Ϸ
				players.release();
				GameingActivity.instance.finish();

				Intent intent = new Intent(GameingActivity.instance,
						ChooseActivity.class);
				GameingActivity.instance.startActivity(intent);

			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	/**
	 * ��Ϸ�߼�
	 */
	private void logic() {
		// �߼����������Ϸ״̬��ͬ���в�ͬ����
		switch (gameState) {
		case GAME_MENU:
			break;
		case GAMEING:
			// �����߼�
			backGround.logic();
			// // �����߼�
			Player.logic2();
			// �л��߼�
			if (isBoss == false) {
				// �л��߼�
				for (int i = 0; i < vcEnemy.size(); i++) {
					Enemy en = vcEnemy.elementAt(i);
					// ��Ϊ����������ӵл� ����ô�Եл�isDead�ж���
					// �����������ô�ʹ�������ɾ��,�����������Ż����ã�
					if (en.isDead) {
						vcEnemy.removeElementAt(i);
					} else {
						en.logic();
					}
				}
				// ���ɵл�
				count++;
				if (count % createEnemyTime == 0) {
					for (int i = 0; i < enemyArray[enemyArrayIndex].length; i++) {
						// ��Ӭ
						if (enemyArray[enemyArrayIndex][i] == 1) {
							int x = random.nextInt(screenW - 100) + 50;
							vcEnemy.addElement(new Enemy(bmpEnemyFly, 1, x, -50));
							// Ѽ����
						} else if (enemyArray[enemyArrayIndex][i] == 2) {
							int y = random.nextInt(20);
							vcEnemy.addElement(new Enemy(bmpEnemyDuck, 2, -50,
									y));
							// Ѽ����
						} else if (enemyArray[enemyArrayIndex][i] == 3) {
							int y = random.nextInt(20);
							vcEnemy.addElement(new Enemy(bmpEnemyDuck, 3,
									screenW + 50, y));
						}
					}
					// �����ж���һ���Ƿ�Ϊ���һ��(Boss)
					if (enemyArrayIndex == enemyArray.length - 1) {
						isBoss = true;
					} else {
						enemyArrayIndex++;
					}
				}
				// ����л������ǵ���ײ
				for (int i = 0; i < vcEnemy.size(); i++) {
					if (player.isCollsionWith(vcEnemy.elementAt(i))) {
						// ������ײ������Ѫ��-1
						v.vibrate(new long[] { 100, 10, 100, 100 }, -1);
						player.setPlayerHp(player.getPlayerHp() - 1);
						// ������Ѫ��С��0���ж���Ϸʧ��
						// mVibrator.vibrate(new long[] { 100, 10, 100, 100 },
						// -1);
						if (player.getPlayerHp() <= -1) {
							// gameState = GAME_LOST;
							players.release();
							GameingActivity.instance.finish();

							Intent intent = new Intent(
									GameingActivity.instance, EndActivity.class);
							intent.putExtra("extra", "lost");
							GameingActivity.instance.startActivity(intent);

						}
					}
				}
				// ÿ2�����һ���л��ӵ�
				countEnemyBullet++;
				if (countEnemyBullet % 40 == 0) {
					for (int i = 0; i < vcEnemy.size(); i++) {
						Enemy en = vcEnemy.elementAt(i);
						// ��ͬ���͵л���ͬ���ӵ����й켣
						int bulletType = 0;
						switch (en.type) {
						// ��Ӭ
						case Enemy.TYPE_FLY:
							bulletType = Bullet.BULLET_FLY;
							break;
						// Ѽ��
						case Enemy.TYPE_DUCKL:
						case Enemy.TYPE_DUCKR:
							bulletType = Bullet.BULLET_DUCK;
							break;
						}
						vcBullet.add(new Bullet(bmpEnemyBullet, en.x + 10,
								en.y + 20, bulletType));
					}
				}
				// ����л��ӵ��߼�
				for (int i = 0; i < vcBullet.size(); i++) {
					Bullet b = vcBullet.elementAt(i);
					if (b.isDead) {
						vcBullet.removeElement(b);
					} else {
						int[] scoordinate = Player.playerX();
						b.logic(scoordinate);
					}
				}
				// ����л��ӵ���������ײ
				for (int i = 0; i < vcBullet.size(); i++) {
					if (player.isCollsionWith(vcBullet.elementAt(i))) {
						// ������ײ������Ѫ��-1
						v.vibrate(new long[] { 100, 10, 100, 100 }, -1);
						player.setPlayerHp(player.getPlayerHp() - 1);

						// ������Ѫ��С��0���ж���Ϸʧ��
						if (player.getPlayerHp() <= -1) {
							// gameState = GAME_LOST;
							players.release();
							GameingActivity.instance.finish();

							Intent intent = new Intent(
									GameingActivity.instance, EndActivity.class);
							intent.putExtra("extra", "lost");
							GameingActivity.instance.startActivity(intent);
						}
					}
				}

				// ���������ӵ���л���ײ
				for (int i = 0; i < vcBulletPlayer.size(); i++) {
					// ȡ�������ӵ�������ÿ��Ԫ��
					Bullet b = vcBulletPlayer.elementAt(i);
					for (int j = 0; j < vcEnemy.size(); j++) {
						// ��ӱ�ըЧ��
						// ȡ���л�������ÿ��Ԫ�������ӵ������ж�
						if (vcEnemy.elementAt(j).isCollsionWith(b)) {
							vcBoom.add(new Boom(bmpBoom,
									vcEnemy.elementAt(j).x, vcEnemy
											.elementAt(j).y, 7));

							b.isDead = true;
							Media.play(1);
						}

					}
				}
			} else {// Boss����߼�
				// ÿ0.5�����һ�������ӵ�
				boss.logic();
				if (countPlayerBullet % 10 == 0) {
					// Boss��û����֮ǰ����ͨ�ӵ�
					vcBulletBoss.add(new Bullet(bmpBossBullet, boss.x + 35,
							boss.y + 40, Bullet.BULLET_FLY));
				}
				// Boss�ӵ��߼�
				for (int i = 0; i < vcBulletBoss.size(); i++) {
					Bullet b = vcBulletBoss.elementAt(i);
					if (b.isDead) {
						vcBulletBoss.removeElement(b);
					} else {
						int[] scoordinate = Player.playerX();
						b.logic(scoordinate);
					}
				}
				// Boss�ӵ������ǵ���ײ
				for (int i = 0; i < vcBulletBoss.size(); i++) {
					if (player.isCollsionWith(vcBulletBoss.elementAt(i))) {
						// ������ײ������Ѫ��-1
						v.vibrate(new long[] { 100, 10, 100, 100 }, -1);
						player.setPlayerHp(player.getPlayerHp() - 1);
						// ������Ѫ��С��0���ж���Ϸʧ��
						if (player.getPlayerHp() <= -1) {
							// gameState = GAME_LOST;
							players.release();
							GameingActivity.instance.finish();

							Intent intent = new Intent(
									GameingActivity.instance, EndActivity.class);
							intent.putExtra("extra", "lost");
							GameingActivity.instance.startActivity(intent);
						}
					}
				}
				// Boss�������ӵ����У�������ըЧ��
				for (int i = 0; i < vcBulletPlayer.size(); i++) {
					Bullet b = vcBulletPlayer.elementAt(i);
					if (boss.isCollsionWith(b)) {
						if (boss.hp <= 0) {
							// ��Ϸʤ��
							// gameState = GAME_WIN;
							players.release();
							GameingActivity.instance.finish();

							Intent intent = new Intent(
									GameingActivity.instance, EndActivity.class);
							intent.putExtra("extra", "win");
							GameingActivity.instance.startActivity(intent);
						} else {
							// ��ʱɾ��������ײ���ӵ�����ֹ�ظ��ж����ӵ���Boss��ײ��
							b.isDead = true;
							// BossѪ����1
							int power = Bullet.bulletPower();
							boss.setHp(boss.hp - power);
							Log.i("TAG", "power=== " + power);
							// ��Boss���������Boss��ըЧ��
							vcBoom.add(new Boom(bmpBoosBoom, boss.x + 25,
									boss.y + 30, 5));
							vcBoom.add(new Boom(bmpBoosBoom, boss.x + 35,
									boss.y + 40, 5));
							vcBoom.add(new Boom(bmpBoosBoom, boss.x + 45,
									boss.y + 50, 5));
							Media.play(0);
						}
					}
				}
			}
			// ÿ1�����һ�������ӵ�
			countPlayerBullet++;
			if (VARIETY == 1) {
				if (countPlayerBullet % 9 < 3) {
					vcBulletPlayer.add(new Bullet(bmpBullet, player.x + 15,
							player.y - 20, Bullet.BULLET_PLAYER1));

					Media.play(2);
				}
			} else if (countPlayerBullet % 9 == 0) {
				vcBulletPlayer.add(new Bullet(bmpBullet, player.x + 15,
						player.y - 20, Bullet.BULLET_PLAYER2));

				Media.play(2);
			}
			// if (countPlayerBullet % 50 == 0) {
			//
			// vcBulletPlayer.add(new Bullet(bmpSkill,0,
			// screenH, Bullet.BULLET_PLAYER));
			// }
			// ���������ӵ��߼�
			for (int i = 0; i < vcBulletPlayer.size(); i++) {
				Bullet b = vcBulletPlayer.elementAt(i);
				if (b.isDead) {
					vcBulletPlayer.removeElement(b);
				} else {
					int[] scoordinate = Player.playerX();
					b.logic(scoordinate);
				}
			}
			// ��ըЧ���߼�
			for (int i = 0; i < vcBoom.size(); i++) {
				Boom boom = vcBoom.elementAt(i);
				if (boom.playEnd) {
					// ������ϵĴ�������ɾ��
					vcBoom.removeElementAt(i);
				} else {
					vcBoom.elementAt(i).logic();
				}
			}
			break;
		case GAME_PAUSE:
			break;
		case GAME_WIN:
			break;
		case GAME_LOST:
			break;

		}
	}

	public static void skill() {

		Bullet.setskilltime(Bullet.getskilltime() - 1);
		if (Bullet.getskilltime() >= 0) {
			vcBulletPlayer
					.add(new Bullet(bmpSkill, 0, 500, Bullet.SKILL_PLAYER));
		}

	}

	@Override
	public void run() {
		while (flag) {
			long start = System.currentTimeMillis();
			myDraw();
			logic();
			long end = System.currentTimeMillis();
			try {
				if (end - start < 50) {
					Thread.sleep(50 - (end - start));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * SurfaceView��ͼ״̬�����ı䣬��Ӧ�˺���
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	/**
	 * SurfaceView��ͼ����ʱ����Ӧ�˺���
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}

}
