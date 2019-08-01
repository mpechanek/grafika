package render;

import model.Vertex;
import rasterdata.ImageBuffer;
import rasterdata.ZBufferTest;
import transforms.Col;
import transforms.Point3D;

/**
 * Rasterizer
 * 
 * @author Milan
 *
 */
public class Rasterizer {
	private ImageBuffer image;
	private ZBufferTest zTest;

	public Rasterizer(ZBufferTest zTest) {
		this.image = zTest.getImageBuffer();
		this.zTest = zTest;
	}

	public void rasterLine(Vertex vertex1, Vertex vertex2) {

		Vertex va = vertex1.dehomog();
		Vertex vb = vertex2.dehomog();

		if ((Math.min(va.getX(), vb.getX()) > 1.0f) || (Math.min(va.getX(), vb.getX()) < -1.0f)
				|| (Math.min(va.getY(), vb.getY()) > 1.0f) || (Math.min(va.getY(), vb.getY()) < -1.0f)
				|| (Math.min(va.getZ(), vb.getZ()) > 1.0f) || (Math.min(va.getZ(), vb.getZ()) < -1.0f)) {
			return;
		}
		va.setPos(new Point3D((image.getWidth() - 1) * (va.getPos().getX() + 1) / 2,
				(image.getHeight() - 1) * (va.getPos().getY() + 1) / 2, va.getZ(), va.getW()));
		vb.setPos(new Point3D((image.getWidth() - 1) * (vb.getPos().getX() + 1) / 2,
				(image.getHeight() - 1) * (vb.getPos().getY() + 1) / 2, vb.getZ(), vb.getW()));

		int dx = (int) (vb.getX() - va.getX());
		int dy = (int) (vb.getY() - va.getY());

		int pocetKroku = Math.max(Math.abs(dx), Math.abs(dy));

		double px = (double) dx / pocetKroku;
		double py = (double) dy / pocetKroku;

		double x = va.getX();
		double y = va.getY();
		double z = va.getZ();

		for (int i = 1; i <= pocetKroku + 1; i++) {
			zTest.setPixel((int) x, (int) y, z, new Col(0x0000ff));
			x += px;
			y += py;
		}

	}

	public void rasterTriangle(Vertex va, Vertex vb, Vertex vc, Col col) {

		va = va.dehomog();
		vb = vb.dehomog();
		vc = vc.dehomog();

		if ((Math.min(Math.min(va.getX(), vb.getX()), vc.getX()) > 1.0f)
				|| (Math.max(Math.max(va.getX(), vb.getX()), vc.getX()) < -1.0f)
				|| (Math.min(Math.min(va.getY(), vb.getY()), vc.getY()) > 1.0f)
				|| (Math.max(Math.max(va.getY(), vb.getY()), vc.getY()) < -1.0f)
				|| (Math.min(Math.min(va.getZ(), vb.getZ()), vc.getZ()) > 1.0f)
				|| (Math.max(Math.max(va.getZ(), vb.getZ()), vc.getZ()) < 0.0f)) {
			return;
		}

		va.setPos(new Point3D((image.getWidth() - 1) * (va.getPos().getX() + 1) / 2,
				(image.getHeight() - 1) * (va.getPos().getY() + 1) / 2, va.getZ(), va.getW()));
		vb.setPos(new Point3D((image.getWidth() - 1) * (vb.getPos().getX() + 1) / 2,
				(image.getHeight() - 1) * (vb.getPos().getY() + 1) / 2, vb.getZ(), vb.getW()));
		vc.setPos(new Point3D((image.getWidth() - 1) * (vc.getPos().getX() + 1) / 2,
				(image.getHeight() - 1) * (vc.getPos().getY() + 1) / 2, vc.getZ(), vc.getW()));

		if (va.getY() > vb.getY()) {
			Vertex pom = va;
			va = vb;
			vb = pom;
		}
		if (vb.getY() > vc.getY()) {
			Vertex pom = vb;
			vb = vc;
			vc = pom;
		}
		if (va.getY() > vb.getY()) {
			Vertex pom = va;
			va = vb;
			vb = pom;
		}

		for (int y = Math.max((int) va.getY() + 1, 0); y <= Math.min(vb.getY(), image.getHeight() - 1); y++) {

			double s1 = (y - va.getY()) / (vb.getY() - va.getY());
			double s2 = (y - va.getY()) / (vc.getY() - va.getY());

			double x1 = (va.getX() * (1 - s1) + vb.getX() * s1);
			double x2 = (va.getX() * (1 - s2) + vc.getX() * s2);

			double z1 = (va.getZ() * (1 - s1) + vb.getZ() * s1);
			double z2 = (va.getZ() * (1 - s2) + vc.getZ() * s2);

			Col c1 = va.getColor().mul(1 - s1).add(vb.getColor().mul(s1));
			Col c2 = va.getColor().mul(1 - s2).add(vc.getColor().mul(s2));

			if (x1 > x2) {
				double pom = x1;
				x1 = x2;
				x2 = pom;
				pom = z1;
				z1 = z2;
				z2 = pom;

				Col temp = c1;
				c1 = c2;
				c2 = temp;
			}
			for (int x = Math.max((int) x1 + 1, 0); x <= Math.min(x2, image.getWidth() - 1); x++) {
				double t = (x - x1) / (x2 - x1);
				double z = (z1 * (1.0 - t) + z2 * t);

				Col color = c1.mul(1 - t).add(c2.mul(t));
				zTest.setPixel(x, y, z, color);

			}

		}

		for (int y = Math.max((int) vb.getY() + 1, 0); y <= Math.min(vc.getY(), image.getHeight() - 1); y++) {
			double s1 = (y - vb.getY()) / (vc.getY() - vb.getY());
			double s2 = (y - va.getY()) / (vc.getY() - va.getY());

			double x1 = (vb.getX() * (1 - s1) + vc.getX() * s1);
			double x2 = (va.getX() * (1 - s2) + vc.getX() * s2);

			double z1 = (vb.getZ() * (1 - s1) + vc.getZ() * s1);
			double z2 = (va.getZ() * (1 - s2) + vc.getZ() * s2);

			Col c1 = vb.getColor().mul(1 - s1).add(vc.getColor().mul(s1));
			Col c2 = va.getColor().mul(1 - s2).add(vc.getColor().mul(s2));

			if (x1 > x2) {
				double pom = x1;
				x1 = x2;
				x2 = pom;
				pom = z1;
				z1 = z2;
				z2 = pom;

				Col temp = c1;
				c1 = c2;
				c2 = temp;
			}
			for (int x = Math.max((int) x1 + 1, 0); x <= Math.min(x2, image.getWidth() - 1); x++) {
				double t = (x - x1) / (x2 - x1);
				double z = (z1 * (1.0 - t) + z2 * t);
				Col color = c1.mul(1 - t).add(c2.mul(t));
				zTest.setPixel(x, y, z, color);

			}
		}

	}
}
