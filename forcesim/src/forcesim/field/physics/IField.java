package forcesim.field.physics;

public interface IField {
	public void removePoint(IPoint p);
	public void addPoint(IPoint p);
	
	public IVector2D getElectromagneticField(double x, double y);
	public IPoint[] getPoints();
	
	public void clear();
}
