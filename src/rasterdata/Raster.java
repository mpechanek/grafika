package rasterdata;

/**
 * Rozhraní Raster
 * 
 * @author Milan
 *
 * @param <C>
 */
public interface Raster<C> {
	int getWidth();

	int getHeight();

	C getPixel(int x, int y);

	void setPixel(int x, int y, C point);

	void clean();
}
