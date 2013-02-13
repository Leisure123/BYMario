package org.bruce.mario.logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


import javax.imageio.ImageIO;


public class StaticValue {

	public static List<BufferedImage> allMarioImage = new ArrayList<BufferedImage>();
	
	public static BufferedImage startImage = null;
	
	public static BufferedImage endImage = null;
	
	public static BufferedImage bgImage = null;
	
	public static List<BufferedImage> allFlowerImage = new ArrayList<BufferedImage>();
	
	public static List<BufferedImage> allTriangleImage = new ArrayList<BufferedImage>();
	
	public static List<BufferedImage> allTurtleImage = new ArrayList<BufferedImage>();
	
	public static List<BufferedImage> allObstructionImage = new ArrayList<BufferedImage>();
	
	public static BufferedImage marioDeadImage = null;
	
	public static String imagePath = "/Users/user/Projects/Eclipse/workspace3/Mario/src/";
	
	//只是定义了变量，还没有把所有图片导入进来
	
	//将全部的图片初始化
	public static void init() {
		//将所有马里奥的图片保存到静态属性当中
		for(int i = 1; i <= 10; i++) {
			try {
				allMarioImage.add(ImageIO.read(new File(imagePath + i + ".gif")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		//导入全部背景
		try {
			startImage =ImageIO.read(new File(imagePath + "start.gif"));
			bgImage = ImageIO.read(new File(imagePath + "firststage.gif"));
			endImage = ImageIO.read(new File(imagePath + "firststageend.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//导入所有敌人的图片
		for(int i= 1; i <= 5;i++) {
			try {
				//导入所有食人花的图片
				if(i <=2) {
					allFlowerImage.add(ImageIO.read(new File(imagePath+"flower"+i+".gif")));
				}
				
				//导入所有三角的图片
				if(i <=3) {
					allTriangleImage.add(ImageIO.read(new File(imagePath+"triangle"+i+".gif")));
				}
				
				//导入所有乌龟的图片
				allTurtleImage.add(ImageIO.read(new File(imagePath+"turtle" + i +".gif")));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		 
		//导入障碍物的图片
		for(int i = 1;i <= 12; i++) {
			try {
				allObstructionImage.add(ImageIO.read(new File(imagePath + "ob" + i + ".gif")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//导入马里奥死亡的图片
		try {
			marioDeadImage = ImageIO.read(new File(imagePath  + "over.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}