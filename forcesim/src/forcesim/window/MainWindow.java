package forcesim.window;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import forcesim.graphics.FieldImage;
import forcesim.graphics.FieldPanel;
import forcesim.graphics.ImageFileWriter;
import forcesim.util.FieldSaveFileReader;
import forcesim.util.FieldSaveFileWriter;


@SuppressWarnings("serial")
public class MainWindow extends JFrame implements Runnable {
	private File saveFile;
	private FieldPanel fieldPanel;

	public MainWindow() {
		super("ForceSim");
	
		setSize(420,400);
		setMinimumSize(new Dimension(420, 400));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
		}
	
        setLayout(new GridBagLayout());
		
        fieldPanel = new FieldPanel();
        
        addComponent(fieldPanel, 1, 0, 0);
        addComponent(new DefaultPointPanel(fieldPanel), 0, 0, 4);
	
		buildMenuBar();
		
		saveFile = null;
			
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
				save();
			}
		});
		fileMenu.add(temp);
		temp = new JMenuItem("Save As");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAs();
			}
		});
		fileMenu.add(temp);

		fileMenu.add(new JSeparator());
		
		temp = new JMenuItem("Open");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				open();
			}
		});
		fileMenu.add(temp);
		menuBar.add(fileMenu);
		
		
		JMenu exportMenu = new JMenu("Export");
		temp = new JMenuItem("Save as PNG");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savePNG();
			}
		});
		exportMenu.add(temp);
		menuBar.add(exportMenu);

		JMenu editMenu = new JMenu("Edit");
		final JCheckBoxMenuItem snapToGridItem = new JCheckBoxMenuItem("Snap to grid lines");
		snapToGridItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowProperties.snapToGrid = snapToGridItem.isSelected();
			}
		});
		snapToGridItem.setSelected(true);
		editMenu.add(snapToGridItem);
		
		editMenu.add(new JSeparator());
		
		temp = new JMenuItem("Clear");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fieldPanel.clear();
			}
		});
		editMenu.add(temp);
		menuBar.add(editMenu);

		JMenu viewMenu = new JMenu("View");
		final JCheckBoxMenuItem showGridLineItem = new JCheckBoxMenuItem("Show grid lines");
		showGridLineItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowProperties.displayGrid = showGridLineItem.isSelected();
				fieldPanel.repaint();
			}
		});
		viewMenu.add(showGridLineItem);
		
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
	
	private void savePNG() {
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(MainWindow.this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	File file = chooser.getSelectedFile();
        	if (!file.getName().endsWith(".png")) {
        		file = new File(file.getPath() + ".png");
        	}
        	try {
				ImageFileWriter.write(FieldImage.getImage(), "PNG", file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }
	}
	
	private void save() {
		if (saveFile == null) {
			saveAs();
			return;
		}
    	try {
			FieldSaveFileWriter.write(fieldPanel.getField(), saveFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private void saveAs() {
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("ForceSim files", "fcvs");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(MainWindow.this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	File file = chooser.getSelectedFile();
        	if (!file.getName().endsWith(".fcvs")) {
        		file = new File(file.getPath() + ".fcvs");
        	}
        	saveFile = file;
        	try {
    			FieldSaveFileWriter.write(fieldPanel.getField(), saveFile);
    		} catch (FileNotFoundException e1) {
    			e1.printStackTrace();
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
        }
	}
	
	private void open() {
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("ForceSim files", "fcvs");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(MainWindow.this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	File file = chooser.getSelectedFile();
        	if (!file.getName().endsWith(".fcvs")) {
        		file = new File(file.getPath() + ".fcvs");
        	}
        	saveFile = file;
        	try {
    			fieldPanel.setField(FieldSaveFileReader.read(saveFile));
    		} catch (FileNotFoundException e1) {
    			e1.printStackTrace();
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
        }
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
