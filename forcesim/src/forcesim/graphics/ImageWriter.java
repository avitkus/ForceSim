package forcesim.graphics;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageWriter {
	
	public static void write(RenderedImage img, String format, String target) throws IOException {
		write(img, format, new File(target));
	}
	
	public static void write(RenderedImage img, String format, File target) throws IOException {
		ImageIO.write(img, format, target);
	}
}
