package org.bruce.mario.gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.bruce.mario.elements.BackGround;
import org.bruce.mario.elements.Enemy;
import org.bruce.mario.elements.Mario;
import org.bruce.mario.elements.Obstacle;
import org.bruce.mario.utils.CompUtil;

/**
 * @author Bruce Yang
 * 
 */
public class MainFrame extends JFrame implements KeyListener, Runnable {
	private static final long serialVersionUID = -2000932617609090031L;

	public static final int WIDTH = 900;
	public static final int HEIGHT = 600;

	private List<BackGround> listBg = new ArrayList<BackGround>();
	private BackGround currBg = null;
	private Mario mario = null;
	private Thread t = new Thread(this);

	// 是否已经开始游戏
	private boolean isStart = false;

	public MainFrame() {
		// 设置窗体标题
		this.setTitle("马里奥游戏程序");
		// 设置窗体大小
		this.setSize(WIDTH, HEIGHT);
		// 设置窗体是否可见
		this.setResizable(false);
		// 取得显示器屏幕的宽度和高度（以便使游戏窗体出现在屏幕中央）
		CompUtil.setComponentBoundsToCenterScreen(this);

		// 创建全部的场景
		for (int i = 0; i < 3; ++i) {
			listBg.add(new BackGround(i));
		}

		// 将第一个场景设置为当前场景
		currBg = listBg.get(0);

		// 初始化马里奥对象
		mario = new Mario(0, 480);

		// 将场景放入马里奥的对象属性中
		mario.setBg(currBg);

		this.repaint();

		this.addKeyListener(this);

		// 线程启动
		t.start();

		// 设置窗体关闭方式，关闭窗体时同时结束程序（不写的话点关闭按钮窗口关闭后程序还在运行）
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		// 建立临时的缓冲图片
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics g2 = image.getGraphics();

		if (this.isStart) {

			// 绘制背景
			g2.drawImage(currBg.getBgImage(), 0, 0, this);

			g2.drawString("生命：" + mario.getLife(), 700, 100);

			// 绘制敌人（如果把绘制敌人的代码放”绘制障碍物”后面，食人花就会出现在水管的外面）
			Iterator<Enemy> iterEnemy = currBg.getEnemies().iterator();
			while (iterEnemy.hasNext()) {
				Enemy e = iterEnemy.next();
				g2.drawImage(e.getShowImage(), e.getX(), e.getY(), this);
			}

			// 绘制障碍物
			Iterator<Obstacle> iter = currBg.getObstacles().iterator();
			while (iter.hasNext()) {
				Obstacle ob = iter.next();
				g2.drawImage(ob.getShowImage(), ob.getX(), ob.getY(), this);
			}

			g2.drawImage(mario.getShowImage(), mario.getX(), mario.getY(), this);

		} else {
			g2.drawImage(ResLoader.startImage, 0, 0, this);
		}

		// 把缓冲图片绘制到窗体中
		g.drawImage(image, 0, 0, this);

	}

	// 当点击键盘上的某一个键时
	@Override
	public void keyPressed(KeyEvent ke) {
		// System.out.println(ke.getKeyCode());
		if (isStart) {
			switch (ke.getKeyCode()) {
			// 向右移动
			case KeyEvent.VK_D:
				mario.rightMove();
				break;

			// 向左移动
			case KeyEvent.VK_A:
				mario.leftMove();
				break;

			// 跳跃
			case KeyEvent.VK_J:
				mario.jump();
				break;

			case KeyEvent.VK_K:
				mario.jump();
				break;

			case KeyEvent.VK_SPACE:
				mario.jump();
				break;

			default:
				break;
			}
		}
	}

	// 当抬起键盘上某一个键时
	@Override
	public void keyReleased(KeyEvent ke) {
		if (isStart) {
			switch (ke.getKeyCode()) {
			// 停止向右移动
			case KeyEvent.VK_D:
				mario.rightStop();
				break;

			// 停止向左移动
			case KeyEvent.VK_A:
				mario.leftStop();
				break;

			default:
				break;
			}
		} else {
			// press any key to start game!
			isStart = true;
			currBg.enemyStartMove();
			mario.setScore(0);
			mario.setLife(3);
		}

	}

	// 当通过键盘输入某些信息时
	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void run() {
		// 如果界面刷新和马里奥移动线程睡眠的时间不一致，就会出现操作和显示不协调的情况
		while (true) {
			this.repaint();
			try {
				Thread.sleep(50);
				if (mario.getX() >= 840) {
					// 切换场景
					// 先加1再减1，两相抵消
					currBg = listBg.get(currBg.getIndex() + 1);
					// 将当前场景设置到mario对象中
					mario.setBg(currBg);
					// 将场景中的敌人移动
					currBg.enemyStartMove();
					// 修改mario的坐标
					mario.setX(0);
				}

				// 对mario死亡的处理，必须放入MyFrame类而不能在Mario类中处理
				if (mario.isDead()) {
					JOptionPane.showMessageDialog(this, "mario is dead!");
					System.exit(0);
				}

				if (mario.isClear()) {
					JOptionPane.showMessageDialog(this, "恭喜通关，请期待以后的关卡！");
					System.exit(0);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
