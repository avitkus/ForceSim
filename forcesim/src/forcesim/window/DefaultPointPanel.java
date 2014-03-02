package forcesim.window;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.EventListenerList;

@SuppressWarnings("serial")
public class DefaultPointPanel extends JPanel {
    private final EventListenerList listenerList;
    
    public DefaultPointPanel() {
    	listenerList = new EventListenerList();
        setLayout(new GridBagLayout());
        switch (WindowProperties.type) {
        	case ELECTROMAGNETIC:
        		buildEMPanel();
        		break;
        }
    }
    
    private void buildEMPanel() {
    	
    }
    
    private JRadioButton buildChoiceButton(double value) {
    	String text = "";
    	switch (WindowProperties.type) {
	    	case ELECTROMAGNETIC:
	    		buildEMPanel();
	    		break;
    	}
    	JRadioButton button = new JRadioButton(Double.toString(value));
    	
    	return null;
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
