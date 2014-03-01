package forcesim.util;

import forcesim.field.physics.IPoint;
import forcesim.field.physics.Point;
import forcesim.field.physics.Vector2D;
import forcesim.window.Window;

public class Util {
	public static Vector2D convertFieldCoordinate(IPoint p) {
		return new Vector2D(
				p.getX()*Window.getScale() + Window.getWidth()/2,
				p.getY()*Window.getScale() + Window.getHeight()/2
				);
	}
	public static Point convertPixelCoordinate(int x, int y) {
		return new Point(
				(x-Window.getWidth()/2)/(double)Window.getScale(),
				(y-Window.getHeight()/2)/(double)Window.getScale()
				);
	}
}
