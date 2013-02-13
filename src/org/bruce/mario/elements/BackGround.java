package org.bruce.mario.elements;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.bruce.mario.gui.ResLoader;

/**
 * @author Bruce Yang
 * 
 */
public class BackGround {
	// 当前场景显示的图片
	private BufferedImage bgImage = null;
	// 场景的顺序
	private int index;
	// 当前时候为最后一个场景
	private boolean flag;
	// 定义一个结束游戏的标记
	private boolean isOver = false;
	// 定义降旗结束的标记
	private boolean isDown = false;

	// 全部的敌人
	private List<Enemy> enemies = new ArrayList<Enemy>();
	// 被消灭的敌人
	private List<Enemy> enemiesRemoved = new ArrayList<Enemy>();

	// 全部的障碍物
	private List<Obstacle> obstacles = new ArrayList<Obstacle>();
	// 被消灭的障碍物
	private List<Obstacle> obstaclesRemoved = new ArrayList<Obstacle>();

	// 使敌人开始移动
	public void enemyStartMove() {
		for (int i = 0; i < enemies.size(); ++i) {
			enemies.get(i).startMove();
		}
	}

	// 构造方法
	public BackGround(int index) {
		this.index = index;

		if (index != 2) {
			flag = false;
			bgImage = ResLoader.bgImage;
		} else {
			flag = true;
			bgImage = ResLoader.endImage;
		}

		switch (index) {
		case 0: {
			for (int i = 0; i < 15; ++i) {
				obstacles.add(new Obstacle(i * 60, 540, 9, this));
			}

			// 绘制砖块和？
			obstacles.add(new Obstacle(120, 360, 4, this));
			obstacles.add(new Obstacle(300, 360, 0, this));
			obstacles.add(new Obstacle(360, 360, 4, this));
			obstacles.add(new Obstacle(420, 360, 0, this));
			obstacles.add(new Obstacle(480, 360, 4, this));
			obstacles.add(new Obstacle(540, 360, 0, this));
			obstacles.add(new Obstacle(420, 180, 4, this));

			// 绘制水管
			obstacles.add(new Obstacle(660, 540, 6, this));
			obstacles.add(new Obstacle(720, 540, 5, this));
			obstacles.add(new Obstacle(660, 480, 8, this));
			obstacles.add(new Obstacle(720, 480, 7, this));

			// 加入隐藏的砖块
			obstacles.add(new Obstacle(660, 300, 3, this));

			// 绘制敌人（出现黄线就在private List allEnemy的List后面加泛型<Enemy>）
			enemies.add(new Enemy(600, 480, true, 1, this));
			enemies.add(new Enemy(690, 540, true, 2, 420, 540, this));
		}
			break;

		case 1: {
			for (int i = 0; i < 15; ++i) {
				if (i != 10) {
					obstacles.add(new Obstacle(i * 60, 540, 9, this));
				}
			}
			obstacles.add(new Obstacle(60, 540, 6, this));
			obstacles.add(new Obstacle(120, 540, 5, this));
			obstacles.add(new Obstacle(60, 480, 6, this));
			obstacles.add(new Obstacle(120, 480, 5, this));
			obstacles.add(new Obstacle(60, 420, 8, this));
			obstacles.add(new Obstacle(120, 420, 7, this));

			obstacles.add(new Obstacle(240, 540, 6, this));
			obstacles.add(new Obstacle(300, 540, 5, this));
			obstacles.add(new Obstacle(240, 480, 6, this));
			obstacles.add(new Obstacle(300, 480, 5, this));
			obstacles.add(new Obstacle(240, 420, 6, this));
			obstacles.add(new Obstacle(300, 420, 5, this));
			obstacles.add(new Obstacle(240, 360, 8, this));
			obstacles.add(new Obstacle(300, 360, 7, this));
		}
			break;

		case 2: {
			for (int i = 0; i < 15; ++i) {
				obstacles.add(new Obstacle(i * 60, 540, 9, this));
			}
			obstacles.add(new Obstacle(550, 180, 11, this));
			obstacles.add(new Obstacle(550, 480, 2, this));
		}
			break;

		default:
			break;
		}
	}

	// 重置方法，将所有的障碍物和敌人返回到原有坐标，并将其状态也修改回
	public void reset() {
		// 将已经移除的障碍物和敌人放回到全部的内容中
		enemies.addAll(this.enemiesRemoved);
		obstacles.addAll(this.obstaclesRemoved);
		// 调用所有障碍物和敌人的重置方法
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).reset();
		}
		for (int i = 0; i < obstacles.size(); i++) {
			obstacles.get(i).reset();
		}
	}

	public boolean isFlag() {
		return flag;
	}

	public int getIndex() {
		return index;
	}

	public boolean isOver() {
		return isOver;
	}

	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public List<Enemy> getEnemiesRemoved() {
		return enemiesRemoved;
	}

	public List<Obstacle> getObstacles() {
		return obstacles;
	}

	public List<Obstacle> getObstaclesRemoved() {
		return obstaclesRemoved;
	}

	public boolean isDown() {
		return isDown;
	}

	public void setDown(boolean isDown) {
		this.isDown = isDown;
	}

	public BufferedImage getBgImage() {
		return bgImage;
	}
}
