package org.bruce.mario.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;


/**
 * @author Bruce Yang
 * 2012。06。09。10。19 取自  HanYu 英汉词典项目~
 * 用于调整 JFrame, JDialog 窗口居中显示~
 */
public class CompUtil {
	
	public static void setComponentBoundsToCenterScreen(Component component) {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		int x = (int)(screen.getWidth() - component.getWidth()) / 2;
		int y = (int)(screen.getHeight() - component.getHeight()) / 2;
		
		component.setBounds(x, y, component.getWidth(), component.getHeight());
	}
}
