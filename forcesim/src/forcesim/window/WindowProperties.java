package forcesim.window;

import forcesim.util.ForceType;

public class WindowProperties {
	public static final int RENDER_FALSE_COLOR=1;
	public static final int RENDER_LINES=2;
	
	public static int renderMode = RENDER_FALSE_COLOR;
	
	public static int scale = 40; // pixels per meter
	
	public static ForceType type = ForceType.ELECTROMAGNETIC;
	
	public static double currentChargeChoice = 1;
	public static boolean drawingPoints = true;
	
	public static boolean snapToGrid=true;
	public static boolean displayGrid=false;
}
