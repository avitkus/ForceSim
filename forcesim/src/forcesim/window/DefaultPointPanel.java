package forcesim.window;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    		final JRadioButton button = buildChoiceButton(i);
    		if (i == 1) {
    			button.setSelected(true);
    		}
    		buttons.add(button);
    		panel.add(button);
    		ImageIcon img = createImageIcon("/images/" + i + "+.png");;
    		JLabel imageLbl = new JLabel(img);
    		imageLbl.addMouseListener(new MouseAdapter() {

    			@Override
    			public void mouseClicked(MouseEvent event) {
    				button.doClick();
    			}
        	});
    		panel.add(imageLbl);
    	}
		addComponent(panel, 0, 0);
		panel = new JPanel();
    	for(int i = 1; i <= 5; i ++) {
            panel.setBorder(BorderFactory.createTitledBorder("Negative Charge"));
    		final JRadioButton button = buildChoiceButton(-i);
    		buttons.add(button);
    		panel.add(button);
    		ImageIcon img = createImageIcon("/images/" + i + "-.png");
    		JLabel imageLbl = new JLabel(img);
    		imageLbl.addMouseListener(new MouseAdapter() {

    			@Override
    			public void mouseClicked(MouseEvent event) {
    				button.setSelected(true);
    				button.doClick();
    			}
        	});
    		panel.add(imageLbl);
    	}
		addComponent(panel, 0, 1);
		
		panel = new JPanel(new GridLayout(2, 1));
		buttons = new ButtonGroup();
		panel.setBorder(BorderFactory.createTitledBorder("Render Mode"));
		JRadioButton button = new JRadioButton("Draw Lines");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				WindowProperties.renderMode = WindowProperties.RENDER_LINES;
				WindowProperties.drawingPoints = false;
			}
    	});
		buttons.add(button);
		panel.add(button);
		button = new JRadioButton("Fill False Color");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				WindowProperties.renderMode = WindowProperties.RENDER_FALSE_COLOR;
				WindowProperties.drawingPoints = true;
			}
    	});
		buttons.add(button);
		panel.add(button);
		addComponent(panel, 2, 1, 0);
    }
    
    private JRadioButton buildChoiceButton(final int value) {
    	final JRadioButton button = new JRadioButton("");
    	
    	button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
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
	
	private void addComponent(Component component, int height, int x, int y) {
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridheight = height;
        c.gridx = x;
        c.gridy = y;

        add(component, c);
    }
}
