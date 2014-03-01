package forcesim.graphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import forcesim.field.physics.IPoint;
import forcesim.field.physics.Point;
import forcesim.field.physics.Vector2D;
import forcesim.util.Util;

@SuppressWarnings("serial")
public class FieldPanel extends JPanel {

	private final Color COLOR_POSITIVE = Color.red;
	private final Color COLOR_NEGATIVE = Color.blue;

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Point p = new Point(0,0);
		p.setCharge(10);
		renderPoint(g, p);
		p = new Point(1,-1);
		p.setCharge(-10);
		renderPoint(g, p);
	}

	private void renderPoint(Graphics g, IPoint p) {
		if (p.isPositiveCharge()) {
			renderPointPositive(g, p);
		} else {
			renderPointNegative(g, p);
		}
	}

	public void renderPointPositive(Graphics g, IPoint p) {
		renderPointCircle(g, p, COLOR_POSITIVE);
		Vector2D center = Util.convertFieldCoordinate(this, p);
		int halflength = 5;
		g.setColor(Color.black);
		g.drawLine((int)center.getX()-halflength, (int)center.getY(), (int)center.getX()+halflength, (int)center.getY());
		g.drawLine((int)center.getX(), (int)center.getY()-halflength, (int)center.getX(), (int)center.getY()+halflength);
	}
	public void renderPointNegative(Graphics g, IPoint p) {
		renderPointCircle(g, p, COLOR_NEGATIVE);
		Vector2D center = Util.convertFieldCoordinate(this, p);
		int halflength = 5;
		g.setColor(Color.black);
		g.drawLine((int)center.getX()-halflength, (int)center.getY(), (int)center.getX()+halflength, (int)center.getY());
		
	}

	private void renderPointCircle(Graphics g, IPoint p, Color c) {
		Vector2D center = Util.convertFieldCoordinate(this, p);
		int radius = 15;
		g.setColor(Color.black);
		g.fillOval((int)center.getX()-radius, (int)center.getY()-radius, 2*radius, 2*radius);
		g.setColor(c);
		radius = 13;
		g.fillOval((int)center.getX()-radius, (int)center.getY()-radius, 2*radius, 2*radius);
	}
}
