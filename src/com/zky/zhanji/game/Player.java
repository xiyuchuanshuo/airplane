package com.zky.zhanji.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.View.OnTouchListener;

/**
 * ���Ƿɻ�
 * 
 * @author zky
 * 
 */
public class Player {
	// ���ǵ�Ѫ����Ѫ��λͼ
	// Ĭ��3Ѫ
	private int playerHp = 3;
	private Bitmap bmpPlayerHp;
	// ���ǵ������Լ�λͼ
	public static int x;
	public static int y;
	private static Bitmap bmpPlayer;
	// �����ƶ��ٶ�
	private static int speed = 5;

	// ��Ļ�������
	private int eventX, eventY;
	// ��ײ�����޵�ʱ��
	// ��ʱ��
	private static int noCollisionCount = 0;
	// ��Ϊ�޵�ʱ��
	private static int noCollisionTime = 60;
	// �Ƿ���ײ�ı�ʶλ
	private static boolean isCollision;

	// ���ǵĹ��캯��
	public Player(Bitmap a, Bitmap bmpPlayerHp) {
		this.bmpPlayer = a;
		this.bmpPlayerHp = bmpPlayerHp;
		x = MySurfaceView.screenW / 2 - bmpPlayer.getWidth() / 2;
		y = MySurfaceView.screenH - bmpPlayer.getHeight();
	}

	// ���ǵĻ�ͼ����
	public void draw(Canvas canvas, Paint paint) {
		// ��������
		// �������޵�ʱ��ʱ����������˸
		if (isCollision) {
			// ÿ2����Ϸѭ��������һ������
			if (noCollisionCount % 2 == 0) {
				canvas.drawBitmap(bmpPlayer, x, y, paint);
			}
		} else {
			canvas.drawBitmap(bmpPlayer, x, y, paint);
		}
		// ��������Ѫ��
		for (int i = 0; i < playerHp; i++) {
			canvas.drawBitmap(bmpPlayerHp, i * bmpPlayerHp.getWidth(),
					MySurfaceView.screenH - bmpPlayerHp.getHeight(), paint);
		}
	}

	// ���ǵ��߼�
	public static void logic(int eventX, int eventY) {
		// ���������ƶ�
		if (eventX < (x + 25)) {
			x -= speed;
		}
		if (eventX > (x + 25)) {
			x += speed;
		}
		if (eventY < (y + 30)) {
			y -= speed;
		}
		if (eventY > (y + 30)) {
			y += speed;
		}
		// �ж���ĻX�߽�
		if (x + bmpPlayer.getWidth() >= MySurfaceView.screenW) {
			x = MySurfaceView.screenW - bmpPlayer.getWidth();
		} else if (x <= 0) {
			x = 0;
		}
		// �ж���ĻY�߽�
		if (y + bmpPlayer.getHeight() >= MySurfaceView.screenH) {
			y = MySurfaceView.screenH - bmpPlayer.getHeight();
		} else if (y <= 0) {
			y = 0;
		}
	
	}

	// ���ǵ��߼�
	public static void logic2() {
		// �����޵�״̬
		if (isCollision) {
			// ��ʱ����ʼ��ʱ
			noCollisionCount++;
			if (noCollisionCount >= noCollisionTime) {
				// �޵�ʱ����󣬽Ӵ��޵�״̬����ʼ��������
				isCollision = false;
				noCollisionCount = 0;
			}
		}
	}

	// ��������Ѫ��
	public void setPlayerHp(int hp) {
		this.playerHp = hp;
	}

	// ��ȡ����Ѫ��
	public int getPlayerHp() {
		return playerHp;
	}

	// �ж���ײ(������л�)
	public boolean isCollsionWith(Enemy en) {
		// �Ƿ����޵�ʱ��
		if (isCollision == false) {
			int x2 = en.x;
			int y2 = en.y;
			int w2 = en.frameW;
			int h2 = en.frameH;
			if (x >= x2 && x >= x2 + w2) {
				return false;
			} else if (x <= x2 && x + bmpPlayer.getWidth() <= x2) {
				return false;
			} else if (y >= y2 && y >= y2 + h2) {
				return false;
			} else if (y <= y2 && y + bmpPlayer.getHeight() <= y2) {
				return false;
			}
			// ��ײ�������޵�״̬
			isCollision = true;
			return true;
			// �����޵�״̬��������ײ
		} else {
			return false;
		}
	}

	// �ж���ײ(������л��ӵ�)
	public boolean isCollsionWith(Bullet bullet) {
		// �Ƿ����޵�ʱ��
		if (isCollision == false) {
			int x2 = bullet.bulletX;
			int y2 = bullet.bulletY;
			int w2 = bullet.bmpBullet.getWidth();
			int h2 = bullet.bmpBullet.getHeight();
			if (x >= x2 && x >= x2 + w2) {
				return false;
			} else if (x <= x2 && x + bmpPlayer.getWidth() <= x2) {
				return false;
			} else if (y >= y2 && y >= y2 + h2) {
				return false;
			} else if (y <= y2 && y + bmpPlayer.getHeight() <= y2) {
				return false;
			}
			// ��ײ�������޵�״̬
			isCollision = true;
			return true;
			// �����޵�״̬��������ײ
		} else {
			return false;
		}
	}

	public static int[] playerX() {
		int a[] = new int[2];
		a[0] = x + 25;
		a[1] = y + 30;
		return a;
	}

	public static void logic3(float x2, float y2, float z) {
		// ���������ƶ�
				if(y2>0){
					y += speed;
				}else if(y2<0){
					y -= speed;
				}
				if(x2>0){
					x -= speed;
					
				}else if(x2<0){
					x += speed;
				}
				// �ж���ĻX�߽�
				if (x + bmpPlayer.getWidth() >= MySurfaceView.screenW) {
					x = MySurfaceView.screenW - bmpPlayer.getWidth();
				} else if (x <= 0) {
					x = 0;
				}
				// �ж���ĻY�߽�
				if (y + bmpPlayer.getHeight() >= MySurfaceView.screenH) {
					y = MySurfaceView.screenH - bmpPlayer.getHeight();
				} else if (y <= 0) {
					y = 0;
				}
		
	}
}
