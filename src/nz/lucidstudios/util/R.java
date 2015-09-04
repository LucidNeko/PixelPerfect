package nz.lucidstudios.util;

import nz.lucidstudios.image.PixelImage;

/** Resource class. All resources retrieved from R are shared. Invoke the objects clone method (if supported) to get a non-shared instance. */
public class R {
	
	private static final String dir = "assets/";
	
	public static class characters {
		private static final String dir = R.dir + "characters/";
		
		public static final PixelImage rule = Util.loadImage(dir + "rule.png");
		public static final PixelImage mami_run = Util.loadImage(dir + "mami_run_f8_w70_h110.png");
		
		
//		public static class hero {
//			private static final String dir = R.characters.dir + "hero/";
//		}
	}
	
	public static class environment {
		private static final String dir = R.dir + "environment/";
		
		public static final PixelImage block = Util.loadImage(dir + "block.png");
		public static final PixelImage spike = Util.loadImage(dir + "spike.png");
		public static final PixelImage slope_left = Util.loadImage(dir + "slope_left.png");
		public static final PixelImage slope_right = Util.loadImage(dir + "slope_right.png");
	}

}
