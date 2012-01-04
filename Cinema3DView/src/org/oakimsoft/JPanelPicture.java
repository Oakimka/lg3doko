package org.oakimsoft;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class JPanelPicture extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BufferedImage biDisplaying = null;
	public Color backgroundColor = Color.black;

	/**
	 * Create the panel.
	 */
	public JPanelPicture() {

	}

	@Override
	public void revalidate() {

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Rectangle rr = this.getBounds();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setBackground(this.backgroundColor);
		g2d.clearRect(0, 0, rr.width, rr.height);

		if (biDisplaying == null)
			return;

		// Выравниваем по высоте
		double k1 = (double) rr.height / (double) biDisplaying.getHeight();
		// Если умещаемся
		if ((double) biDisplaying.getWidth() * k1 <= (double) rr.width) {
			g2d.drawImage(biDisplaying, (int) ((rr.width - biDisplaying.getWidth() * k1) / 2), 0, // от
																									// верхнего
					(int) (rr.width - (rr.width - biDisplaying.getWidth() * k1) / 2), rr.height, // до
																									// нижнего
					0, 0, biDisplaying.getWidth(), biDisplaying.getHeight(), null);
		} else {
			k1 = (double) rr.width / (double) biDisplaying.getWidth();
			g2d.drawImage(biDisplaying, 0, (int) ((rr.height - biDisplaying.getHeight() * k1) / 2), rr.width, (int) (rr.height - (rr.height - biDisplaying.getHeight() * k1) / 2), 0, 0,
					biDisplaying.getWidth(), biDisplaying.getHeight(), null);

		}

	}

}
