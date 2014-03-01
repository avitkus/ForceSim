package forcesim.graphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import forcesim.field.physics.IPoint;

public class FieldPanel extends JPanel {
	
	private final Color COLOR_POSITIVE = Color.red;
	private final Color COLOR_NEGATIVE = Color.blue;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
	}
	
	private void renderPoint(Graphics g, IPoint p) {
		if (p.isPositiveCharge()) {
			renderPointCircle(g, p, COLOR_POSITIVE);
		} else {
			renderPointCircle(g, p, COLOR_NEGATIVE);
		}
	}
	
	private void renderPointCircle(Graphics g, IPoint p, Color c) {
		
	}
}
