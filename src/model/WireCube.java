package model;

import model.Topology.Part.PartType;
import transforms.Point3D;

/**
 * Tøída reprezentující drátový model krychle
 * 
 * @author Milan
 *
 */
public class WireCube extends Solid {
	private final double edge = 0.5;

	public WireCube() {
		getVertices().add(new VertexBase(new Point3D(edge, edge, edge)));
		getVertices().add(new VertexBase(new Point3D(-edge, edge, edge)));
		getVertices().add(new VertexBase(new Point3D(edge, -edge, edge)));
		getVertices().add(new VertexBase(new Point3D(-edge, -edge, edge)));
		getVertices().add(new VertexBase(new Point3D(edge, -edge, -edge)));
		getVertices().add(new VertexBase(new Point3D(-edge, -edge, -edge)));
		getVertices().add(new VertexBase(new Point3D(-edge, edge, -edge)));
		getVertices().add(new VertexBase(new Point3D(edge, edge, -edge)));

		getIndices().add(0);
		getIndices().add(1);

		getIndices().add(0);
		getIndices().add(2);

		getIndices().add(0);
		getIndices().add(7);

		getIndices().add(2);
		getIndices().add(3);

		getIndices().add(2);
		getIndices().add(4);

		getIndices().add(1);
		getIndices().add(3);

		getIndices().add(1);
		getIndices().add(6);

		getIndices().add(3);
		getIndices().add(5);

		getIndices().add(4);
		getIndices().add(5);

		getIndices().add(4);
		getIndices().add(7);

		getIndices().add(5);
		getIndices().add(6);

		getIndices().add(6);
		getIndices().add(7);

		getIndices().add(6);
		getIndices().add(4);

		getPart().add(new SolidPart(12, 0, PartType.LINE));

	}

}
