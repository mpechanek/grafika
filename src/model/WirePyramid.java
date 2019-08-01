package model;

import model.Topology.Part.PartType;
import transforms.Point3D;

/**
 * Tøída reprezentující drátový model jehlanu
 * 
 * @author Milan
 *
 */
public class WirePyramid extends Solid {
	private final double edge = 0.9;

	public WirePyramid() {
		getVertices().add(new VertexBase(new Point3D(0, edge, 0)));
		getVertices().add(new VertexBase(new Point3D(edge, 0, 0)));
		getVertices().add(new VertexBase(new Point3D(edge, edge, edge)));
		getVertices().add(new VertexBase(new Point3D(0, 0, edge)));

		getIndices().add(0);
		getIndices().add(1);

		getIndices().add(1);
		getIndices().add(2);

		getIndices().add(2);
		getIndices().add(0);

		getIndices().add(0);
		getIndices().add(3);

		getIndices().add(1);
		getIndices().add(3);

		getIndices().add(2);
		getIndices().add(3);

		getPart().add(new SolidPart(6, 0, PartType.LINE));
	}
}
