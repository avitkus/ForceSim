package forcesim.field.physics;

public class Point implements IPoint {
	private double x, y;	//location
	private double charge;	//charge magnitude
	
	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public boolean isPositiveCharge() {
		return charge > 0;
	}

	@Override
	public double getCharge() {
		return charge;
	}

}
