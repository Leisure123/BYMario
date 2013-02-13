package org.bruce.mario.entry;

import javax.swing.SwingUtilities;

import org.bruce.mario.gui.MainFrame;

/**
 * @author Bruce Yang
 * 
 */
public class Entry {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrame();
			}
		});
	}
}
