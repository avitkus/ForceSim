package forcesim.field.physics;

public interface IField {
	public void addPoint(IPoint p);
	
	public Vector2D getElectromagneticForce(int x, int y);
}
