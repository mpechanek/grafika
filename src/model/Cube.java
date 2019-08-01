package model;

import model.Topology.Part.PartType;
/**
 * Tøída reprezentující krychli.
 * @author Milan Pechánek
 * 
 */
import transforms.Col;
import transforms.Point3D;

/**
 * Tøída reprezentující krychli
 * 
 * @author pechami2
 * 
 */
public class Cube extends Solid {
	private final double edge = 0.5;

	public Cube() {

		getVertices().add(new VertexBase(new Point3D(edge, edge, edge), new Col(0xff0000)));
		getVertices().add(new VertexBase(new Point3D(-edge, edge, edge), new Col(0x0000ff)));
		getVertices().add(new VertexBase(new Point3D(edge, -edge, edge), new Col(0x00ff00)));
		getVertices().add(new VertexBase(new Point3D(-edge, -edge, edge), new Col(0xff0000)));
		getVertices().add(new VertexBase(new Point3D(edge, -edge, -edge), new Col(0x0000ff)));
		getVertices().add(new VertexBase(new Point3D(-edge, -edge, -edge), new Col(0xfff000)));
		getVertices().add(new VertexBase(new Point3D(-edge, edge, -edge), new Col(0x1001f00)));
		getVertices().add(new VertexBase(new Point3D(edge, edge, -edge), new Col(0xffff00)));

		getIndices().add(0);
		getIndices().add(1);
		getIndices().add(2);

		getIndices().add(1);
		getIndices().add(2);
		getIndices().add(3);

		getIndices().add(2);
		getIndices().add(3);
		getIndices().add(4);

		getIndices().add(3);
		getIndices().add(4);
		getIndices().add(5);

		getIndices().add(4);
		getIndices().add(5);
		getIndices().add(6);

		getIndices().add(4);
		getIndices().add(6);
		getIndices().add(7);

		getIndices().add(2);
		getIndices().add(4);
		getIndices().add(7);

		getIndices().add(5);
		getIndices().add(3);
		getIndices().add(1);

		getIndices().add(5);
		getIndices().add(1);
		getIndices().add(6);

		getIndices().add(7);
		getIndices().add(1);
		getIndices().add(6);

		getIndices().add(0);
		getIndices().add(7);
		getIndices().add(1);

		getIndices().add(2);
		getIndices().add(0);
		getIndices().add(7);

		getPart().add(new SolidPart(12, 0, PartType.TRIANGLE));
	}

}
