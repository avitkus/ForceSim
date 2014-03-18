package forcesim.field.physics;

import forcesim.field.physics.space.IPoint2D;

public interface IPoint extends IPoint2D{
	public void setX(double x);
	public void setY(double y);

	public boolean isPositiveCharge();
	public double getCharge();
	
	public void setCharge(double charge);
	
	public IPoint clone();
	
	public String dataStringHeader();
	public String toDataString();
}
