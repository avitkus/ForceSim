package forcesim.window;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class DefaultPointPanel extends JPanel {
    
    public DefaultPointPanel() {
        setLayout(new GridBagLayout());
        switch (WindowProperties.type) {
        	case ELECTROMAGNETIC:
        		buildEMPanel();
        		break;
        }
    }
    
    private void buildEMPanel() {
    	ButtonGroup buttons = new ButtonGroup();
		JPanel panel = new JPanel();
    	for(int i = 1; i <= 5; i ++) {
            panel.setBorder(BorderFactory.createTitledBorder("Positive Charge"));
    		JRadioButton button = buildChoiceButton(i);
    		buttons.add(button);
    		panel.add(button);
    		ImageIcon img = createImageIcon("/images/" + i + "+.png");
    		panel.add(new JLabel(img));
    	}
		addComponent(panel, 0, 0);
		panel = new JPanel();
    	for(int i = 1; i <= 5; i ++) {
            panel.setBorder(BorderFactory.createTitledBorder("Negative Charge"));
    		JRadioButton button = buildChoiceButton(-i);
    		buttons.add(button);
    		panel.add(button);
    		ImageIcon img = createImageIcon("/images/" + i + "-.png");
    		panel.add(new JLabel(img));
    	}
		addComponent(panel, 0, 1);
    }
    
    private JRadioButton buildChoiceButton(final int value) {
    	final JRadioButton button = new JRadioButton("");
    	
    	button.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent event) {
				switch (WindowProperties.type) {
			    	case ELECTROMAGNETIC:
			    		WindowProperties.currentChargeChoice = value;
			    	break;
		    	}
			}
    	});
    	
    	return button;
    }
    
    private ImageIcon createImageIcon(String path) {
        return new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(path)));
    }

	private void addComponent(Component component, int x, int y) {
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = x;
        c.gridy = y;

        add(component, c);
    }
}
