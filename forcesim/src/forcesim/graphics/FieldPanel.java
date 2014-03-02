package forcesim.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import forcesim.field.physics.ElectromagneticForce;
import forcesim.field.physics.Field;
import forcesim.field.physics.IField;
import forcesim.field.physics.IPoint;
import forcesim.field.physics.IVector2D;
import forcesim.field.physics.Point;
import forcesim.field.physics.Vector2D;
import forcesim.util.Util;
import forcesim.window.WindowProperties;

@SuppressWarnings("serial")
public class FieldPanel extends JPanel {

	private final Color COLOR_POSITIVE = Color.red;
	private final Color COLOR_NEGATIVE = new Color(0f,0.6f,1f);
	
	private final int RADIUS = 15;

	private IField field = new Field();
	
	private List<Vector2D> lineOrigins = new ArrayList<Vector2D>();

	public FieldPanel() {
		setBackground(Color.white);
		new FieldPanelListener(this);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		FieldImage.resize(getWidth(), getHeight());
		Graphics gg = FieldImage.getImage().getGraphics();
		if (WindowProperties.renderMode == WindowProperties.RENDER_FALSE_COLOR) {
			renderField(gg);
			if (WindowProperties.displayGrid) {
				renderGridLines(gg);
			}
		} else {
			if (WindowProperties.displayGrid) {
				renderGridLines(gg);
			}
			renderLines(gg);
		}
		
		for (IPoint p: field.getPoints()) {
			renderPoint(gg, p);
		}

		g.drawImage(FieldImage.getImage(), 0, 0, Color.WHITE, null);
	}

	private void renderLines(Graphics g) {
		if (field.getPoints().length == 0) return; 
		for (Vector2D l: lineOrigins) {
			renderLine(g, l);
		}
	}
	
	private void renderLine(Graphics g, IVector2D vf1) {
		g.setColor(Color.black);
		IVector2D vclone = vf1;
		IVector2D vf2 = vf1;
		IVector2D vp1 = Util.convertFieldCoordinate(this, vf1);
		IVector2D vp2 = Util.convertFieldCoordinate(this, vf2);
		while (isValid(vp1)) {
			IVector2D v = field.getElectromagneticField(vf1.getX(), vf1.getY());
			v = v.scale(.025/v.getMagnitude());
			vf2 = vf1.sum(v);
			vp1 = Util.convertFieldCoordinate(this, vf1);
			vp2 = Util.convertFieldCoordinate(this, vf2);
			g.drawLine((int)vp1.getX(), (int)vp1.getY(), (int)vp2.getX(), (int)vp2.getY());
			vf1 = vf2;
		}
		vf1 = vclone;
		vp1 = Util.convertFieldCoordinate(this, vf1);
		while (isValid(vp1)) {
			IVector2D v = field.getElectromagneticField(vf1.getX(), vf1.getY());
			v = v.scale(-.025/v.getMagnitude());
			vf2 = vf1.sum(v);
			vp1 = Util.convertFieldCoordinate(this, vf1);
			vp2 = Util.convertFieldCoordinate(this, vf2);
			g.drawLine((int)vp1.getX(), (int)vp1.getY(), (int)vp2.getX(), (int)vp2.getY());
			vf1 = vf2;
		}
	}
	
	private boolean isValid (IVector2D v) {
		if (!(0 < v.getX() && v.getX() < getWidth()) && (0 < v.getY() && v.getY() < getWidth())) return false;
		for (IPoint p : field.getPoints()) {
			IVector2D vv = Util.convertFieldCoordinate(this, p);
			int dx = (int)(vv.getX() - v.getX());
			int dy = (int)(vv.getY() - v.getY());
			if (dx*dx + dy*dy < RADIUS*RADIUS) {
				return false;
			}
		}
		return true;
	}
	
