package render;

import java.util.ArrayList;
import java.util.List;

import model.Solid;
import model.Topology.Part;
import rasterdata.ImageBuffer;
import rasterdata.ZBufferTest;
import model.Vertex;
import transforms.Camera;
import transforms.Col;
import transforms.Mat4;
import transforms.Mat4Identity;

/**
 * Renderování
 * 
 * @author Milan
 *
 */
public class Renderer {
	private ImageBuffer imageBuffer;
	private Mat4 finalMatrix = new Mat4Identity();
	private Mat4 proj;
	private Mat4 view;
	private Mat4 model;
	private final double wMin = 0.01;
	private ZBufferTest zTest;
	private Rasterizer rasterizer;
	private Camera cam;

	public Renderer(ImageBuffer imageBuffer, Mat4 projection, Mat4 visible, Mat4 model, ZBufferTest zBuffer,
			Camera camera) {
		this.imageBuffer = imageBuffer;
		this.proj = projection;
		this.view = visible;
		this.model = model;
		this.zTest = zBuffer;
		this.cam = camera;
		rasterizer = new Rasterizer(zBuffer);
	}

	public void draw(Solid solid) {
		finalMatrix = model.mul(cam.getViewMatrix()).mul(proj);
		List<Vertex> vertices = new ArrayList<>();
		for (Vertex verti : solid.getVertices()) {
			vertices.add(verti.mul(finalMatrix));
		}

		for (Part part : solid.getPart()) {
			int index;
			int startIndex;
			switch (part.getPartType()) {
			case LINE:
				startIndex = part.getStartIndex();
				for (int i = 0; i < part.getCount(); i++) {
					index = startIndex + i * 2;

					int index1 = solid.getIndices().get(index);
					int index2 = solid.getIndices().get(index + 1);

					Vertex vertex1 = vertices.get(index1);
					Vertex vertex2 = vertices.get(index2);
					drawLine(vertex1, vertex2);
				}
				break;
			case TRIANGLE:
				startIndex = part.getStartIndex();
				for (int i = 0; i < part.getCount(); i++) {
					index = startIndex + i * 3;

					int index1 = solid.getIndices().get(index);
					int index2 = solid.getIndices().get(index + 1);
					int index3 = solid.getIndices().get(index + 2);

					Vertex vertex1 = vertices.get(index1);
					Vertex vertex2 = vertices.get(index2);
					Vertex vertex3 = vertices.get(index3);
					drawTriangle(vertex1, vertex2, vertex3, vertex1.getColor());
				}
				break;
			default:
				break;
			}
		}
	}

	public Camera getCamera() {
		return cam;
	}

	public void setCamera(Camera cam) {
		this.cam = cam;
	}

	public void drawLine(Vertex vertex1, Vertex vertex2) {
		Vertex temp;
		if (vertex1.getW() < vertex2.getW()) {
			temp = vertex1;
			vertex1 = vertex2;
			vertex2 = temp;
		}
		if (vertex1.getW() < wMin)
			return;
		if (vertex2.getW() < wMin) {
			double t1 = (wMin - vertex2.getW()) / (vertex1.getW() - vertex2.getW());
			Vertex v2 = vertex2.mul(1 - t1).add(vertex1.mul(t1));
			rasterizer.rasterLine(vertex1, v2);
		}
		rasterizer.rasterLine(vertex1, vertex2);
	}

	public void drawTriangle(Vertex va, Vertex vb, Vertex vc, Col col) {
		Vertex temp;
		// øazení (zBuffer)
		if (va.getZ() < vb.getZ()) {
			temp = va;
			va = vb;
			vb = temp;
		}

		if (vb.getZ() < vc.getZ()) {
			temp = vb;
			vb = vc;
			vc = temp;
		}

		if (va.getZ() < vb.getZ()) {
			temp = va;
			va = vb;
			vb = temp;
		}

		double zMin = 0;
		// 1. scénáø (all in)
		if (vc.getZ() > zMin) {
			rasterizer.rasterTriangle(va, vb, vc, col);
			return;
		}

		// 2. scénáø (p3 out)
		if (vb.getZ() > zMin) {
			// t mezi p1 a p3
			double ta = ((zMin - vc.getZ()) / (va.getZ() - vc.getZ()));
			Vertex p3a = vc.mul(1 - ta).add(va.mul(ta));

			double tb = ((zMin - vc.getZ()) / (vb.getZ() - vc.getZ()));
			Vertex p3b = vc.mul(1 - tb).add(vb.mul(tb));

			rasterizer.rasterTriangle(va, vb, p3a, col);
			rasterizer.rasterTriangle(vb, p3a, p3b, col);
			return;
		}
		// 3. scénáø (only p1 in)
		if (va.getZ() > zMin) {
			double t3 = ((zMin - vc.getZ()) / (va.getZ() - vc.getZ()));
			double t2 = ((zMin - vb.getZ()) / (va.getZ() - vb.getZ()));

			vc = vc.mul(1 - t3).add(va.mul(t3));
			vb = vb.mul(1 - t2).add(va.mul(t2));

			rasterizer.rasterTriangle(va, vb, vc, col);
		}

		// 4. scénáø (all out)
		return;

	}

	public ImageBuffer getImageBuffer() {
		return imageBuffer;
	}

	public void setImageBuffer(ImageBuffer imageBuffer) {
		this.imageBuffer = imageBuffer;
	}

	public Mat4 getFinalMat() {
		return finalMatrix;
	}

	public void setFinalMat(Mat4 finalMat) {
		this.finalMatrix = finalMat;
	}

	public Mat4 getProjection() {
		return proj;
	}

	public void setProjection(Mat4 proj) {
		this.proj = proj;
	}

	public Mat4 getVisible() {
		return view;
	}

	public void setVisible(Mat4 visible) {
		this.view = visible;
	}

	public Mat4 getModel() {
		return model;
	}

	public void setModel(Mat4 model) {
		this.model = model;
	}

	public ZBufferTest getZTest() {
		return zTest;
	}

	public void setZTest(ZBufferTest zTest) {
		this.zTest = zTest;
	}
}
