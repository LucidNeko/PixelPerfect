package nz.lucidstudios.util;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import nz.lucidstudios.image.PixelImage;

public class Util {
	
	public static PixelImage loadImage(String fpath) {
		try {
			return new PixelImage(ImageIO.read(new File(fpath)));
		} catch (IOException e) {
			System.err.print("Unable to load image at path '" + fpath + "' because: " + e.getMessage() + "\n");
			return null;
		}
	}
	
	public static void writeImage(PixelImage image, String fpath) {
		try {
			ImageIO.write(image.getImage(), "png", new File(fpath));
		} catch (IOException e) {
			System.err.print("Unable to write image to path '" + fpath + "' because: " + e.getMessage() + "\n");
		}
	}
	
	public static void renderAsMask(PixelImage canvas, PixelImage img, int maskColor) {
		
	}

}
