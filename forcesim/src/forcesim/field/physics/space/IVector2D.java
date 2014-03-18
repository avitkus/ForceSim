package forcesim.field.physics.space;

public interface IVector2D {
	public double getMagnitude();
	public double getAngle();
	
	public double getX();
	public double getY();
	
	public IVector2D sum(IVector2D v);
	public IVector2D scale(double factor);
}
