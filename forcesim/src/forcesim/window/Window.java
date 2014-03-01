package forcesim.window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Window {

	private static final JFrame frame;
	private static final JPanel canvas;

	private static final JMenuBar menuBar;

	static {
		frame = new JFrame("Awesome Title");

		frame.setSize(400,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLocationByPlatform(true);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
		}

		canvas = new JPanel();
		frame.add(canvas);

		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenuItem temp;
		
		JMenu fileMenu = new JMenu("File");
		temp = new JMenuItem("Save");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Save");//TODO
			}
		});
		fileMenu.add(temp);
		temp = new JMenuItem("Save As");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Save As");//TODO
			}
		});
		fileMenu.add(temp);
		temp = new JMenuItem("Open");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Open");//TODO
			}
		});
		fileMenu.add(temp);
		menuBar.add(fileMenu);
		
		
		JMenu exportMenu = new JMenu("Export");
		temp = new JMenuItem("Save as PNG");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Save as PNG");//TODO
			}
		});
		exportMenu.add(temp);
		menuBar.add(exportMenu);

		JMenu editMenu = new JMenu("Edit");
		temp = new JMenuItem("Clear");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clear");//TODO
			}
		});
		editMenu.add(temp);
		menuBar.add(editMenu);

		JMenu viewMenu = new JMenu("View");
		temp = new JMenuItem("Display as Lines");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Toggle Display");//TODO
			}
		});
		viewMenu.add(temp);
		menuBar.add(viewMenu);

		//frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/GenSim icon large.png")));

		frame.setVisible(true);
	}
	
	
	
	
	
	public static final int RENDER_FALSE_COLOR=1;
	public static final int RENDER_LINES=2;
	
	private static int renderMode = RENDER_FALSE_COLOR;
	
	public static int getHeight() {
		return canvas.getHeight(); 
	}
	public static int getWidth() {
		return canvas.getWidth();
	}
	public static int getRenderMode() {
		return renderMode;
	}

	public static void main(String[] args) {

	}

}
