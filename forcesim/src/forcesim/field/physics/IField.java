package forcesim.field.physics;

import forcesim.field.physics.space.IVector2D;

public interface IField {
	public void removePoint(IPoint p);
	public void addPoint(IPoint p);
	
	public IVector2D getElectromagneticField(double x, double y);
	public IPoint[] getPoints();
	
	public void clear();
}
