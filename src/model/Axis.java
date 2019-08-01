package model;

import model.Topology.Part.PartType;
import transforms.Point3D;

/**
 * T��da reprezentuj�c� osy x, y, z a koncov� troj�heln�ky
 * 
 * @author pechami2
 *
 */
public class Axis extends Solid {
	private final double axisSize = 2.5;

	public Axis() {

		getVertices().add(new VertexBase(new Point3D(0, 0, 0)));

		getVertices().add(new VertexBase(new Point3D(0, 0, axisSize)));

		getVertices().add(new VertexBase(new Point3D(0, axisSize, 0)));

		getVertices().add(new VertexBase(new Point3D(axisSize, 0, 0)));

		// osy
		getIndices().add(0);
		getIndices().add(1);

		getIndices().add(0);
		getIndices().add(2);

		getIndices().add(0);
		getIndices().add(3);

		getPart().add(new SolidPart(3, 0, PartType.LINE));

	}
}