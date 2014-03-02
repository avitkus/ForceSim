package forcesim.field.physics;

public class Point implements IPoint {
	private double x, y;	//location
	private double charge;	//charge magnitude
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		charge = 0;
	}
	
	public void setCharge(double charge) {
		this.charge = charge;
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
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public boolean isPositiveCharge() {
		return charge > 0;
	}

	@Override
	public double getCharge() {
		return charge;
	}

	@Override
	public double getDistanceTo(IPoint p) {
		return Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2));
	}

	@Override
	public double getAngleTo(IPoint p) {
		return Math.atan2(y - p.getY(), x - p.getX());
	}

	@Override
	public IPoint clone() {
		Point ret = new Point(x, y);
		ret.setCharge(charge);
		return ret;
	}
}
