package forcesim.util;

import forcesim.field.physics.Point;
import forcesim.field.physics.Vector2D;
import forcesim.window.Window;

public class Util {
	public static Vector2D convertFieldCoordinate(Point p) {
		return new Vector2D(
				p.getX()*Window.getScale() + Window.getWidth(),
				p.getY()*Window.getScale() + Window.getHeight()
				);
	}
	public static Point convertPixelCoordinate(int x, int y) {
		return new Point(
				(x-Window.getWidth())/(double)Window.getScale(),
				(y-Window.getHeight())/(double)Window.getScale()
				);
	}
}
