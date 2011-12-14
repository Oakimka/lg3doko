package org.oakimsoft;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StereoViewer {
	/**
	 * Source Image
	 */
	public BufferedImage biSource = null;
	/**
	 * Converted Image
	 */
	public BufferedImage biConverted = null;

	/**
	 * Converted Left Image
	 */
	public BufferedImage biLeft = null;
	/**
	 * Converted Left Image
	 */
	public BufferedImage biRight = null;

	public static final int HORIZONTAL_FULLSIZE_LR = 0;
	public static final int HORIZONTAL_FULLSIZE_RL = 1;
	public static final int HORIZONTAL_HALFSIZE_LR = 2;
	public static final int HORIZONTAL_HALFSIZE_RL = 3;
	public static final int VERTICAL_FULLSIZE_LR = 10;
	public static final int VERTICAL_FULLSIZE_RL = 11;
	public static final int VERTICAL_HALFSIZE_LR = 12;
	public static final int VERTICAL_HALFSIZE_RL = 13;
	public static final int AS_IS = 14;

	public static final int STRETCH_FULLSCREEN = 0;
	public static final int STRETCH_SIZE100 = 1;
	public static final int STRETCH_SIZE050 = 2;
	public static final int STRETCH_SIZE025 = 3;

	private int srcType = 0;

	/**
	 * Растягивание
	 */
	private int stretching = STRETCH_FULLSCREEN;

	/**
	 * Height of output container
	 */
	private int targetHeight = 0;
	/**
	 * Width of output container
	 */
	private int targetWidth = 0;

	/**
	 * Параллакс в пикселях
	 */
	public int parallax_pix = 0;

	public StereoViewer() {

	}

	/**
	 * Load stereo file
	 */
	public void load(File vFile) {
		biSource = null;
		try {
			biSource = ImageIO.read(vFile);
		} catch (IOException e) {
			System.out.println(e);
		}
		System.out.print(biSource.getWidth());
		System.out.print("x");
		System.out.println(biSource.getHeight());
	}

	/**
	 * 
	 HORIZONTAL_FULLSIZE_LR = 0; <br>
	 * HORIZONTAL_FULLSIZE_RL = 1; <br>
	 * HORIZONTAL_HALFSIZE_LR = 2; <br>
	 * HORIZONTAL_HALFSIZE_RL = 3; <br>
	 * VERTICAL_FULLSIZE_LR = 10; <br>
	 * VERTICAL_FULLSIZE_RL = 11; <br>
	 * VERTICAL_HALFSIZE_LR = 12; <br>
	 * VERTICAL_HALFSIZE_RL = 13; <br>
	 * AS_IS = 14; <br>
	 * 
	 * @param stereoType
	 * 
	 */
	public void setStereoType(int stereoType) {
		srcType = stereoType;

	}

	public int getStereoType() {
		return srcType;
	}

	/**
	 * public static final int STRETCH_FULLSCREEN = 0; <br>
	 * public static final int STRETCH_SIZE100 = 1; <br>
	 * public static final int STRETCH_SIZE050 = 2; <br>
	 * public static final int STRETCH_SIZE025 = 3; <br>
	 * 
	 * 
	 * @param stretch
	 */
	public void setStretch(int stretch) {
		this.stretching = stretch;
	}

	public int getStretch() {
		return this.stretching;
	}

	/**
	 * 
	 * @param pWidth
	 *            Ширина
	 * @param pHeight
	 *            Высота <i></i>
	 * @return 0 - ok, -1 проблема
	 */
	public int setTargetSize(int pWidth, int pHeight) {
		this.targetHeight = pHeight;
		this.targetWidth = pWidth;
		this.biConverted = null;
		// if ((this.targetHeight)!=(this.targetHeight/2*2)){
		// this.targetHeight-- ;
		// }
		// if ((this.targetHeight<=0)||(this.targetWidth<=0)){
		// this.targetHeight = 0;
		// this.targetWidth = 0;
		// return -1;
		// }
		return 0;
	}

	/**
	 * Convert source image
	 */
	public void convert() {
		if (this.biSource == null) {
			return;
		}
		this.biConverted = null;
		this.biLeft = null;
		this.biRight = null;
		int newWidth = 0; // Размер половинок для последующей склейки
		int newHeight = 0;// Размер половинок для последующей склейки

		int realWidth = 0; // Размер реального изображения
		int realHeight = 0; // Размер реального изображения

		double koef = 1d; // Коэффициент масштабирования для базовой картинки
							// изображения

		if ((srcType == HORIZONTAL_FULLSIZE_LR)
				|| (srcType == HORIZONTAL_FULLSIZE_RL)) {

			realWidth = biSource.getWidth() / 2;
			realHeight = biSource.getHeight();

		}
		if ((srcType == HORIZONTAL_HALFSIZE_LR)
				|| (srcType == HORIZONTAL_HALFSIZE_RL)
				|| (srcType == VERTICAL_HALFSIZE_LR)
				|| (srcType == VERTICAL_HALFSIZE_RL)) {

			realWidth = biSource.getWidth();
			realHeight = biSource.getHeight();

		}
		if ((srcType == VERTICAL_FULLSIZE_LR)
				|| (srcType == VERTICAL_FULLSIZE_RL)) {

			realWidth = biSource.getWidth();
			realHeight = biSource.getHeight() / 2;

		}

		if ((srcType == AS_IS)) {
			realWidth = biSource.getWidth();
			realHeight = biSource.getHeight();
		}

		// а попробуем ка выровнять по высоте
		if ((double) this.targetHeight / (double) realHeight
				* (double) realWidth <= this.targetWidth) {
			koef = (double) this.targetHeight / (double) realHeight;
		} else {
			koef = (double) this.targetWidth / (double) realWidth;
		}

		if (this.stretching == StereoViewer.STRETCH_SIZE100) {
			koef = 1d;
		}
		if (this.stretching == StereoViewer.STRETCH_SIZE050) {
			koef = 0.5d;
		}
		if (this.stretching == StereoViewer.STRETCH_SIZE025) {
			koef = 0.25d;
		}

		newHeight = (int) Math.round(realHeight * koef);
		newWidth = (int) Math.round(realWidth * koef)
				+ Math.abs(this.parallax_pix);

		this.biLeft = new BufferedImage(newWidth, newHeight / 2,
				BufferedImage.TYPE_INT_RGB);
		this.biRight = new BufferedImage(newWidth, newHeight / 2,
				BufferedImage.TYPE_INT_RGB);

		this.biConverted = new BufferedImage(newWidth, newHeight,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g2dConverted = (Graphics2D) this.biConverted.getGraphics();
		Graphics2D g2dLeft = (Graphics2D) this.biLeft.getGraphics();
		Graphics2D g2dRight = (Graphics2D) this.biRight.getGraphics();

		g2dConverted.setBackground(Color.black);
		g2dConverted.clearRect(0, 0, newWidth, newHeight);

		if ((srcType == AS_IS)) {
			g2dConverted.drawImage(this.biSource, 0, 0, newWidth, newHeight, 0,
					0, biSource.getWidth(), biSource.getHeight(), null);
			return;
		}

		int leftOffset = 0;
		int rightOffset = 0;

		if (this.parallax_pix > 0) {
			leftOffset = 0;
			rightOffset = Math.abs(this.parallax_pix);
		} else {
			leftOffset = Math.abs(this.parallax_pix);
			rightOffset = 0;
		}

		if ((srcType == HORIZONTAL_FULLSIZE_LR)
				|| (srcType == HORIZONTAL_FULLSIZE_RL)
				|| (srcType == HORIZONTAL_HALFSIZE_LR)
				|| (srcType == HORIZONTAL_HALFSIZE_RL)) {

			g2dLeft.drawImage(this.biSource, 0, 0, newWidth, newHeight / 2, 0,
					0, biSource.getWidth() / 2, biSource.getHeight(), null);
			g2dRight.drawImage(this.biSource, 0, 0, newWidth, newHeight / 2,
					biSource.getWidth() / 2 + 1, 0, biSource.getWidth(),
					biSource.getHeight(), null);
			
			BufferedImage biFirst;
			BufferedImage biSecond;

			if ((srcType == HORIZONTAL_FULLSIZE_LR)
    			|| (srcType == HORIZONTAL_HALFSIZE_LR)) {
				biFirst = this.biLeft;
				biSecond = this.biRight;
			}else{
				biFirst = this.biRight;
				biSecond = this.biLeft;
			}
			

			for (int rowNumber = 0; rowNumber <= this.biLeft.getHeight(); rowNumber++) {
					g2dConverted.drawImage(biFirst, 0 + leftOffset,
							rowNumber * 2, newWidth - rightOffset,
							rowNumber * 2 + 1, 0, rowNumber, newWidth,
							rowNumber + 1, null);

					g2dConverted.drawImage(biSecond, 0 + rightOffset,
							rowNumber * 2 + 1, newWidth - leftOffset,
							rowNumber * 2 + 2, 0, rowNumber, newWidth,
							rowNumber + 1, null);
	
			}
		}

	
		if ((srcType == VERTICAL_FULLSIZE_LR)
				|| (srcType == VERTICAL_FULLSIZE_RL)
				|| (srcType == VERTICAL_HALFSIZE_LR)
				|| (srcType == VERTICAL_HALFSIZE_RL)) {

			g2dLeft.drawImage(this.biSource, 0, 0, newWidth, newHeight / 2, 0,
					0, biSource.getWidth(), biSource.getHeight() / 2, null);
			g2dRight.drawImage(this.biSource, 0, 0, newWidth, newHeight / 2, 0,
					biSource.getHeight() / 2 + 1, biSource.getWidth(),
					biSource.getHeight(), null);

			BufferedImage biFirst;
			BufferedImage biSecond;

			if ((srcType == VERTICAL_FULLSIZE_LR)
					|| (srcType == VERTICAL_HALFSIZE_LR)) {
				biFirst = this.biLeft;
				biSecond = this.biRight;
			}else{
				biFirst = this.biRight;
				biSecond = this.biLeft;
			}
						
			for (int rowNumber = 0; rowNumber <= this.biLeft.getHeight(); rowNumber++) {
					g2dConverted.drawImage(biFirst, 0+ leftOffset, rowNumber * 2,
							newWidth-rightOffset, rowNumber * 2 + 1, 0, rowNumber,
							newWidth, rowNumber + 1, null);
					g2dConverted.drawImage(biSecond, 0+rightOffset, rowNumber * 2 + 1,
							newWidth-leftOffset, rowNumber * 2 + 2, 0, rowNumber,
							newWidth, rowNumber + 1, null);

			}
		}

		// try {
		// ImageIO.write(biLeft,"BMP", new File("C:\\temp\\left.bmp"));
		// ImageIO.write(biRight,"BMP", new File("C:\\temp\\right.bmp"));
		// } catch (IOException e) {
		// System.out.println(e);
		// }

	}

}
