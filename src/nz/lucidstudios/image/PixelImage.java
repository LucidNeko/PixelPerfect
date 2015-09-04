package nz.lucidstudios.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class PixelImage implements Cloneable{
	
	public static final int DEFAULT_CLEAR_COLOR = 0x00000000;
	
	private final int width;
	private final int height;
	
	private final BufferedImage image;
	private final int[] data;
	
	/** Create a new PixelImage of dimensions width height */
	public PixelImage(int width, int height) {
		this.width = width;
		this.height = height;
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.data = ((DataBufferInt)(image.getRaster().getDataBuffer())).getData();
	}
	
	/** Create a new PixelImage reading pixel data from src */
	public PixelImage(BufferedImage src) {
		this(src.getWidth(), src.getHeight());
		
		Graphics2D g = this.image.createGraphics();
		g.drawImage(src, null, 0, 0);
		g.dispose();
	}
	
	/**
	 * Get the ARGB value of the pixel at (x, y)
	 * @throws IndexOutOfBoundsException
	 */
	public int getRGB(int x, int y) {
		return data[y*width + x];
	}
	
	/**
	 * Set the ARGB value of the pixel at (x, y)
	 * @throws IndexOutOfBoundsException
	 */
	public void setRGB(int x, int y, int rgb) {
		data[y*width + x] = rgb;
	}
	
	/** Get the width of this image */
	public int getWidth() {
		return this.width;
	}
	
	/** Get the height of this image */
	public int getHeight() {
		return this.height;
	}
	
	/** Get the BufferedImage that underlies this PixelImage */
	public BufferedImage getImage() {
		return image;
	}
	
	/**
	 * Returns a subimage defined by a specified rectangular region.
	 * @param offsetX the X coordinate of the upper-left corner of the specified rectangular region
	 * @param offsetY the Y coordinate of the upper-left corner of the specified rectangular region
	 * @param width the width of the specified rectangular region
	 * @param height the height of the specified rectangular region
	 * @return a new PixelImage that is the subimage of this PixelImage
	 */
	public PixelImage getSubImage(int offsetX, int offsetY, int width, int height) {
		PixelImage out = new PixelImage(width, height);
		
		for(int y = 0; y < out.height; y++) {
			for(int x = 0; x < out.width; x++) {
				int srcX = offsetX + x;
				int srcY = offsetY + y;
				if(srcX >= 0 && srcX < this.getWidth() && srcY >= 0 && srcY < this.getHeight()) {
					out.setRGB(x, y, this.getRGB(srcX, srcY));
				}
			}
		}
		
		return out;
	}
	
	public PixelImage getScaledInstance(int factor) {
		PixelImage out = new PixelImage(this.width * factor, this.height * factor);
		
		for(int y = 0; y < out.height; y++) {
			for(int x = 0; x < out.width; x++) {
				out.setRGB(x, y, this.getRGB(x/factor, y/factor));
			}
		}
		
		return out;
	}
	
	/**
	 * Blit img into this image from position (x, y).
	 * If the provided (x, y) or (x+img.width, y+img.height) are out of bounds the pixels are simply ignored.
	 * @param img
	 * @param x
	 * @param y
	 */
	public void blit(PixelImage img, int x, int y) {
		for(int srcY = 0; srcY < img.getHeight(); srcY++) {
			for(int srcX = 0; srcX < img.getWidth(); srcX++) {
				int dstX = x + srcX;
				int dstY = y + srcY;
				if(dstX >= 0 && dstX < this.getWidth() && dstY >= 0 && dstY < this.getHeight()) {
					int alpha = img.getRGB(srcX, srcY) >> 24 & 0xFF;
					if(alpha != 0) {
						this.setRGB(dstX, dstY, img.getRGB(srcX, srcY));
					}
				}
			}
		}
	}
	
	public void blit(PixelImage img, int x, int y, int colorOverride) {
		for(int srcY = 0; srcY < img.getHeight(); srcY++) {
			for(int srcX = 0; srcX < img.getWidth(); srcX++) {
				int dstX = x + srcX;
				int dstY = y + srcY;
				if(dstX >= 0 && dstX < this.getWidth() && dstY >= 0 && dstY < this.getHeight()) {
					int alpha = img.getRGB(srcX, srcY) >> 24 & 0xFF;
					if(alpha != 0) {
						this.setRGB(dstX, dstY, colorOverride);
					}
				}
			}
		}
	}
	
	private PixelImage scratch = null;
	public boolean overlap(PixelImage img, int x, int y, int ignoreARGB) {
//		PixelImage out = new PixelImage(img.getWidth(), img.getHeight());
		
		for(int srcY = 0; srcY < img.getHeight(); srcY++) {
			for(int srcX = 0; srcX < img.getWidth(); srcX++) {
				int dstX = x + srcX;
				int dstY = y + srcY;
				if(dstX >= 0 && dstX < this.getWidth() && dstY >= 0 && dstY < this.getHeight()) {
//					System.out.println("this: 0x" + Integer.toHexString(this.getRGB(dstX, dstY)));
//					System.out.println("othe: 0x" + Integer.toHexString(img.getRGB(srcX, srcY)));
					if(this.getRGB(dstX, dstY) != ignoreARGB) {
						
						if((this.getRGB(dstX, dstY) >> 24 & 0xFF) != 0 && (img.getRGB(srcX, srcY) >> 24 & 0xFF) != 0) {
							return true;
						}
					}
					
				}
			}
		}
		return false;
	}
	
	public void clear() {
		fill(DEFAULT_CLEAR_COLOR);
	}
	
	public void fill(int rgb) {
		Arrays.fill(data, rgb);
	}
	
	@Override
	public PixelImage clone() {
		PixelImage out = new PixelImage(width, height);
		
		for(int i = 0; i < this.data.length; i++) {
			out.data[i] = this.data[i];
		}
		
		return out;
	}

}
