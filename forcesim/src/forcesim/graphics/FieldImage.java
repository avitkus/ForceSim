package forcesim.graphics;

import java.awt.image.BufferedImage;

public class FieldImage {
	private static final int IMAGE_TYPE = BufferedImage.TYPE_4BYTE_ABGR;
	private static BufferedImage img;
	
	static {
		img = new BufferedImage(100, 100,IMAGE_TYPE);
	}
	
	public static void resize(int width, int height) {
		img = new BufferedImage(width, height, IMAGE_TYPE);
	}
	
	public static void setColor(int x, int y, int abgr) {
		img.setRGB(x, y, abgr);
	}
	
	public static int getColor(int x, int y) {
		return img.getRGB(x, y);
	}
	
	public static int getWidth() {
		return img.getWidth();
	}
	
	public static int getHeight() {
		return img.getHeight();
	}
	
	public static BufferedImage getImage() {
		return img;
	}
}
