package forcesim.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import forcesim.field.physics.ElectromagneticForce;
import forcesim.field.physics.Field;
import forcesim.field.physics.IField;
import forcesim.field.physics.IPoint;
import forcesim.field.physics.Point;
import forcesim.field.physics.Vector2D;
import forcesim.util.Util;
import forcesim.window.WindowProperties;

@SuppressWarnings("serial")
public class FieldPanel extends JPanel {

	private final Color COLOR_POSITIVE = Color.red;
	private final Color COLOR_NEGATIVE = new Color(0f,0.6f,1f);
	
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
		//System.out.println("REPAINT "+(System.currentTimeMillis()-time));
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
		//System.out.println("AGAIN "+(System.currentTimeMillis()-time));
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
			if (WindowProperties.snapToGrid) {
				IPoint t = Util.convertPixelCoordinate(this, x, y);
				t.setX(Math.round(t.getX()));
				t.setY(Math.round(t.getY()));
				Vector2D v = Util.convertFieldCoordinate(this, t);
				x = (int)v.getX();
				y = (int)v.getY();
			}
			Vector2D v = Util.convertFieldCoordinate(this, p);
			int dx = (int)v.getX() - x;
			int dy = (int)v.getY() - y;
			if (dx*dx + dy*dy < RADIUS*RADIUS) return p;
		}
		return null;
	}
	
	public void addPoint(int x, int y) {
		IPoint p = Util.convertPixelCoordinate(this,x,y);
		if (WindowProperties.snapToGrid) {
			p.setX(Math.round(p.getX()));
			p.setY(Math.round(p.getY()));
		}
		p.setCharge(WindowProperties.currentChargeChoice);
		field.addPoint(p);
	}
	
	private class FieldPanelListener extends MouseAdapter {

		public FieldPanelListener(FieldPanel fp) {
			addMouseListener(this);
		}

		@Override
		public void mouseClicked(MouseEvent event) {
			final IPoint p = getPointAt(event.getX(), event.getY());
			if (event.getButton() == MouseEvent.BUTTON1) {
				if (p == null) {
					addPoint(event.getX(), event.getY());
					repaint();
				}
			} else if (event.getButton() == MouseEvent.BUTTON3) {
				if (p != null) {
					JPopupMenu popup = new JPopupMenu();
					JMenuItem changeChargeMenu = new JMenuItem("Change charge");
					changeChargeMenu.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							boolean failAtLife = false;
							do {
								failAtLife = false;
								String response = JOptionPane.showInputDialog(FieldPanel.this, "What is the new charge?", "Enter a number.", JOptionPane.QUESTION_MESSAGE);
				                if (response == null) {
				                    return;
				                }
				                try {
				                	p.setCharge(Double.parseDouble(response));
				                	repaint();
				                } catch (NumberFormatException ex) {
				                	JOptionPane.showMessageDialog(FieldPanel.this, "Charge must be a number", "Invalid charge!", JOptionPane.ERROR_MESSAGE);
				                	failAtLife = true;
				                }
							} while (failAtLife);
						}
						
					});
					popup.add(changeChargeMenu);
					
					popup.show(event.getComponent(), event.getX(), event.getY());
				}
			}
		}
	}

}
