package forcesim.window;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

@SuppressWarnings("serial")
public class DefaultPointPanel extends JPanel {
    private final EventListenerList listenerList;
    
    private final double[] DEFAULT_CHARGE_VALUES = {1, 2, 3, 4, 5, -1, -2, -3, -4, -5};
    
    public DefaultPointPanel() {
    	listenerList = new EventListenerList();
        setLayout(new GridBagLayout());
    }
    
    private void buildEMPanel() {
    	
    }
    
    private void addComponent(Component component, int x, int y) {
        addComponent(component, 1, x, y);
    }
    
    private void addComponent(Component component, int height, int x, int y) {
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.weighty = 1;
        c.gridheight = height;
        c.gridx = x;
        c.gridy = y;

        add(component, c);
    }
    
}
