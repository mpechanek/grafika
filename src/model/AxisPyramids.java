package model;

import model.Topology.Part.PartType;
import transforms.Col;
import transforms.Point3D;
/**
 * Tøída reprezentující barevné zvýraznìní koncù os (jehlany)
 * @author Milan
 *
 */
public class AxisPyramids extends Solid {
	private final double axisSize = 2.5;
	private final double pryramidEdge = 0.1;
	private final int color1 = 0xff0000;
	private final int color2 = 0x00ff00;
	private final int color3 = 0x0000ff;

	public AxisPyramids() {
		// podstava 1
		getVertices().add(new VertexBase(new Point3D(axisSize, pryramidEdge, pryramidEdge), new Col(color1)));
		getVertices().add(new VertexBase(new Point3D(axisSize, -pryramidEdge, -pryramidEdge), new Col(color1)));
		getVertices().add(new VertexBase(new Point3D(axisSize, -pryramidEdge, pryramidEdge), new Col(color1)));
		getVertices().add(new VertexBase(new Point3D(axisSize, pryramidEdge, -pryramidEdge), new Col(color1)));
		// spicka 1
		getVertices().add(new VertexBase(new Point3D(axisSize + pryramidEdge + 0.5, 0, 0), new Col(color1)));
		// podstava 2
		getVertices().add(new VertexBase(new Point3D(pryramidEdge, axisSize, pryramidEdge), new Col(color2)));
		getVertices().add(new VertexBase(new Point3D(-pryramidEdge, axisSize, -pryramidEdge), new Col(color2)));
		getVertices().add(new VertexBase(new Point3D(-pryramidEdge, axisSize, pryramidEdge), new Col(color2)));
		getVertices().add(new VertexBase(new Point3D(pryramidEdge, axisSize, -pryramidEdge), new Col(color2)));
		// spicka 2
		getVertices().add(new VertexBase(new Point3D(0, axisSize + pryramidEdge + 0.5, 0), new Col(color2)));
		// podstava 3
		getVertices().add(new VertexBase(new Point3D(pryramidEdge, pryramidEdge, axisSize), new Col(color3)));
		getVertices().add(new VertexBase(new Point3D(-pryramidEdge, -pryramidEdge, axisSize), new Col(color3)));
		getVertices().add(new VertexBase(new Point3D(-pryramidEdge, pryramidEdge, axisSize), new Col(color3)));
		getVertices().add(new VertexBase(new Point3D(pryramidEdge, -pryramidEdge, axisSize), new Col(color3)));
		// spicka 3
		getVertices().add(new VertexBase(new Point3D(0, 0, axisSize + pryramidEdge + 0.5), new Col(color3)));

		// Podstava 1
		getIndices().add(0);
		getIndices().add(1);
		getIndices().add(3);

		getIndices().add(0);
		getIndices().add(1);
		getIndices().add(2);
		// Stìny 1
		getIndices().add(0);
		getIndices().add(2);
		getIndices().add(4);

		getIndices().add(0);
		getIndices().add(3);
		getIndices().add(4);

		getIndices().add(1);
		getIndices().add(2);
		getIndices().add(4);

		getIndices().add(2);
		getIndices().add(3);
		getIndices().add(4);

		// Podstava 2
		getIndices().add(5);
		getIndices().add(6);
		getIndices().add(8);

		getIndices().add(5);
		getIndices().add(6);
		getIndices().add(7);
		// Stìny 2
		getIndices().add(5);
		getIndices().add(7);
		getIndices().add(9);

		getIndices().add(5);
		getIndices().add(8);
		getIndices().add(9);

		getIndices().add(6);
		getIndices().add(7);
		getIndices().add(9);

		getIndices().add(7);
		getIndices().add(8);
		getIndices().add(9);

		// Podstava 3
		getIndices().add(10);
		getIndices().add(11);
		getIndices().add(13);

		getIndices().add(10);
		getIndices().add(11);
		getIndices().add(12);
		// Stìny 3
		getIndices().add(12);
		getIndices().add(12);
		getIndices().add(14);

		getIndices().add(10);
		getIndices().add(13);
		getIndices().add(14);

		getIndices().add(11);
		getIndices().add(12);
		getIndices().add(14);

		getIndices().add(12);
		getIndices().add(13);
		getIndices().add(14);

		getPart().add(new SolidPart(18, 0, PartType.TRIANGLE));

	}
}
