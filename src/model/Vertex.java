package model;

import transforms.Col;
import transforms.Mat4;
import transforms.Point3D;

/**
 * Rozhraní Vertex
 * 
 * @author Milan
 *
 */
public interface Vertex {
	Point3D getPos();

	Vertex mul(double s);

	Vertex mul(Mat4 s);

	void setPos(Point3D p);

	double getX();

	double getY();

	double getZ();

	double getW();

	Vertex add(Vertex v);

	void setOne(double one);

	double getOne();

	Vertex dehomog();

	Col getColor();

	void setColor(Col color);

}