	private void renderField(Graphics g) {
		double maxCharge = 0;
		
		for (IPoint p : field.getPoints()) {
			IPoint p2 = new Point(p.getX(), p.getY()+(((double)RADIUS-1)/WindowProperties.scale));
			p2.setCharge(1);
			double f = ElectromagneticForce.getVector(p, p2).getMagnitude();
			if (f > maxCharge) maxCharge = f;
		}
		//long time = System.currentTimeMillis();
		double[][] charges = getCharges();
		//System.out.println("Calc time "+(System.currentTimeMillis()-time));
		//time = System.currentTimeMillis();
		for (int x=0; x<getWidth(); x++) {
			for (int y=0; y<getHeight(); y++) {
				//System.out.println(charges[x][y]);
				if (charges[x][y] < maxCharge) {
					float cor = (float)Math.log10(9*Math.sqrt(Math.log10(9*(charges[x][y]/maxCharge)+1))+1);
					FieldImage.setColor(x, y, (Math.round(cor * 255)<<24) + (255<<16));
				}
			}
		}
		//System.out.println("Fill pic time "+(System.currentTimeMillis()-time));
		//time = System.currentTimeMillis();
		//System.out.println("Paint time "+(System.currentTimeMillis()-time));
	}
	
	private double[][] getCharges() {
		int cores = Runtime.getRuntime().availableProcessors();
		int threads = cores * 2;
	
	    int total = getWidth() * getHeight();
	    int size = total / threads;
	    ExecutorService pool = Executors.newFixedThreadPool(threads);
	    ArrayList<Future<Boolean>> futures = new ArrayList<>();
		final double[][] charges = new double[getWidth()][getHeight()];
	    for (final AtomicInteger start = new AtomicInteger(0); start.get() < total; start.addAndGet(size)) {
	    	final int thisStart = start.get();
	        final int end = Math.min(total, thisStart + size);
	        
	        futures.add(pool.submit(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					for (int i = thisStart; i < end; i++) {
	                	int x = i % getWidth();
	                	int y = i / getWidth();
	    				Point p = Util.convertPixelCoordinate(FieldPanel.this, x, y);
	    				double charge = field.getElectromagneticField(p.getX(), p.getY()).getMagnitude();
	    				charges[x][y] = charge;
					}
    				
    				return Boolean.TRUE;
				}
	        }));
	    }
	    for(Future<Boolean> future : futures) {
	    	try {
				future.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
	    }
	    return charges;
	}

	private void renderPoint(Graphics g, IPoint p) {
		if (p.isPositiveCharge()) {
			renderPointPositive(g, p);
		} else {
			renderPointNegative(g, p);
		}
	}

	public void renderPointPositive(Graphics g, IPoint p) {
		renderPointCircle(g, p, COLOR_POSITIVE);
		Vector2D center = Util.convertFieldCoordinate(this, p);
		int halflength = 5;
		g.setColor(Color.black);
		g.drawLine((int)center.getX()-halflength, (int)center.getY(), (int)center.getX()+halflength, (int)center.getY());
		g.drawLine((int)center.getX(), (int)center.getY()-halflength, (int)center.getX(), (int)center.getY()+halflength);
	}
	public void renderPointNegative(Graphics g, IPoint p) {
		renderPointCircle(g, p, COLOR_NEGATIVE);
		Vector2D center = Util.convertFieldCoordinate(this, p);
		int halflength = 5;
		g.setColor(Color.black);
		g.drawLine((int)center.getX()-halflength, (int)center.getY(), (int)center.getX()+halflength, (int)center.getY());

	}

	private void renderPointCircle(Graphics g, IPoint p, Color c) {
		Vector2D center = Util.convertFieldCoordinate(this, p);
		int radius = RADIUS;
		g.setColor(Color.black);
		g.fillOval((int)center.getX()-radius, (int)center.getY()-radius, 2*radius, 2*radius);
		g.setColor(c);
		radius = radius-2;
		g.fillOval((int)center.getX()-radius, (int)center.getY()-radius, 2*radius, 2*radius);
	}
	
	private void renderGridLines(Graphics g) {
		//System.out.println("WAAAAAT");
		g.setColor(Color.gray);
		int s = WindowProperties.scale;
		int x_lim = ((getWidth()/2)%s)-s;
		int y_lim = ((getHeight()/2)%s)-s;
		//System.out.println(x_lim+" "+y_lim);
		for (int x=x_lim; x<getWidth(); x+=s) {
			g.drawLine(x, 0, x, getHeight());
		}
		for (int y=y_lim; y<getHeight(); y+=s) {
			g.drawLine(0, y, getWidth(), y);
		}
	}
	
	public IPoint getPointAt(int x, int y) {
		for (IPoint p : field.getPoints()) {
			if (WindowProperties.snapToGrid) {
				IPoint t = Util.convertPixelCoordinate(this, x, y);
				t.setX(Math.round(t.getX()));
				t.setY(Math.round(t.getY()));
				Vector2D v = Util.convertFieldCoordinate(this, t);
				x = (int)v.getX();
				y = (int)v.getY();
			}
			Vector2D v = Util.convertFieldCoordinate(this, p);
			int dx = (int)v.getX() - x;
			int dy = (int)v.getY() - y;
			if (dx*dx + dy*dy < RADIUS*RADIUS) return p;
		}
		return null;
	}
	
	public void addPoint(int x, int y) {
		IPoint p = Util.convertPixelCoordinate(this,x,y);
		if (WindowProperties.snapToGrid) {
			p.setX(Math.round(p.getX()));
			p.setY(Math.round(p.getY()));
		}
		p.setCharge(WindowProperties.currentChargeChoice);
		field.addPoint(p);
		lineOrigins.clear();
	}
	public void removePoint(IPoint p) {
		field.removePoint(p);
	}
	
	public void addLine(int x, int y) {
		Point p = Util.convertPixelCoordinate(this, x, y);
		lineOrigins.add(new Vector2D(p.getX(), p.getY()));
	}
	
	public IField getField() {
		return field;
	}
	
	public void setField(IField field) {
		this.field = field;
		repaint();
	}
	
	public void clear() {
		field.clear();
		lineOrigins.clear();
		repaint();
	}
	
	private class FieldPanelListener extends MouseAdapter {

		public FieldPanelListener(FieldPanel fp) {
			addMouseListener(this);
		}

		@Override
		public void mouseClicked(MouseEvent event) {
			final IPoint p = getPointAt(event.getX(), event.getY());
			if (event.getButton() == MouseEvent.BUTTON1) {
				if (p == null) {
					if (WindowProperties.drawingPoints) {
						addPoint(event.getX(), event.getY());
					} else {
						addLine(event.getX(), event.getY());
					}
					repaint();
				}
			} else if (event.getButton() == MouseEvent.BUTTON3) {
				if (p != null) {
					JPopupMenu popup = new JPopupMenu();
					JMenuItem removePointMenu = new JMenuItem("Remove point");
					removePointMenu.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							removePoint(p);
							repaint();
						}
					});
					popup.add(removePointMenu);
					
					JMenuItem changeChargeMenu = new JMenuItem("Change charge");
					changeChargeMenu.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							boolean failAtLife = false;
							do {
								failAtLife = false;
								String response = JOptionPane.showInputDialog(FieldPanel.this, "What is the new charge?", "Enter a number.", JOptionPane.QUESTION_MESSAGE);
				                if (response == null) {
				                    return;
				                }
				                try {
				                	p.setCharge(Double.parseDouble(response));
				                	repaint();
				                } catch (NumberFormatException ex) {
				                	JOptionPane.showMessageDialog(FieldPanel.this, "Charge must be a number", "Invalid charge!", JOptionPane.ERROR_MESSAGE);
				                	failAtLife = true;
				                }
							} while (failAtLife);
						}
						
					});
					popup.add(changeChargeMenu);
					
					popup.show(event.getComponent(), event.getX(), event.getY());
				}
			}
		}
	}

}
