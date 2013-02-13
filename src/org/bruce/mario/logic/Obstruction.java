package org.bruce.mario.logic;

import java.awt.image.BufferedImage;

public class Obstruction implements Runnable {
	//坐标
	private int x;
	
	private int y;
	
	//线程用来完成游戏结束时降旗的操作
	private Thread t = new Thread(this);
	
	//类型
	private int type;
	
	public int getX() {
		return x;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getY() {
		return y;
	}

	public int getType() {
		return type;
	}

	public BufferedImage getShowImage() {
		return showImage;
	}

	//初始的类型
	private int startType;
	
	//显示的图片
	private BufferedImage showImage = null;
	
	//当前障碍物所在的场景
	private BackGround bg ;
	
	//构造方法
	public Obstruction(int x,int y,int type,BackGround bg) {
		this.x = x;
		this.y = y;
		this.type = type ;
		this.startType =type;
		this.bg = bg;
		setImage();
		if(this.type == 11) {
			t.start();
		}
	}
	
	//重置方法
	public void reset() {
		
		//修改类型为初始类型
		this.type = startType;
		
		//改变显示图片
		this.setImage();
	}
	
	//根据类型改变显示图片
	public void setImage() {
		showImage = StaticValue.allObstructionImage.get(type);
	}

	public void run() {
		while(true) {
			if(this.bg.isOver()) {
				if(this.y < 420) {
					this.y += 5 ;
				} else {
					this.bg.setDown(true);
				}				
			}
			
			//睡眠加捕获异常
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
