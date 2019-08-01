package rasterdata;

/**
 * Image Buffer
 *@author Milan 
 */
import java.awt.image.BufferedImage;

import transforms.Col;

public class ImageBuffer implements Raster<Col> {
	private BufferedImage bufferedImage;

	public ImageBuffer(BufferedImage img) {
		this.bufferedImage = img;
	}

	@Override
	public int getWidth() {
		return bufferedImage.getWidth();
	}

	@Override
	public int getHeight() {
		return bufferedImage.getHeight();
	}

	@Override
	public Col getPixel(int x, int y) {
		if (isPointValid(x, y)) {
			if (bufferedImage != null) {
				return new Col(bufferedImage.getRGB(x, y));
			}
		}
		return null;
	}

	@Override
	public void setPixel(int x, int y, Col point) {
		bufferedImage.setRGB(x, y, point.getRGB());
	}

	public BufferedImage getImg() {
		return bufferedImage;
	}

	public void setImg(BufferedImage img) {
		this.bufferedImage = img;
	}

	@Override
	public void clean() {
		for (int i = 0; i < bufferedImage.getWidth(); i++) {
			for (int j = 0; j < bufferedImage.getHeight(); j++) {
				bufferedImage.setRGB(i, j, 0xcccccc);
			}
		}
	}

	private boolean isPointValid(int x, int y) {
		return (x >= 0 && x < bufferedImage.getWidth() && y >= 0 && y < bufferedImage.getHeight());

	}

}
