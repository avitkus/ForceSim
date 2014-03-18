package forcesim.field.physics.space;

import forcesim.field.physics.IPoint;

public class Point2D implements IPoint2D {
	private double x, y;
	
	public Point2D() {
		this(0,0);
	}
	
	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}
	
	@Override
	public double getDistanceTo(IPoint p) {
		return Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2));
	}

	@Override
	public double getAngleTo(IPoint p) {
		return Math.atan2(y - p.getY(), x - p.getX());
	}
}
