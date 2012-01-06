package org.oakimsoft;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StereoComposer {
	public BufferedImage biLeftImage = null;
	public BufferedImage biRightImage = null;
	public BufferedImage biResult = null;

	public int ResultWidth = 0;
	public int ResultHeight = 0;
	public int LeftOffsetX = 0;
	public int LeftOffsetY = 0;
	public double LeftRotation = 0;
	public int RightOffsetX = 0;
	public int RightOffsetY = 0;
	public double RightRotation = 0;

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

		BufferedImage biLocalLeft = new BufferedImage(ResultWidth, ResultHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2dLeft = (Graphics2D) biLocalLeft.getGraphics();
		g2dLeft.drawImage(this.biLeftImage, 
				0 + this.LeftOffsetX, 0 + this.LeftOffsetY, biLocalLeft.getWidth() + this.LeftOffsetX, biLocalLeft.getHeight() + this.LeftOffsetY, 
				0, 0,ResultWidth, ResultHeight, null);
		// g2dLeft.rotate(30);// , biLocalLeft.getWidth()/2,  biLocalLeft.getHeight()/2);
		
		BufferedImage biLocalLeftRot = new BufferedImage(ResultWidth, ResultHeight, BufferedImage.TYPE_INT_RGB);
		AffineTransform atLeft = new AffineTransform();
		atLeft.rotate(Math.toRadians(this.LeftRotation), biLocalLeft.getWidth()/2.0,  biLocalLeft.getHeight()/2.0);
		BufferedImageOp bio;
	    bio = new AffineTransformOp(atLeft, AffineTransformOp.TYPE_BILINEAR);
	    bio.filter(biLocalLeft, biLocalLeftRot);
	    biLocalLeft = null;
		
		
		

		BufferedImage biLocalRight = new BufferedImage(ResultWidth, ResultHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2dRight = (Graphics2D) biLocalRight.getGraphics();
		g2dRight.drawImage(this.biRightImage, 
				0 + this.RightOffsetX, 0 + this.RightOffsetY, biLocalRight.getWidth() + this.RightOffsetX, biLocalRight.getHeight() + this.RightOffsetY, 
				0, 0,ResultWidth, ResultHeight, null);
		
		BufferedImage biLocalRightRot = new BufferedImage(ResultWidth, ResultHeight, BufferedImage.TYPE_INT_RGB);
		AffineTransform atRight = new AffineTransform();
		atRight.rotate(Math.toRadians(this.RightRotation), biLocalRight.getWidth()/2.0,  biLocalRight.getHeight()/2.0);
		BufferedImageOp bioRight;
		bioRight = new AffineTransformOp(atRight, AffineTransformOp.TYPE_BILINEAR);
		bioRight.filter(biLocalRight, biLocalRightRot);
	    biLocalRight = null;

		
		this.biResult = new BufferedImage(ResultWidth * 2, ResultHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) this.biResult.getGraphics();
		g2d.setBackground(Color.GREEN);
		g2d.clearRect(0, 0, ResultWidth * 2, ResultHeight);
		
		g2d.drawImage(biLocalLeftRot, 0, 0, ResultWidth , ResultHeight, 0, 0, biLocalLeftRot.getWidth() , biLocalLeftRot.getHeight(), null);
		g2d.drawImage(biLocalRightRot, ResultWidth, 0, ResultWidth *2 , ResultHeight, 0, 0, biLocalRightRot.getWidth() , biLocalRightRot.getHeight(), null);
		
		

	}

}
