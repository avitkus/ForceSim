package forcesim.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import forcesim.field.physics.IField;
import forcesim.field.physics.IPoint;
import forcesim.window.WindowProperties;

public class FieldSaveFileWriter {
	public static void write(IField field, File file) throws FileNotFoundException, IOException {
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			bw.write("renderMode, scale, currentChargeChoice, displayGrid, drawingPoints, snapToGrid, type\n");
			bw.write(WindowProperties.renderMode + ",");
			bw.write(WindowProperties.scale + ",");
			bw.write(Double.toString(WindowProperties.currentChargeChoice) + ",");
			bw.write(Boolean.toString(WindowProperties.displayGrid) + ",");
			bw.write(Boolean.toString(WindowProperties.drawingPoints) + ",");
			bw.write(Boolean.toString(WindowProperties.snapToGrid) + ",");
			bw.write(WindowProperties.type.name() + "\n");
			
			IPoint[] points = field.getPoints();
			if (points.length > 0) {
				bw.write(points[0].dataStringHeader() + "\n");
				for(IPoint p : points) {
					bw.write(p.toDataString() + "\n");
				}
			}
		}
	}
}
