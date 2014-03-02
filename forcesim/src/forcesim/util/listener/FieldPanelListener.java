package forcesim.util.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import forcesim.field.physics.IPoint;
import forcesim.graphics.FieldPanel;

public class FieldPanelListener implements MouseListener {

	private final FieldPanel fieldPanel;

	public FieldPanelListener(FieldPanel fp) {
		fieldPanel = fp;
		fieldPanel.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		IPoint p = fieldPanel.getPointAt(event.getX(), event.getY());
		if (event.getButton() == MouseEvent.BUTTON1) {
			if (p == null) {
				fieldPanel.addPoint(event.getX(), event.getY());
				fieldPanel.repaint();
			}
		} else if (event.getButton() == MouseEvent.BUTTON3) {
			if (p != null) {
				System.out.println("menu");
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
