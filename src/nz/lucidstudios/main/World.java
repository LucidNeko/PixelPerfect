package nz.lucidstudios.main;

import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.HashMap;

import nz.lucidstudios.image.PixelImage;
import nz.lucidstudios.input.Keyboard;
import nz.lucidstudios.math.Vec2;
import nz.lucidstudios.util.Util;

public class World {
	
	private HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();
	
	public static PixelImage staticCollisionMask = new PixelImage(1024, 576);
	public static PixelImage movingCollisionMask = new PixelImage(1024, 576);;
	
	public void addEntity(Entity entity) {
		System.out.println("Adding: 0x" + Integer.toHexString(entity.getID()));
		
		entities.put(entity.getID(), entity);
	}
	
	public void removeEntity(Entity entity) {
		entities.remove(entity.getID());
//		staticObjects.remove(entity.getID());
//		movingObjects.remove(entity.getID());
	}
	
	public Collection<Entity> getEntities() {
		return entities.values();
	}
	
	private static int i = 0;
	
	public void generateCollisionMask() {
		staticCollisionMask.clear();
		movingCollisionMask.clear();
		
		for(Entity e : entities.values()) {
			if(e.isStatic()) {
				Vec2 pos = e.getPosition();
				staticCollisionMask.blit(e.getImage(), (int)pos.getX(), (int)pos.getY(), e.getID());
			} else {
				Vec2 pos = e.getPosition();
				movingCollisionMask.blit(e.getImage(), (int)pos.getX(), (int)pos.getY(), e.getID());
			}
		}
		
		if(Keyboard.isKeyDownOnce(KeyEvent.VK_P)) {
			Util.writeImage(staticCollisionMask, "staticMask" + i++ + ".png");
			Util.writeImage(movingCollisionMask, "movingMask" + i++ + ".png");
		}
	}

}
