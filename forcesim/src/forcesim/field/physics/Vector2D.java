package forcesim.field.physics;

public class Vector2D implements IVector2D {
	private double i;
	private double j;
	
	public Vector2D(double i, double j) {
		this.i = i;
		this.j = j;
	}
	
	@Override
	public double getMagnitude() {
		return Math.sqrt(Math.pow(i, 2) + Math.pow(j, 2));
	}

	@Override
	public double getAngle() {
		return Math.atan(i/j);
	}

	@Override
	public double getX() {
		return i;
	}

	@Override
	public double getY() {
		return j;
	}

	@Override
	public IVector2D sum(IVector2D v) {
		return new Vector2D(i + v.getX(), j + v.getY());
	}

	@Override
	public IVector2D scale(double factor) {
		return new Vector2D(i * factor, j * factor);
	}
	
}
