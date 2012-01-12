package org.oakimsoft;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StereoComposer {
	public BufferedImage	biLeftImage		= null;
	public BufferedImage	biRightImage	= null;
	public BufferedImage	biResult		= null;
	public Point			LeftAnchor		= new Point(0, 0);
	public Point			RightAnchor		= new Point(0, 0);

	public int				ResultWidth		= 0;
	public int				ResultHeight	= 0;
	public int				ResultZoom		= 100;
	public int				resultOffsetX	= 0;
	public int				resultOffsetY	= 0;
	public double			LeftRotation	= 0;
	public double			RightRotation	= 0;

	/**
	 * Тип выравнивания<br>
	 * 0-левая база<br>
	 * 1-центр<br>
	 * 2-правая база<br>
	 */
	public int				alignmentType	= 0;

	/**
	 * Load image
	 */
	public void loadLeftImage(File vFile) {
		BufferedImage biTemp = null;
		biLeftImage = null;
		try {
			biTemp = ImageIO.read(vFile);
			if (biTemp != null){
				biLeftImage = new BufferedImage(biTemp.getWidth(), biTemp.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = (Graphics2D) biLeftImage.getGraphics();
				g2d.drawImage(biTemp, 0,0, Color.black, null);
			}
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * Load image
	 */
	public void loadRightImage(File vFile) {
		BufferedImage biTemp = null;
		biRightImage = null;
		try {
			biTemp = ImageIO.read(vFile);
			if (biTemp != null){
				biRightImage = new BufferedImage(biTemp.getWidth(), biTemp.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = (Graphics2D) biRightImage.getGraphics();
				g2d.drawImage(biTemp, 0,0, Color.black, null);
			}
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void convert() {
		this.biResult = null;
		if ((ResultWidth == 0) || (ResultHeight == 0))
			return;
		if ((this.biLeftImage == null) || (this.biRightImage == null))
			return;
		if ((this.biLeftImage.getWidth() != this.biRightImage.getWidth()) || (this.biLeftImage.getHeight() != this.biRightImage.getHeight()))
			return;
		

	
		AffineTransform atLeft = new AffineTransform();
		atLeft.rotate(Math.toRadians(this.LeftRotation), this.LeftAnchor.x, this.LeftAnchor.y);
		BufferedImageOp bioLeft;
		bioLeft = new AffineTransformOp(atLeft, AffineTransformOp.TYPE_BILINEAR);
		
		BufferedImage biLocalLeftRot = new BufferedImage(this.biLeftImage.getWidth() , this.biLeftImage.getHeight() , BufferedImage.TYPE_INT_RGB);
		Graphics2D g2dLeft = (Graphics2D) biLocalLeftRot.getGraphics();
		g2dLeft.setBackground(Color.black);
		g2dLeft.clearRect(0,0, biLocalLeftRot.getWidth(),  biLocalLeftRot.getHeight());
		bioLeft.filter(this.biLeftImage, biLocalLeftRot);
		

		AffineTransform atRight = new AffineTransform();
		atRight.rotate(Math.toRadians(this.RightRotation), this.RightAnchor.x, this.RightAnchor.y);
		BufferedImageOp bioRight;
		bioRight = new AffineTransformOp(atRight, AffineTransformOp.TYPE_BILINEAR);
		
		BufferedImage biLocalRightRot = new BufferedImage(this.biRightImage.getWidth() , this.biRightImage.getHeight() , BufferedImage.TYPE_INT_RGB);
		Graphics2D g2dRight = (Graphics2D) biLocalRightRot.getGraphics();
		g2dRight.setBackground(Color.black);
		g2dRight.clearRect(0,0, biLocalRightRot.getWidth(),  biLocalRightRot.getHeight());
		bioRight.filter(this.biRightImage, biLocalRightRot);
		
	
		this.biResult = new BufferedImage(ResultWidth * 2 * this.ResultZoom / 100, ResultHeight * this.ResultZoom / 100, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) this.biResult.getGraphics();
		g2d.setBackground(Color.BLACK);
		g2d.clearRect(0, 0, ResultWidth * 2, ResultHeight);

		g2d.drawImage(biLocalLeftRot, 0, 0, this.ResultWidth * this.ResultZoom / 100, this.ResultHeight * this.ResultZoom / 100,
				this.LeftAnchor.x + this.resultOffsetX,
				this.LeftAnchor.y + this.resultOffsetY,
				this.LeftAnchor.x + this.resultOffsetX + this.ResultWidth,
				this.LeftAnchor.y + this.resultOffsetY + this.ResultHeight, null);

		g2d.drawImage(biLocalRightRot, ResultWidth * this.ResultZoom / 100, 0, ResultWidth * 2 * this.ResultZoom / 100, ResultHeight * this.ResultZoom / 100,
				 this.RightAnchor.x + this.resultOffsetX,
				 this.RightAnchor.y + this.resultOffsetY,
				 this.RightAnchor.x + this.resultOffsetX + this.ResultWidth,
				 this.RightAnchor.y + this.resultOffsetY + this.ResultHeight, null);

	}
	
	public void saveResultImage(){
		
	}

}
