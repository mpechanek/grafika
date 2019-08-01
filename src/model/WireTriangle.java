package model;

import model.Topology.Part.PartType;
import transforms.Point3D;

/**
 * T��da reprezentuj�c� dr�tov� model troj�heln�ku
 * 
 * @author Milan
 *
 */
public class WireTriangle extends Solid {
	private final double edge = 1.5;

	public WireTriangle() {
		getVertices().add(new VertexBase(new Point3D(0, edge, 0)));
		getVertices().add(new VertexBase(new Point3D(edge, 0, 0)));
		getVertices().add(new VertexBase(new Point3D(0, 0, edge)));
		getIndices().add(0);
		getIndices().add(1);

		getIndices().add(1);
		getIndices().add(2);

		getIndices().add(0);
		getIndices().add(2);

		getPart().add(new SolidPart(3, 0, PartType.LINE));
	}

}