package org.bruce.mario.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.bruce.mario.utils.PathUtil;

public class ResLoader {
	public static BufferedImage startImage;
	public static BufferedImage endImage;
	public static BufferedImage bgImage;

	public static BufferedImage marioDeadImage;

	public static List<BufferedImage> allMarioImage;
	public static List<BufferedImage> allFlowerImage;
	public static List<BufferedImage> allTriangleImage;
	public static List<BufferedImage> allTurtleImage;
	public static List<BufferedImage> allObstructionImage;

	// 将全部的图片初始化
	// "/Users/user/Projects/Eclipse/workspace3/Mario/src/";
	static {
		allMarioImage = new ArrayList<BufferedImage>();
		allFlowerImage = new ArrayList<BufferedImage>();
		allTriangleImage = new ArrayList<BufferedImage>();
		allTurtleImage = new ArrayList<BufferedImage>();
		allObstructionImage = new ArrayList<BufferedImage>();

		try {
			// 将所有马里奥的图片保存到静态属性当中
			for (int i = 1; i <= 10; ++ i) {
				allMarioImage.add(getBufferedImage(i + ".gif"));
			}

			// 导入全部背景
			startImage = getBufferedImage("start.gif");
			bgImage = getBufferedImage("firststage.gif");
			endImage = getBufferedImage("firststageend.gif");

			// 导入所有敌人的图片
			for (int i = 1; i <= 5; ++ i) {
				// 导入所有食人花的图片
				if (i <= 2) {
					allFlowerImage.add(getBufferedImage("flower" + i + ".gif"));
				}

				// 导入所有三角的图片
				if (i <= 3) {
					allTriangleImage.add(getBufferedImage("triangle" + i + ".gif"));
				}

				// 导入所有乌龟的图片
				allTurtleImage.add(getBufferedImage("turtle" + i + ".gif"));
			}

			// 导入障碍物的图片
			for (int i = 1; i <= 12; ++ i) {
				allObstructionImage.add(getBufferedImage("ob" + i + ".gif"));
			}

			// 导入马里奥死亡的图片
			marioDeadImage = getBufferedImage("over.gif");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param imgName
	 * @return
	 * @throws IOException 
	 */
	public static BufferedImage getBufferedImage(String imgName)
			throws IOException {
		String strPath = PathUtil.getPath(imgName);
		InputStream is = Class.class.getResourceAsStream(strPath);
		BufferedImage bi = ImageIO.read(is);
		is.close();
		return bi;
	}
}