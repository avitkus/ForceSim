package forcesim.field.physics;

import java.util.ArrayList;

public class Field implements IField {
	
	private ArrayList<IPoint> points;
	
	public Field() {
		points = new ArrayList<>();
	}

	@Override
	public void addPoint(IPoint p) {
		points.add(p);
	}
	
	@Override
	public void removePoint(IPoint p) {
		points.remove(p);
	}
	
	@Override
	public IVector2D getElectromagneticField(double x, double y) {
		IPoint test = new Point(x, y);
		test.setCharge(1);
		IVector2D field = new Vector2D(0,0);
		for(IPoint p : points) {
			field = field.sum(ElectromagneticForce.getVector(p, test));
		}
		return field;
	}
	
	@Override
	public IPoint[] getPoints() {
		return points.toArray(new IPoint[points.size()]);
	}
}
