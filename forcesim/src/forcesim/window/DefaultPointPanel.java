package forcesim.window;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

@SuppressWarnings("serial")
public class DefaultPointPanel extends JPanel {
    private final EventListenerList listenerList;
    
    public DefaultPointPanel() {
    	listenerList = new EventListenerList();
    }
}
