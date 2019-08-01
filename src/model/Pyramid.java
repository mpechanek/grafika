package model;

import model.Topology.Part.PartType;
import transforms.Col;
import transforms.Point3D;

/**
 * Tøída reprezentující jehlan
 * 
 * @author pechami2
 *
 */
public class Pyramid extends Solid {
	private final double edge = 0.9;

	public Pyramid() {
		getVertices().add(new VertexBase(new Point3D(0, edge, 0), new Col(0x00ff00)));
		getVertices().add(new VertexBase(new Point3D(edge, 0, 0), new Col(0x0000ff)));
		getVertices().add(new VertexBase(new Point3D(edge, edge, edge), new Col(0xfff000)));
		getVertices().add(new VertexBase(new Point3D(0, 0, edge), new Col(0xffff00)));

		getIndices().add(0);
		getIndices().add(1);
		getIndices().add(2);

		getIndices().add(1);
		getIndices().add(2);
		getIndices().add(3);

		getIndices().add(2);
		getIndices().add(3);
		getIndices().add(0);

		getIndices().add(3);
		getIndices().add(1);
		getIndices().add(0);

		getPart().add(new SolidPart(4, 0, PartType.TRIANGLE));
	}

}
