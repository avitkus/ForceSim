package forcesim.util;

import forcesim.field.physics.IPoint;
import forcesim.field.physics.Point;
import forcesim.field.physics.Vector2D;
import forcesim.graphics.FieldPanel;
import forcesim.window.WindowProperties;

public class Util {
	public static Vector2D convertFieldCoordinate(FieldPanel fp, IPoint p) {
		return new Vector2D(
				p.getX()*WindowProperties.scale + fp.getWidth()/2,
				p.getY()*WindowProperties.scale + fp.getHeight()/2
				);
	}
	public static Point convertPixelCoordinate(FieldPanel fp, int x, int y) {
		return new Point(
				(x-fp.getWidth()/2)/(double)WindowProperties.scale,
				(y-fp.getHeight()/2)/(double)WindowProperties.scale
				);
	}
}
