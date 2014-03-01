package forcesim.window;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
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
		canvas = new JPanel();
		frame.add(canvas);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		menuBar.add(new JLabel("lol"));
		
		frame.setLocationByPlatform(true);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
        }

        //frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/GenSim icon large.png")));
		
		frame.setVisible(true);
	}
	
	public static int getHeight() {
		return canvas.getHeight(); 
	}
	public static int getWidth() {
		return canvas.getWidth();
	}
	
	public static void main(String[] args) {
		
	}
	
}