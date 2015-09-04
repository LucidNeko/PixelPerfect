package nz.lucidstudios.main;

import java.awt.event.KeyEvent;

import nz.lucidstudios.input.Keyboard;
import nz.lucidstudios.math.Vec2;
import nz.lucidstudios.util.R;

public class Level {
	
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;
	
	public static final char KEY_BLOCK = '#';
	public static final char KEY_PLAYER = 'P';
	public static final char KEY_SPIKE = 'X';
	public static final char KEY_SLOPE_LEFT = 'L';
	public static final char KEY_SLOPE_RIGHT = 'R';
	
	private static final String[] levels = new String[]{
		"################################\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"################################\n",
		
		"################################\n" +
		"#                           R  #\n" +
		"#                          R   #\n" +
		"#                         R    #\n" +
		"# P P P P P P P P        R     #\n" +
		"#                       R      #\n" +
		"#                      R       #\n" +
		"#                     R        #\n" +
		"#                    R         #\n" +
		"#L                  R         R#\n" +
		"##L                R         R##\n" +
		"###L              R         R###\n" +
		"####L            R         R####\n" +
		"#####L          R         R#####\n" +
		"######L        R         R######\n" +
		"#######L      R      R##########\n" +
		"########L    R     R############\n" +
		"################################\n",
		
		"################################\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#   P      P                   #\n" +
		"#                   P          #\n" +
		"#        P                     #\n" +
		"#                 P            #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#             P                #\n" +
		"#                         P    #\n" +
		"#                              #\n" +
		"#            X                 #\n" +
		"##         XR#LX              R#\n" +
		"###       R#####L   XXXX     R##\n" +
		"####     R##P  ##L R####L   R###\n" +
		"#####L   ###   ### ######  R####\n" +
		"######XXX#########X######X######\n", 
		
		"################################\n" +
		"################################\n" +
		"################################\n" +
		"################################\n" +
		"################################\n" +
		"################################\n" +
		"################################\n" +
		"################################\n" +
		"################################\n" +
		"################################\n" +
		"################################\n" +
		"################################\n" +
		"################################\n" +
		"################################\n" +
		"################################\n" +
		"################################\n" +
		"################################\n" +
		"################################\n",
		
		"################################\n" +
		"#XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX#\n" +
		"################################\n" +
		"#XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX#\n" +
		"################################\n" +
		"#XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX#\n" +
		"################################\n" +
		"#XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX#\n" +
		"################################\n" +
		"#XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX#\n" +
		"################################\n" +
		"#XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX#\n" +
		"################################\n" +
		"#XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX#\n" +
		"################################\n" +
		"#XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX#\n" +
		"#                              #\n" +
		"################################\n",
	};
	
	public static World load(int level) {
		return load(levels[level%levels.length]);
	}
	
	private static World load(String level) {
		
		World world = new World();
		
		String[] lines = level.split("\\n");
		
		for(int y = 0; y < lines.length; y++) {
			for(int x = 0; x < lines[y].length(); x++) {
				Entity entity;
				switch(lines[y].charAt(x)) {
				case KEY_BLOCK :
					entity = new Entity(x*TILE_WIDTH, y*TILE_HEIGHT);
					entity.setImage(R.environment.block);
					entity.setStatic(true);
					world.addEntity(entity);
					break;
				case KEY_SLOPE_LEFT :
					entity = new Entity(x*TILE_WIDTH, y*TILE_HEIGHT);
					entity.setImage(R.environment.slope_left);
					entity.setStatic(true);
					world.addEntity(entity);
					break;
				case KEY_SLOPE_RIGHT :
					entity = new Entity(x*TILE_WIDTH, y*TILE_HEIGHT);
					entity.setImage(R.environment.slope_right);
					entity.setStatic(true);
					world.addEntity(entity);
					break;
				case KEY_SPIKE :
					entity = new Entity(x*TILE_WIDTH, y*TILE_HEIGHT);
					entity.setImage(R.environment.spike);
					entity.setStatic(true);
					world.addEntity(entity);
					break;
				case KEY_PLAYER :
					entity = new Entity(x*TILE_WIDTH, y*TILE_HEIGHT) {
						@Override
						public void update(float delta) {
							Vec2 move = new Vec2();
							if(Keyboard.isKeyDown(KeyEvent.VK_A)) {
								move.x -= 0.1f;
							}
							if(Keyboard.isKeyDown(KeyEvent.VK_D)) {
								move.x += 0.1f;
							}
							if(Keyboard.isKeyDown(KeyEvent.VK_W)) {
								move.y -= 0.1f;
							}
							if(Keyboard.isKeyDown(KeyEvent.VK_S)) {
								move.y += 0.1f;
							}
							this.move(move);
						}
					};
					entity.setImage(R.characters.rule);
					entity.setStatic(false);
					world.addEntity(entity);
					break;
				}
			}
		}
		
//		Entity entity = new Entity(147, 328);
//		entity.setImage(R.characters.mami_run);
//		world.addEntity(entity, World.Type.MOVING);
		
		return world;
	}

}
