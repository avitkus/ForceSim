package forcesim.field.physics.space;

import forcesim.field.physics.IPoint;

public interface IPoint2D {
	public double getX();
	public double getY();

	public double getDistanceTo(IPoint p);
	public double getAngleTo(IPoint p);
}
