package rasterdata;

import transforms.Col;

/**
 * Z-Buffer
 * 
 * @author Milan
 *
 */
public class ZBufferTest {
	private DepthBuffer depthBuffer;
	private ImageBuffer imageBuffer;

	public ZBufferTest(DepthBuffer depthBuffer, ImageBuffer imageBuffer) {
		this.depthBuffer = depthBuffer;
		this.imageBuffer = imageBuffer;

	}

	public void setPixel(int x, int y, double z, Col col) {
		if (zValueTest(x, y, z)) {
			depthBuffer.setPixel(x, y, z);
			imageBuffer.setPixel(x, y, col);
		}
	}

	public DepthBuffer getDepthBuffer() {
		return depthBuffer;
	}

	public void setDepthBuffer(DepthBuffer db) {
		this.depthBuffer = db;
	}

	public ImageBuffer getImageBuffer() {
		return imageBuffer;
	}

	public void setImageBuffer(ImageBuffer imageBuffer) {

		this.imageBuffer = imageBuffer;
	}

	public boolean zValueTest(int x, int y, double z) {
		return (x > 0 && y > 0 && x < depthBuffer.getWidth() - 1 && y < depthBuffer.getHeight() - 1
				&& z < depthBuffer.getPixel(x, y));
	}

	public void clear(int width, int height, Col bgColor) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				depthBuffer.setPixel(i, j, 1.0);
				imageBuffer.setPixel(i, j, bgColor);
			}

		}
	}

	public void draw() {
		for (int i = 0; i < imageBuffer.getWidth(); i++) {
			for (int j = 0; j < imageBuffer.getHeight(); j++) {
				imageBuffer.getImg().setRGB(i, j, imageBuffer.getPixel(i, j).getRGB());
			}
		}
	}
}
