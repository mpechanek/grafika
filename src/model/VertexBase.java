package model;

import transforms.Col;
import transforms.Mat4;
import transforms.Point3D;

/**
 * Vertex base
 * 
 * @author Milan
 *
 */
public class VertexBase implements Vertex {

	protected Point3D pos;
	protected double one;
	protected Col color;

	public VertexBase(Point3D point, Col color) {
		super();
		this.pos = point;
		one = 1.0;
		this.color = color;
	}

	public VertexBase(Point3D point) {
		super();
		this.pos = point;
		one = 1.0;
	}

	public VertexBase() {
		one = 1.0;
	}

	public Col getColor() {
		return color;
	}

	public void setColor(Col color) {
		this.color = color;
	}

	@Override
	public Vertex mul(double s) {
		VertexBase vb = new VertexBase();
		vb.setColor(color.mul(s));
		vb.pos = pos.mul(s);
		vb.one = one * s;
		return vb;
	}

	@Override
	public void setPos(Point3D p) {
		this.pos = p;
	}

	@Override
	public Point3D getPos() {
		return pos;
	}

	@Override
	public double getX() {
		return pos.getX();
	}

	@Override
	public double getY() {
		return pos.getY();
	}

	@Override
	public double getZ() {
		return pos.getZ();
	}

	@Override
	public double getW() {
		return pos.getW();
	}

	@Override
	public Vertex add(Vertex v) {
		VertexBase n = new VertexBase();
		n.setColor(color.add(v.getColor()));
		n.setPos(pos.add(v.getPos()));
		n.setOne(getOne() + v.getOne());
		return n;
	}

	@Override
	public double getOne() {
		return one;
	}

	@Override
	public void setOne(double one) {
		this.one = one;
	}

	@Override
	public Vertex dehomog() {
		VertexBase v = new VertexBase();
		Double w = pos.getW();
		v.setColor(color);
		v.pos = pos.mul(1 / w);
		v.one = one / w;
		return v;
	}

	@Override
	public Vertex mul(Mat4 s) {
		VertexBase v = new VertexBase();
		v.setColor(color);
		v.setPos(pos.mul(s));
		return v;
	}
}
