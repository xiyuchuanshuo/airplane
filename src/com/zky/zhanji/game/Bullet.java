package com.zky.zhanji.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author zky �ӵ����
 */
public class Bullet {

	// �ӵ�������
	public static int POWER;

	// �ӵ�ͼƬ��Դ
	public Bitmap bmpBullet;
	// �ӵ�������
	public int bulletX, bulletY;
	// �ӵ����ٶ�
	public int speed;
	// �ӵ��������Լ�����
	public int bulletType;
	// ���ǵ�
	public static final int BULLET_PLAYER2 = -2;
	public static final int BULLET_PLAYER1 = -1;
	// Ѽ�ӵ�
	public static final int BULLET_DUCK = 1;
	// ��Ӭ��
	public static final int BULLET_FLY = 2;
	// Boss��
	public static final int BULLET_BOSS = 3;

	// ����
	public static final int SKILL_PLAYER = 5;
	// ���ܴ���
	private static int skilltime = 3;

	// �ӵ��Ƿ����� �Ż�����
	public boolean isDead;

	// Boss���״̬���ӵ���س�Ա����
	private int dir;// ��ǰBoss�ӵ�����
	// 8������
	public static final int DIR_UP = -1;
	public static final int DIR_DOWN = 2;
	public static final int DIR_LEFT = 3;
	public static final int DIR_RIGHT = 4;
	public static final int DIR_UP_LEFT = 5;
	public static final int DIR_UP_RIGHT = 6;
	public static final int DIR_DOWN_LEFT = 7;
	public static final int DIR_DOWN_RIGHT = 8;

	// �ӵ���ǰ����
	public Bullet(Bitmap bmpBullet, int bulletX, int bulletY, int bulletType) {
		this.bmpBullet = bmpBullet;
		this.bulletX = bulletX;
		this.bulletY = bulletY;
		this.bulletType = bulletType;
		// ��ͬ���ӵ������ٶȲ�һ
		switch (bulletType) {
		case BULLET_PLAYER1:
			speed = 12;
			POWER = 1;
			break;
		case BULLET_PLAYER2:
			speed = 10;
			POWER = 5;
			break;
		case BULLET_DUCK:
			speed = 6;
			break;
		case BULLET_FLY:
			speed = 10;
			break;
		case BULLET_BOSS:
			speed = 5;
		case SKILL_PLAYER:
			speed = 20;
			POWER = 10;
			break;
		}
	}

	public static int bullet() {

		return POWER;
	}

	public static int bulletPower() {
		return POWER;

	}

	/**
	 * ר���ڴ���Boss���״̬�´������ӵ�
	 * 
	 * @param bmpBullet
	 * @param bulletX
	 * @param bulletY
	 * @param bulletType
	 * @param Dir
	 */
	public Bullet(Bitmap bmpBullet, int bulletX, int bulletY, int bulletType,
			int dir) {
		this.bmpBullet = bmpBullet;
		this.bulletX = bulletX;
		this.bulletY = bulletY;
		this.bulletType = bulletType;
		speed = 5;
		this.dir = dir;
	}

	// �ӵ��Ļ���
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(bmpBullet, bulletX, bulletY, paint);
	}

	// �ӵ����߼�
	public void logic(int[] a) {
		// ��ͬ���ӵ������߼���һ
		// ���ǵ��ӵ���ֱ�����˶�
		int x = a[0];
		int y = a[1];
		switch (bulletType) {
		case BULLET_PLAYER1:
			bulletY -= speed;
			if (bulletY < -50) {
				isDead = true;
			}
			break;
		case BULLET_PLAYER2:
			bulletY -= speed;
			if (bulletY < -50) {
				isDead = true;
			}
			break;

		case SKILL_PLAYER:
			bulletY -= speed;
			if (bulletY < -50) {
				isDead = true;
			}
			break;
		// Ѽ�ӺͲ�Ӭ���ӵ����Ǵ�ֱ�����˶�
		case BULLET_DUCK:
			bulletY += speed;
			break;
		case BULLET_FLY:
			bulletY += speed;

			// ׷��
//			if (bulletY < y) {
//
//				if (bulletX > x) {
//					bulletX = bulletX - speed;
//				} else if (bulletX < x) {
//					bulletX = bulletX + speed;
//				}
//			}
			if (bulletY > MySurfaceView.screenH) {
				isDead = true;
			}
			break;
		// Boss���״̬�µ�8�����ӵ��߼�
		case BULLET_BOSS:
			// Boss���״̬�µ��ӵ��߼���ʵ��
			switch (dir) {
			// �����ϵ��ӵ�
			case DIR_UP:
				bulletY -= speed;
				break;
			// �����µ��ӵ�
			case DIR_DOWN:
				bulletY += speed;
				break;
			// ��������ӵ�
			case DIR_LEFT:
				bulletX -= speed;
				break;
			// �����ҵ��ӵ�
			case DIR_RIGHT:
				bulletX += speed;
				break;
			// �������ϵ��ӵ�
			case DIR_UP_LEFT:
				bulletY -= speed;
				bulletX -= speed;
				break;
			// �������ϵ��ӵ�
			case DIR_UP_RIGHT:
				bulletX += speed;
				bulletY -= speed;
				break;
			// �������µ��ӵ�
			case DIR_DOWN_LEFT:
				bulletX -= speed;
				bulletY += speed;
				break;
			// �������µ��ӵ�
			case DIR_DOWN_RIGHT:
				bulletY += speed;
				bulletX += speed;
				break;
			}
			// �߽紦��
			if (bulletY > MySurfaceView.screenH || bulletY <= -40
					|| bulletX > MySurfaceView.screenW || bulletX <= -40) {
				isDead = true;
			}
			break;
		}
	}

	// ��������Ѫ��
	public static void setskilltime(int a) {
		skilltime = a;
	}

	// ��ȡ����Ѫ��
	public static int getskilltime() {
		return skilltime;
	}
}
