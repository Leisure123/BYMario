package org.bruce.mario.gui;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.bruce.mario.logic.BackGround;
import org.bruce.mario.logic.Enemy;
import org.bruce.mario.logic.Mario;
import org.bruce.mario.logic.Obstruction;
import org.bruce.mario.logic.StaticValue;

public class MyFrame extends JFrame implements KeyListener,Runnable {
	
	private List<BackGround> allBG = new ArrayList<BackGround>();
	
	private Mario mario = null;
	
	private BackGround nowBG = null;
	
	private Thread t = new Thread(this);
	
	//是否已经开始游戏
	private boolean isStart = false;
	
	public MyFrame() {
		
		//设置窗体标题
		this.setTitle("马里奥游戏程序");
		
		//设置窗体大小
		this.setSize(900, 600);
		
		//取得显示器屏幕的宽度和高度（以便使游戏窗体出现在屏幕中央）
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		//设置窗体左上角的坐标
		this.setLocation((width-900)/2,(height-600)/2);
		
		//设置窗体是否可见
		this.setResizable(false);
		
		//初始化全部的图片
		StaticValue.init();
		
		//创建全部的场景
		for(int i = 1; i<= 3 ;i++ ) {
			this.allBG.add(new BackGround(i, i==3 ? true : false));
		}
		
		//将第一个场景设置为当前场景
		this.nowBG = this.allBG.get(0);
		
		//初始化马里奥对象
		this.mario = new Mario(0,480);
		
		//将场景放入马里奥的对象属性中
		this.mario.setBg(nowBG);
		
		this.repaint();
		
		this.addKeyListener(this);
		
		//线程启动
		t.start();
		
		//设置窗体关闭方式，关闭窗体时同时结束程序（不写的话点关闭按钮窗口关闭后程序还在运行）
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	

	public void paint(Graphics g) {
		//建立临时的缓冲图片
		BufferedImage image = new BufferedImage(900,600,BufferedImage.TYPE_3BYTE_BGR);
		Graphics g2 = image.getGraphics();
		
		if(this.isStart) {
		
			//绘制背景
			g2.drawImage(this.nowBG.getBgImage(), 0, 0, this);
			
			g2.drawString("生命：" + this.mario.getLife(),700,100);
			
			//绘制敌人（如果把绘制敌人的代码放”绘制障碍物”后面，食人花就会出现在水管的外面）
			Iterator<Enemy> iterEnemy = this.nowBG.getAllEnemy().iterator();
			while(iterEnemy.hasNext()) {
				Enemy e = iterEnemy.next();
				g2.drawImage(e.getShowImage(),e.getX(),e.getY(),this);
			}
			
			//绘制障碍物
			Iterator<Obstruction> iter =this.nowBG.getAllObstruction().iterator();
			while(iter.hasNext()) {
				Obstruction ob = iter.next();
				g2.drawImage(ob.getShowImage(), ob.getX(), ob.getY(), this);
			}
			
			g2.drawImage(this.mario.getShowImage(),this.mario.getX(),this.mario.getY(),this);
			
		} else {
			g2.drawImage(StaticValue.startImage,0,0,this);
		}
		
		//把缓冲图片绘制到窗体中
		g.drawImage(image,0,0,this);
		
	}
	
	//当点击键盘上的某一个键时
	
//	public void keyPressed(KeyEvent arg0) {
	public void keyPressed(KeyEvent ke) {
		
		if(this.isStart) {
			
//		arg0.getKeyChar();
//		System.out.println(arg0.getKeyCode());
//		键位的编码：左箭头37，右箭头39，空格键32
			
			//当按下39时（-->），马里奥向右移动
			if(ke.getKeyCode() == 39) {
				this.mario.rightMove();
			}
			
			//当按下37时（<--），马里奥向左移动                                               
			if(ke.getKeyCode() == 37) {
				this.mario.leftMove();
			}
			
			//当按下32时（空格键），mario跳跃
			if(ke.getKeyCode() == 32) {
				this.mario.jump();
			}
		}
	}
	
	//当抬起键盘上某一个键时

	public void keyReleased(KeyEvent ke) {
		
		if(this.isStart) {
		
			//当抬起 39 时（-->），马里奥停止向右移动
			if(ke.getKeyCode() == 39) {
				this.mario.rightStop();
			}
			//当抬起 37 时（<--），马里奥停止向左移动
			if(ke.getKeyCode() == 37) {
				this.mario.leftStop();
			}
		} else {
			if(ke.getKeyCode() == 32) {
				this.isStart = true;
				this.nowBG.enemyStartMove();
				this.mario.setScore(0);
				this.mario.setLife(3);
			}
		}

	}
	
	//当通过键盘输入某些信息时

	public void keyTyped(KeyEvent arg0) {

	}

	public void run() {
		// TODO Auto-generated method stub
		//如果界面刷新和马里奥移动线程睡眠的时间不一致，就会出现操作和显示不协调的情况
		while(true) {
			this.repaint();
			try {
				Thread.sleep(50);
				if(this.mario.getX() >= 840) {
					//切换场景
					//先加1再减1，两相抵消
					this.nowBG = this.allBG.get(this.nowBG.getSort());
					//将当前场景设置到mario对象中
					this.mario.setBg(this.nowBG);
					//将场景中的敌人移动
					this.nowBG.enemyStartMove();
					//修改mario的坐标
					this.mario.setX(0);
				}
				
				//对mario死亡的处理，必须放入MyFrame类而不能在Mario类中处理
				if(this.mario.isDead()) {
					JOptionPane.showMessageDialog(this,"mario is dead!");
					System.exit(0);
				}
				
				if(this.mario.isClear()) {
					JOptionPane.showMessageDialog(this,"恭喜通关，请期待以后的关卡！");
					System.exit(0);
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}







