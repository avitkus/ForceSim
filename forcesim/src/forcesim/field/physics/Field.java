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
	public IVector2D getElectromagneticField(int x, int y) {
		IPoint test = new Point(x, y);
		test.setCharge(1);
		IVector2D field = new Vector2D(0,0);
		for(IPoint p : points) {
			field = field.sum(ElectromagneticForce.getVector(p, test));
		}
		return field;
	}

}
