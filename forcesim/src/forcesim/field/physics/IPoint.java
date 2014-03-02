package forcesim.field.physics;

public interface IPoint {
	public double getX();
	public double getY();
	
	public void setX(double x);
	public void setY(double y);

	public boolean isPositiveCharge();
	public double getCharge();
	
	public void setCharge(double charge);
	
	public double getDistanceTo(IPoint p);
	public double getAngleTo(IPoint p);
	
	public IPoint clone();
}
