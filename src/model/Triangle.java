package model;

import model.Topology.Part.PartType;
import transforms.Col;
import transforms.Point3D;
/**
 * Tøída reprezentující trojúhelník
 * @author Milan
 *
 */
public class Triangle extends Solid {
	private final double edge = 1.5;
	private final int color1 = 0xff0000;
	private final int color2 = 0x00ff00;
	private final int color3 = 0x0000ff;

	public Triangle() {
		getVertices().add(new VertexBase(new Point3D(0, edge, 0), new Col(color1)));
		getVertices().add(new VertexBase(new Point3D(edge, 0, 0), new Col(color2)));
		getVertices().add(new VertexBase(new Point3D(0, 0, edge), new Col(color3)));
		getIndices().add(0);
		getIndices().add(1);
		getIndices().add(2);

		getPart().add(new SolidPart(1, 0, PartType.TRIANGLE));
	}

}
