package forcesim.window;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import forcesim.graphics.FieldPanel;


@SuppressWarnings("serial")
public class MainWindow extends JFrame implements Runnable {

	public MainWindow() {
		super("ForceSim");
	
		setSize(310,400);
		setMinimumSize(new Dimension(310, 400));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
		}
	
        setLayout(new GridBagLayout());
		
        addComponent(new FieldPanel(), 1, 0, 0);
        addComponent(new DefaultPointPanel(), 0, 0, 4);
	
		buildMenuBar();
			
		//setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/1-.png")));
	}
	
	private void buildMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenuItem temp;
		
		JMenu fileMenu = new JMenu("File");
		temp = new JMenuItem("New");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("New");
			}
		});
		fileMenu.add(temp);

		fileMenu.add(new JSeparator());
		
		temp = new JMenuItem("Save");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Save");
			}
		});
		fileMenu.add(temp);
		temp = new JMenuItem("Save As");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Save As");
			}
		});
		fileMenu.add(temp);

		fileMenu.add(new JSeparator());
		
		temp = new JMenuItem("Open");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Open");
			}
		});
		fileMenu.add(temp);
		menuBar.add(fileMenu);
		
		
		JMenu exportMenu = new JMenu("Export");
		temp = new JMenuItem("Save as PNG");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Save as PNG");
			}
		});
		exportMenu.add(temp);
		menuBar.add(exportMenu);

		JMenu editMenu = new JMenu("Edit");
		final JCheckBoxMenuItem snapToGridItem = new JCheckBoxMenuItem("Snap to grid lines");
		snapToGridItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowProperties.snapToGrid = snapToGridItem.isSelected();
				System.out.println("Snap to grid lines");
				repaint();
			}
		});
		snapToGridItem.setSelected(true);
		editMenu.add(snapToGridItem);
		
		editMenu.add(new JSeparator());
		
		temp = new JMenuItem("Clear");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clear");
			}
		});
		editMenu.add(temp);
		menuBar.add(editMenu);

		JMenu viewMenu = new JMenu("View");
		final JCheckBoxMenuItem showGridLineItem = new JCheckBoxMenuItem("Show grid lines");
		showGridLineItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowProperties.displayGrid = showGridLineItem.isSelected();
				System.out.println("Toggle dem grid lines");
				repaint();
			}
		});
		viewMenu.add(showGridLineItem);

		viewMenu.add(new JSeparator());
		
		ButtonGroup buttonGroup = new ButtonGroup();
		temp = new JRadioButtonMenuItem("Display as lines");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowProperties.renderMode = WindowProperties.RENDER_LINES;
				repaint();
			}
		});
		buttonGroup.add(temp);
		viewMenu.add(temp);
		temp = new JRadioButtonMenuItem("Display false color");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowProperties.renderMode = WindowProperties.RENDER_FALSE_COLOR;
				repaint();
			}
		});
		temp.setSelected(true);
		buttonGroup.add(temp);
		viewMenu.add(temp);
		
		menuBar.add(viewMenu);
		
		JMenu aboutMenu = new JMenu("About");
		temp = new JMenuItem("About ForceSim");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				javax.swing.SwingUtilities.invokeLater(new AboutWindow());
			}
		});
		aboutMenu.add(temp);
		menuBar.add(aboutMenu);
		
		setJMenuBar(menuBar);
	}
	
	private void addComponent(Component component, int weighty, int x, int y) {
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = weighty;
        c.gridx = x;
        c.gridy = y;

        add(component, c);
    }

	@Override
	public void run() {
		setVisible(true);
	}
}
