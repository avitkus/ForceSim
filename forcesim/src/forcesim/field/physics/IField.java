package forcesim.field.physics;

public interface IField {
	public void addPoint(IPoint p);
	
	public IVector2D getElectromagneticField(double x, double y);
	public IPoint[] getPoints();
}
