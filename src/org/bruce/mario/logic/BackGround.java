package org.bruce.mario.logic;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BackGround {
	
	//当前场景显示的图片
	private BufferedImage bgImage = null;
	
	public boolean isOver() {
		return isOver;
	}

	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}

	//场景的顺序
	private int sort;
	
	//当前时候为最后一个场景 
	private boolean flag;
	
	//定义一个结束游戏的标记
	private boolean isOver = false ;
	
	//定义降旗结束的标记
	private boolean isDown = false ;
	
	public boolean isDown() {
		return isDown;
	}

	public void setDown(boolean isDown) {
		this.isDown = isDown;
	}

	public BufferedImage getBgImage() {
		return bgImage;
	}

	//通过集合来保存
	//全部的敌人
	private List<Enemy> allEnemy = new ArrayList<Enemy>();
	
	//全部的障碍物
	private List<Obstruction> allObstruction = new ArrayList<Obstruction>();
	
	public List<Obstruction> getAllObstruction() {
		return allObstruction;
	}

	//被消灭的敌人
	private List removedEnemy = new ArrayList();
	
	//被消灭的障碍物
	private List<Obstruction> removedObstruction = new ArrayList<Obstruction>();
	
	public List<Obstruction> getRemovedObstruction() {
		return removedObstruction;
	}
	
	//使敌人开始移动
	public void enemyStartMove() {
		for(int i = 0; i< this.allEnemy.size();i++) {
			this.allEnemy.get(i).startMove();
		}
	}

	//构造方法
	public BackGround(int sort,boolean flag) {
		this.sort = sort ;
		this.flag = flag ;
		if (flag) {
			bgImage = StaticValue.endImage;
		} else {
			bgImage = StaticValue.bgImage;
		}
		
		//如果当前场景为1
		if(sort == 1) {
			for(int i = 0; i < 15 ; i++) {
				this.allObstruction.add(new Obstruction(i*60,540,9,this));
			}
			
			//绘制砖块和？
			this.allObstruction.add(new Obstruction(120,360,4,this));
			this.allObstruction.add(new Obstruction(300,360,0,this));
			this.allObstruction.add(new Obstruction(360,360,4,this));
			this.allObstruction.add(new Obstruction(420,360,0,this));
			this.allObstruction.add(new Obstruction(480,360,4,this));
			this.allObstruction.add(new Obstruction(540,360,0,this));
			this.allObstruction.add(new Obstruction(420,180,4,this));
			
			//绘制水管
			this.allObstruction.add(new Obstruction(660,540,6,this));
			this.allObstruction.add(new Obstruction(720,540,5,this));
			this.allObstruction.add(new Obstruction(660,480,8,this));
			this.allObstruction.add(new Obstruction(720,480,7,this));
			
			//加入隐藏的砖块
			this.allObstruction.add(new Obstruction(660,300,3,this));
			
			//绘制敌人（出现黄线就在private List allEnemy的List后面加泛型<Enemy>）
			this.allEnemy.add(new Enemy(600,480,true,1,this));
			this.allEnemy.add(new Enemy(690,540,true,2,420,540,this));
		}
		
		//绘制第2个场景
		if(sort == 2) {
			for (int i = 0; i < 15; i++) {
				if (i != 10) {
					this.allObstruction.add(new Obstruction(i * 60, 540, 9,this));
				}
			}
			this.allObstruction.add(new Obstruction(60, 540, 6,this));
			this.allObstruction.add(new Obstruction(120, 540, 5,this));
			this.allObstruction.add(new Obstruction(60, 480, 6,this));
			this.allObstruction.add(new Obstruction(120, 480, 5,this));
			this.allObstruction.add(new Obstruction(60, 420, 8,this));
			this.allObstruction.add(new Obstruction(120, 420, 7,this));
			
			this.allObstruction.add(new Obstruction(240, 540, 6,this));
			this.allObstruction.add(new Obstruction(300, 540, 5,this));
			this.allObstruction.add(new Obstruction(240, 480, 6,this));
			this.allObstruction.add(new Obstruction(300, 480, 5,this));
			this.allObstruction.add(new Obstruction(240, 420, 6,this));
			this.allObstruction.add(new Obstruction(300, 420, 5,this));
			this.allObstruction.add(new Obstruction(240, 360, 8,this));
			this.allObstruction.add(new Obstruction(300, 360, 7,this));

		}
		
		//绘制第3个场景
		if(sort == 3) {
			for(int i = 0; i < 15; i++) {
				this.allObstruction.add(new Obstruction(i * 60,540,9,this));
			}
			this.allObstruction.add(new Obstruction(550,180,11,this));
			this.allObstruction.add(new Obstruction(550,480,2,this));
		}
	}
	
	public boolean isFlag() {
		return flag;
	}

	public List<Enemy> getAllEnemy() {
		return allEnemy;
	}

	public List getRemovedEnemy() {
		return removedEnemy;
	}

	public int getSort() {
		return sort;
	}

	//重置方法，将所有的障碍物和敌人返回到原有坐标，并将其状态也修改回
	public void reset() {
		//将已经移除的障碍物和敌人放回到全部的内容中
		this.allEnemy.addAll(this.removedEnemy);
		this.allObstruction.addAll(this.removedObstruction);
		//调用所有障碍物和敌人的重置方法
		for(int i = 0; i < this.allEnemy.size();i++) {
			this.allEnemy.get(i).reset();
		}
		for(int i = 0; i < this.allObstruction.size();i++) {
			this.allObstruction.get(i).reset();
		}
	}
	
}
