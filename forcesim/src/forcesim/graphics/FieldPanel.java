package forcesim.graphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import forcesim.field.physics.ElectromagneticForce;
import forcesim.field.physics.Field;
import forcesim.field.physics.IField;
import forcesim.field.physics.IPoint;
import forcesim.field.physics.Point;
import forcesim.field.physics.Vector2D;
import forcesim.util.Util;
import forcesim.util.listener.FieldPanelListener;
import forcesim.window.WindowProperties;

@SuppressWarnings("serial")
public class FieldPanel extends JPanel {

	private final Color COLOR_POSITIVE = Color.red;
	private final Color COLOR_NEGATIVE = Color.blue;
	
	private final int RADIUS = 15;

	private final IField field = new Field();

	public FieldPanel() {
		Point p = new Point(0,0);
		p.setCharge(10);
		field.addPoint(p);
		p = new Point(1,-1);
		p.setCharge(-10);
		field.addPoint(p);
		
		new FieldPanelListener(this);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		renderField(g);
		
		for (IPoint p: field.getPoints()) {
			renderPoint(g, p);
		}
	}

	private void renderField(Graphics g) {
		double charge;
		double maxCharge = 0;
		
		for (IPoint p : field.getPoints()) {
			IPoint p2 = new Point(p.getX(), p.getY()+(((double)RADIUS-1)/WindowProperties.scale));
			p2.setCharge(1);
			double f = ElectromagneticForce.getVector(p, p2).getMagnitude();
			if (f > maxCharge) maxCharge = f;
		}
		Point p;
		long time = System.currentTimeMillis();
		double[][] charges = new double[getWidth()][getHeight()];
		for (int x=0; x<getWidth(); x++) {
			for (int y=0; y<getHeight(); y++) {
				p = Util.convertPixelCoordinate(this, x, y);
				charge = field.getElectromagneticField(p.getX(), p.getY()).getMagnitude();
				charges[x][y] = charge;
			}
		}
		System.out.println("REPAINT "+(System.currentTimeMillis()-time));
		time = System.currentTimeMillis();
		for (int x=0; x<getWidth(); x++) {
			for (int y=0; y<getHeight(); y++) {
				//System.out.println(charges[x][y]);
				if (charges[x][y] < maxCharge) {
					//float cor = (float)(charges[x][y]/maxCharge);
					float cor = (float)Math.log10(9*Math.sqrt(Math.log10(9*(charges[x][y]/maxCharge)+1))+1);
					g.setColor(new Color(1f,1f-cor,1f-cor));
					g.drawLine(x, y, x, y);
				}
			}
		}
		System.out.println("AGAIN "+(System.currentTimeMillis()-time));
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
		int radius = RADIUS;
		g.setColor(Color.black);
		g.fillOval((int)center.getX()-radius, (int)center.getY()-radius, 2*radius, 2*radius);
		g.setColor(c);
		radius = radius-2;
		g.fillOval((int)center.getX()-radius, (int)center.getY()-radius, 2*radius, 2*radius);
	}
	
	public IPoint getPointAt(int x, int y) {
		for (IPoint p : field.getPoints()) {
			Vector2D v = Util.convertFieldCoordinate(this, p);
			int dx = (int)v.getX() - x;
			int dy = (int)v.getY() - y;
			if (dx*dx + dy*dy < RADIUS*RADIUS) return p;
		}
		return null;
	}
	
	public void addPoint(int x, int y) {
		IPoint p = Util.convertPixelCoordinate(this,x,y);
		p.setCharge(WindowProperties.currentChargeChoice);
		field.addPoint(p);
	}
}
