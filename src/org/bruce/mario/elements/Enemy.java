package org.bruce.mario.elements;

import java.awt.image.BufferedImage;

import org.bruce.mario.gui.ResLoader;

public class Enemy implements Runnable {

	// 坐标
	private int x;
	private int y;

	// 初始坐标
	private int startX;
	private int startY;

	// 类型
	private int type;

	// 显示图片
	private BufferedImage showImage;

	// 移动方向
	private boolean isLeftOrUp = true;

	// 移动范围
	private int upMax = 0;
	private int downMax = 0;

	public BackGround getBg() {
		return bg;
	}

	// 定义线程
	Thread t = new Thread(this);

	// 定义当前的图片状态
	private int imageType = 0;

	// 定义一个场景对象
	private BackGround bg;

	// 创建普通敌人
	@SuppressWarnings("deprecation")
	public Enemy(int x, int y, boolean isLeft, int type, BackGround bg) {
		this.x = x;
		this.y = y;
		this.startX = x;
		this.startY = y;
		this.isLeftOrUp = isLeft;
		this.type = type;
		this.bg = bg;
		if (type == 1) {
			this.showImage = ResLoader.allTriangleImage.get(0);
		}
		t.start();
		// 为防止场景很乱（进入第一个场景的时候第二个场景中的敌人早已经动了半天了），先将线程挂起
		t.suspend();
	}

	public int getType() {
		return type;
	}

	@SuppressWarnings("deprecation")
	public void startMove() {
		t.resume();
	}

	// 创建食人花
	@SuppressWarnings("deprecation")
	public Enemy(int x, int y, boolean isUp, int type, int upMax, int downMax,
			BackGround bg) {
		this.x = x;
		this.y = y;
		this.startX = x;
		this.startY = y;
		this.isLeftOrUp = isUp;
		this.type = type;
		this.bg = bg;
		this.upMax = upMax;
		this.downMax = downMax;
		if (type == 2) {
			this.showImage = ResLoader.allFlowerImage.get(0);
		}
		t.start();
		t.suspend();
	}

	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (type == 1) {
				if (this.isLeftOrUp) {
					this.x -= 2;
				} else {
					this.x += 2;
				}

				// 图片的交替显示
				if (imageType == 0) {
					imageType = 1;
				} else {
					imageType = 0;
				}

				boolean canLeft = true;
				boolean canRight = true;

				for (int i = 0; i < this.bg.getAllObstruction().size(); i++) {
					Obstacle ob = this.bg.getAllObstruction().get(i);
					// 不允许继续向右移动
					if (ob.getX() == this.x + 60
							&& (ob.getY() + 55 > this.y && ob.getY() - 55 < this.y)) {
						canRight = false;
					}
					// 不允许继续向左移动
					if (ob.getX() == this.x - 60
							&& (ob.getY() + 55 > this.y && ob.getY() - 55 < this.y)) {
						canLeft = false;
					}
				}
				if (this.isLeftOrUp && !canLeft || this.x == 0) {
					this.isLeftOrUp = false;
				} else if (!this.isLeftOrUp && !canRight || this.x == 840) {
					this.isLeftOrUp = true;
				}

				this.showImage = ResLoader.allTriangleImage.get(imageType);

			}

			if (type == 2) {
				if (this.isLeftOrUp) {
					this.y -= 2;
				} else {
					// this.x += 2; 这个错误害我找了半天，后果就是食人花不受边界限制
					this.y += 2;
				}
				if (imageType == 0) {
					imageType = 1;
				} else {
					imageType = 0;
				}

				// 我晕死，视频里是“this.x == this.upMax”！真是他妈的奇了怪了
				if (this.isLeftOrUp && this.y == this.upMax) {
					this.isLeftOrUp = false;
				}
				if (!this.isLeftOrUp && this.y == this.downMax) {
					this.isLeftOrUp = true;
				}

				this.showImage = ResLoader.allFlowerImage.get(imageType);
			}

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public BufferedImage getShowImage() {
		return showImage;
	}

	public void reset() {
		// 将坐标重置
		this.x = this.startX;
		this.y = this.startY;
		// 将显示图片重置
		if (this.type == 1) {
			this.showImage = ResLoader.allTriangleImage.get(0);
		} else if (this.type == 2) {
			this.showImage = ResLoader.allFlowerImage.get(0);
		}
	}

	public void dead() {
		// 将显示的图片修改为死亡时的图片
		this.showImage = ResLoader.allTriangleImage.get(2);
		this.bg.getAllEnemy().remove(this);
		this.bg.getRemovedEnemy().add(this);
	}

}
