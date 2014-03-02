package forcesim.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import forcesim.field.physics.Field;
import forcesim.field.physics.IField;
import forcesim.field.physics.IPoint;
import forcesim.field.physics.Point;
import forcesim.window.WindowProperties;

public class FieldSaveFileReader {
	public static IField read(File file) throws FileNotFoundException, IOException {
		IField field = new Field();
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			String[] heads = br.readLine().split(",");
			String[] values = br.readLine().split(",");
			loadWindowProperties(heads, values);
			
			heads = br.readLine().split(",");
			while(br.ready()) {
				System.out.println("a point");
				values = br.readLine().split(",");
				field.addPoint(loadPoint(heads, values));
			}
		}
		return field;
	}
	
	private static IPoint loadPoint(String[] heads, String[] values) {
		IPoint point = new Point();
		for(int i = 0; i < heads.length; i ++) {
			switch(heads[i]) {
				case "x":
					point.setX(Double.parseDouble(values[i]));
					break;
				case "y":
					point.setY(Double.parseDouble(values[i]));
					break;
				case "charge":
					point.setCharge(Double.parseDouble(values[i]));
					break;
			}
		}
		return point;
	}
	
	private static void loadWindowProperties(String[] heads, String[] values) {
		for(int i = 0; i < heads.length; i ++) {
			switch(heads[i]) {
				case "renderMode":
					WindowProperties.renderMode = Integer.parseInt(values[i]);
					break;
				case "scale":
					WindowProperties.scale = Integer.parseInt(values[i]);
					break;
				case "currentChargeChoice":
					WindowProperties.currentChargeChoice = Double.parseDouble(values[i]);
					break;
				case "displayGrid":
					WindowProperties.displayGrid = Boolean.parseBoolean(values[i]);
					break;
				case "drawingPoints":
					WindowProperties.drawingPoints = Boolean.parseBoolean(values[i]);
					break;
				case "snapToGrid":
					WindowProperties.snapToGrid = Boolean.parseBoolean(values[i]);
					break;
				case "type":
					WindowProperties.type = ForceType.valueOf(values[i]);
					break;
			}
		}
	}
}
