package rasterdata;

/**
 * Depth Buffer
 * 
 * @author Milan
 *
 */
public class DepthBuffer implements Raster<Double> {
	private int width;
	private int height;
	private double[][] buffer;

	public DepthBuffer(int width, int height) {
		super();
		if (width > 0 && height > 0) {
			this.width = width;
			this.height = height;
			buffer = new double[width][height];
			clean();
		}
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public Double getPixel(int x, int y) {
		if (isPointValid(x, y)) {

			return buffer[x][y];
		}
		return null;
	}

	@Override
	public void setPixel(int x, int y, Double pixel) {
		if (pixel >= 0 && pixel <= 1)
			if (isPointValid(x, y)) {

				buffer[x][y] = pixel;
			}
	}

	@Override
	public void clean() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				buffer[x][y] = 1.0;
			}
		}
	}

	private boolean isPointValid(int x, int y) {
		return (x < width && x >= 0 && y < height && y >= 0);

	}

}
