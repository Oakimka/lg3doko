package org.oakimsoft;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class JPanelPicture extends JPanel implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BufferedImage biDisplaying = null;
	public Color backgroundColor = Color.black;
	public PaintComponentExtension paintComponentExtension = null;
	private MouseListener externalMouseListener = null;
	

	/**
	 * Create the panel.
	 */
	public JPanelPicture() {
		
	}

	@Override
	public void revalidate() {

	}
	
	public void setExternalMouseListener(MouseListener mouseListener){
		if ( mouseListener!=null){
		externalMouseListener = mouseListener;
		this.addMouseListener(this);
		}
		
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
		if (this.paintComponentExtension != null){
			this.paintComponentExtension.paintComponentExt(this, g);
		}
			
		

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if (externalMouseListener!=null){
			externalMouseListener.mouseEntered( arg0);
		}
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if (externalMouseListener!=null){
			externalMouseListener.mouseExited( arg0);
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if (externalMouseListener!=null){
			externalMouseListener.mousePressed( arg0);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (externalMouseListener!=null){
			externalMouseListener.mouseReleased( arg0);
		}
	}
	
	
	/**
	 * Преобразование координат панели в координаты на изображении
	 * @param panelPoint Координаты на панели
	 */
	public Point coorsFromPanelToPic(Point panelPoint){
		Point picPoint = new Point(0,0);
		if (biDisplaying != null){
			Rectangle rr = this.getBounds();
			// Выравниваем по высоте
			double k1 = (double) rr.height / (double) biDisplaying.getHeight();
			// Если умещаемся
			if ((double) biDisplaying.getWidth() * k1 <= (double) rr.width) {
				// Выравнено по высоте
				picPoint.y = Math.round((float)(panelPoint.y / k1));
				
				picPoint.x = Math.round((float)((panelPoint.x - (rr.width - biDisplaying.getWidth() * k1) / 2.0 ) / k1));
				
			} else {
				// Выравнено по ширине
				k1 = (double) rr.width / (double) biDisplaying.getWidth();
				picPoint.x = Math.round((float)(panelPoint.x / k1));
				picPoint.y = Math.round((float)((panelPoint.y - (rr.height - biDisplaying.getHeight() * k1) / 2.0)  / k1));
			}
		}
		return picPoint;
	}
	/**
	 * Преобразование координат изображения в координаты на панели
	 * @param panelPoint Координаты на панели
	 */
	public Point coorsFromPicToPanel(Point picPoint){
		Point panelPoint = new Point(0,0);
		if (biDisplaying != null){
			Rectangle rr = this.getBounds();
			// Выравниваем по высоте
			double k1 = (double) rr.height / (double) biDisplaying.getHeight();
			// Если умещаемся
			if ((double) biDisplaying.getWidth() * k1 <= (double) rr.width) {
				// Выравнено по высоте
				panelPoint.y = Math.round((float)(picPoint.y * k1));
				panelPoint.x = Math.round((float)(( (rr.width - biDisplaying.getWidth() * k1) / 2.0  )+ picPoint.x *  k1));
				
			} else {
				// Выравнено по ширине
				k1 = (double) rr.width / (double) biDisplaying.getWidth();
				panelPoint.x = Math.round((float)(picPoint.x * k1));
				panelPoint.y = Math.round((float)(((rr.height - biDisplaying.getHeight() * k1) / 2.0 ) + picPoint.y * k1));
			}
		}
		return panelPoint;
	}
	

}
