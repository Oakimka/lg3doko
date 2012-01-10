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
	public int				resultOffsetX		= 0;
	public int				resultOffsetY		= 0;
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
		biLeftImage = null;
		try {
			biLeftImage = ImageIO.read(vFile);
			if ((biLeftImage != null) && (biRightImage != null)) {
				this.ResultWidth = Math.min(biLeftImage.getWidth(), biRightImage.getWidth());
				this.ResultHeight = Math.min(biLeftImage.getHeight(), biRightImage.getHeight());
			}
		} catch (IOException e) {
			System.out.println(e);
		}
		System.out.print(biLeftImage.getWidth());
		System.out.print("x");
		System.out.println(biLeftImage.getHeight());
	}

	/**
	 * Load image
	 */
	public void loadRightImage(File vFile) {
		biRightImage = null;
		try {
			biRightImage = ImageIO.read(vFile);
			if ((biLeftImage != null) && (biRightImage != null)) {
				this.ResultWidth = Math.min(biLeftImage.getWidth(), biRightImage.getWidth());
				this.ResultHeight = Math.min(biLeftImage.getHeight(), biRightImage.getHeight());
			}
		} catch (IOException e) {
			System.out.println(e);
		}
		System.out.print(biRightImage.getWidth());
		System.out.print("x");
		System.out.println(biRightImage.getHeight());
	}

	public void convert() {
		this.biResult = null;
		if ((ResultWidth == 0) || (ResultHeight == 0))
			return;
		if ((this.biLeftImage == null) || (this.biRightImage == null))
			return;
		if ((this.biLeftImage.getWidth() != this.biRightImage.getWidth()) || (this.biLeftImage.getHeight() != this.biRightImage.getHeight()))
			return;

		if (this.alignmentType == 0) {

			BufferedImage biLocalLeft = new BufferedImage(this.biLeftImage.getWidth() * 3, this.biLeftImage.getHeight() * 3, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2dLeft = (Graphics2D) biLocalLeft.getGraphics();
			g2dLeft.drawImage(this.biLeftImage,
					this.biLeftImage.getWidth(), this.biLeftImage.getHeight(), this.biLeftImage.getWidth() * 2, this.biLeftImage.getHeight() * 2,
					0, 0, this.biLeftImage.getWidth(), this.biLeftImage.getHeight(), null);
			BufferedImage biLocalLeftRot = new BufferedImage(biLocalLeft.getWidth(), biLocalLeft.getHeight(), BufferedImage.TYPE_INT_RGB);
			AffineTransform atLeft = new AffineTransform();
			atLeft.rotate(Math.toRadians(this.LeftRotation), this.biLeftImage.getWidth() + this.LeftAnchor.x, this.biLeftImage.getHeight() + this.LeftAnchor.y);
			BufferedImageOp bioLeft;
			bioLeft = new AffineTransformOp(atLeft, AffineTransformOp.TYPE_BILINEAR);
			bioLeft.filter(biLocalLeft, biLocalLeftRot);
			biLocalLeft = null;
//			try {
//				ImageIO.write( biLocalLeftRot, "BMP", new File("C:\\TEMP\\1.BMP"));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			

			int dx = this.LeftAnchor.x - this.RightAnchor.x;
			int dy = this.LeftAnchor.y - this.RightAnchor.y;

			BufferedImage biLocalRight = new BufferedImage(this.biRightImage.getWidth() * 3, this.biRightImage.getHeight() * 3, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2dRight = (Graphics2D) biLocalRight.getGraphics();
			g2dRight.drawImage(this.biRightImage,
					this.biRightImage.getWidth() + dx, this.biRightImage.getHeight() + dy, this.biRightImage.getWidth() * 2 + dx, this.biRightImage.getHeight() * 2 + dy,
					0, 0, this.biRightImage.getWidth(), this.biRightImage.getHeight(), null);
			BufferedImage biLocalRightRot = new BufferedImage(biLocalRight.getWidth(), biLocalRight.getHeight(), BufferedImage.TYPE_INT_RGB);
			AffineTransform atRight = new AffineTransform();
			atRight.rotate(Math.toRadians(this.RightRotation), this.biRightImage.getWidth() + this.RightAnchor.x, this.biRightImage.getHeight() + this.RightAnchor.y);
			BufferedImageOp bioRight;
			bioRight = new AffineTransformOp(atRight, AffineTransformOp.TYPE_BILINEAR);
			bioRight.filter(biLocalRight, biLocalRightRot);
			biLocalRight = null;

			
			this.biResult = new BufferedImage(ResultWidth * 2, ResultHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D) this.biResult.getGraphics();
			g2d.setBackground(Color.GREEN);
			g2d.clearRect(0, 0, ResultWidth * 2, ResultHeight);

			g2d.drawImage(biLocalLeftRot, 0, 0, this.ResultWidth, this.ResultHeight, 
					this.biLeftImage.getWidth()+this.LeftAnchor.x+this.resultOffsetX, 
					this.biLeftImage.getHeight()+this.LeftAnchor.y+this.resultOffsetY, 
					this.biLeftImage.getWidth()+this.LeftAnchor.x+this.resultOffsetX + this.ResultWidth, 
					this.biLeftImage.getHeight()+this.LeftAnchor.y+this.resultOffsetY + this.ResultHeight,  null);
			
			g2d.drawImage(biLocalRightRot, ResultWidth, 0, ResultWidth * 2, ResultHeight, 
					this.biRightImage.getWidth()+this.RightAnchor.x+this.resultOffsetX, this.biRightImage.getHeight()+this.RightAnchor.y+this.resultOffsetY, 
					this.biRightImage.getWidth()+this.RightAnchor.x+this.resultOffsetX + this.ResultWidth, 
					this.biRightImage.getHeight()+this.RightAnchor.y+this.resultOffsetY + this.ResultHeight,  null);

		}

		if (this.alignmentType == 2) {

			BufferedImage biLocalRight = new BufferedImage(this.biRightImage.getWidth() * 3, this.biRightImage.getHeight() * 3, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2dRight = (Graphics2D) biLocalRight.getGraphics();
			g2dRight.drawImage(this.biRightImage,
					this.biRightImage.getWidth(), this.biRightImage.getHeight(), this.biRightImage.getWidth() * 2, this.biRightImage.getHeight() * 2,
					0, 0, this.biRightImage.getWidth(), this.biRightImage.getHeight(), null);
			BufferedImage biLocalRightRot = new BufferedImage(biLocalRight.getWidth(), biLocalRight.getHeight(), BufferedImage.TYPE_INT_RGB);
			AffineTransform atRight = new AffineTransform();
			atRight.rotate(Math.toRadians(this.RightRotation), this.biRightImage.getWidth() + this.RightAnchor.x, this.biRightImage.getHeight() + this.RightAnchor.y);
			BufferedImageOp bioRight;
			bioRight = new AffineTransformOp(atRight, AffineTransformOp.TYPE_BILINEAR);
			bioRight.filter(biLocalRight, biLocalRightRot);
			biLocalRight = null;

			int dx = this.RightAnchor.x - this.LeftAnchor.x;
			int dy = this.RightAnchor.y - this.LeftAnchor.y;

			BufferedImage biLocalLeft = new BufferedImage(this.biLeftImage.getWidth() * 3, this.biLeftImage.getHeight() * 3, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2dLeft = (Graphics2D) biLocalLeft.getGraphics();
			g2dLeft.drawImage(this.biLeftImage,
					this.biLeftImage.getWidth() + dx, this.biLeftImage.getHeight() + dy, this.biLeftImage.getWidth() * 2 + dx, this.biLeftImage.getHeight() * 2 + dy,
					0, 0, this.biLeftImage.getWidth(), this.biLeftImage.getHeight(), null);
			BufferedImage biLocalLeftRot = new BufferedImage(biLocalLeft.getWidth(), biLocalLeft.getHeight(), BufferedImage.TYPE_INT_RGB);
			AffineTransform atLeft = new AffineTransform();
			atLeft.rotate(Math.toRadians(this.LeftRotation), this.biLeftImage.getWidth() + this.LeftAnchor.x, this.biLeftImage.getHeight() + this.LeftAnchor.y);
			BufferedImageOp bioLeft;
			bioLeft = new AffineTransformOp(atLeft, AffineTransformOp.TYPE_BILINEAR);
			bioLeft.filter(biLocalLeft, biLocalLeftRot);
			biLocalLeft = null;

			this.biResult = new BufferedImage(ResultWidth * 2, ResultHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D) this.biResult.getGraphics();
			g2d.setBackground(Color.GREEN);
			g2d.clearRect(0, 0, ResultWidth * 2, ResultHeight);

			g2d.drawImage(biLocalLeftRot, 0, 0, ResultWidth, ResultHeight, 
					this.biLeftImage.getWidth()+this.LeftAnchor.x+this.resultOffsetX, this.biLeftImage.getHeight()+this.LeftAnchor.y+this.resultOffsetY, 
					this.biLeftImage.getWidth()+this.LeftAnchor.x+this.resultOffsetX + this.ResultWidth, 
					this.biLeftImage.getHeight()+this.LeftAnchor.y+this.resultOffsetY + this.ResultHeight,  null);
			
			g2d.drawImage(biLocalRightRot, ResultWidth, 0, ResultWidth * 2, ResultHeight, 
					this.biRightImage.getWidth()+this.RightAnchor.x+this.resultOffsetX, this.biRightImage.getHeight()+this.RightAnchor.y+this.resultOffsetY, 
					this.biRightImage.getWidth()+this.RightAnchor.x+this.resultOffsetX + this.ResultWidth, 
					this.biRightImage.getHeight()+this.RightAnchor.y+this.resultOffsetY + this.ResultHeight,  null);

		}
		
		
	}

	/*
	public void convertPrev() {
		this.biResult = null;
		if ((ResultWidth == 0) || (ResultHeight == 0))
			return;

		BufferedImage biLocalLeft = new BufferedImage(ResultWidth, ResultHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2dLeft = (Graphics2D) biLocalLeft.getGraphics();
		g2dLeft.drawImage(this.biLeftImage, 0 + this.LeftOffsetX, 0 + this.LeftOffsetY, biLocalLeft.getWidth() + this.LeftOffsetX, biLocalLeft.getHeight() + this.LeftOffsetY, 0, 0, ResultWidth,
				ResultHeight, null);
		// g2dLeft.rotate(30);// , biLocalLeft.getWidth()/2,
		// biLocalLeft.getHeight()/2);

		BufferedImage biLocalLeftRot = new BufferedImage(ResultWidth, ResultHeight, BufferedImage.TYPE_INT_RGB);
		AffineTransform atLeft = new AffineTransform();
		atLeft.rotate(Math.toRadians(this.LeftRotation), biLocalLeft.getWidth() / 2.0, biLocalLeft.getHeight() / 2.0);
		BufferedImageOp bio;
		bio = new AffineTransformOp(atLeft, AffineTransformOp.TYPE_BILINEAR);
		bio.filter(biLocalLeft, biLocalLeftRot);
		biLocalLeft = null;

		BufferedImage biLocalRight = new BufferedImage(ResultWidth, ResultHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2dRight = (Graphics2D) biLocalRight.getGraphics();
		g2dRight.drawImage(this.biRightImage, 0 + this.RightOffsetX, 0 + this.RightOffsetY, biLocalRight.getWidth() + this.RightOffsetX, biLocalRight.getHeight() + this.RightOffsetY, 0, 0,
				ResultWidth, ResultHeight, null);

		BufferedImage biLocalRightRot = new BufferedImage(ResultWidth, ResultHeight, BufferedImage.TYPE_INT_RGB);
		AffineTransform atRight = new AffineTransform();
		atRight.rotate(Math.toRadians(this.RightRotation), biLocalRight.getWidth() / 2.0, biLocalRight.getHeight() / 2.0);
		BufferedImageOp bioRight;
		bioRight = new AffineTransformOp(atRight, AffineTransformOp.TYPE_BILINEAR);
		bioRight.filter(biLocalRight, biLocalRightRot);
		biLocalRight = null;

		this.biResult = new BufferedImage(ResultWidth * 2, ResultHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) this.biResult.getGraphics();
		g2d.setBackground(Color.GREEN);
		g2d.clearRect(0, 0, ResultWidth * 2, ResultHeight);

		g2d.drawImage(biLocalLeftRot, 0, 0, ResultWidth, ResultHeight, 0, 0, biLocalLeftRot.getWidth(), biLocalLeftRot.getHeight(), null);
		g2d.drawImage(biLocalRightRot, ResultWidth, 0, ResultWidth * 2, ResultHeight, 0, 0, biLocalRightRot.getWidth(), biLocalRightRot.getHeight(), null);

	}
	*/

}
