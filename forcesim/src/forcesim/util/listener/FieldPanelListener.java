package forcesim.util.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import forcesim.graphics.FieldPanel;

public class FieldPanelListener implements MouseListener {

	private final FieldPanel fieldPanel;
	
	public FieldPanelListener(FieldPanel fp) {
		fieldPanel = fp;
		fieldPanel.addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		if (event.getButton() == MouseEvent.BUTTON1) {
			System.out.println("!!!!!!");
		} else if (event.getButton() == MouseEvent.BUTTON3) {
			System.out.println("##### "+fieldPanel.getPointAt(event.getX(), event.getY()));
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
