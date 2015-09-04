package nz.lucidstudios.main;

import java.awt.Rectangle;

import nz.lucidstudios.image.PixelImage;
import nz.lucidstudios.math.AABB;
import nz.lucidstudios.math.Vec2;
import nz.lucidstudios.util.Util;

public class Entity {
	
	private final int id = IDGen.get();
	
	private boolean isStatic = false;
	
	protected final Vec2 move;
	protected final Vec2 oldPosition;
	protected final Vec2 position;
	
	private PixelImage image = null;
	
	public Entity() {
		this(0, 0);
	}
	
	public Entity(float x, float y) {
		move = new Vec2();
		oldPosition = new Vec2(x, y);
		position = new Vec2(x, y);
	}
	
	public int getID() {
		return id;
	}
	
	public PixelImage getImage() {
		return image;
	}
	
	public Vec2 getPosition() {
		return position.clone();
	}
	
	/** sets the out vector to this entities position */
	public void getPosition(Vec2 out) {
		out.set(position);
	}
	
	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
	
	public boolean isStatic() {
		return isStatic;
	}
	
	public void setPosition(Vec2 position) {
		this.position.set(position);
	}
	
	public void setImage(PixelImage image) {
		this.image = image;
	}
	
	public void move(Vec2 move) {
		this.move.addLocal(move);
	}
	
	public void update(float delta) {
		
	}
	
	public void integrate(float delta) {
		if(!isStatic) {
			Vec2 acc = new Vec2(0f, 100f); //gravity

			Vec2 vel = position.add(move).sub(oldPosition);
			
			Vec2 next = position.add(vel).add(acc.mul(delta*delta));
			
			next = handleCollision(position, next.sub(position), World.staticCollisionMask, World.movingCollisionMask);
			
			oldPosition.set(position);
			position.set(next);
			move.set(0, 0);
		}
	}
	
	private int i = 0;
	
	//TODO: step biggest delta first
	public Vec2 handleCollision(Vec2 old, Vec2 delta, PixelImage staticMask, PixelImage movingMask) {
		
		if(staticMask.overlap(image, (int)(old.x + delta.x), (int)(old.y), this.getID()) || movingMask.overlap(image, (int)(old.x + delta.x), (int)(old.y), this.getID())) {
			delta.x = 0;
		} else {
			//move delta
		}
		
		//y TODO: Invert y?
		if(staticMask.overlap(image, (int)(old.x), (int)(old.y + delta.y), this.getID()) || movingMask.overlap(image, (int)(old.x), (int)(old.y + delta.y), this.getID())) {
			delta.y = 0;
		} else {
			//move delta
		}
		
		return old.add(delta);
	}
	
//	private float stepX(Vec2 old, Vec2 delta, PixelImage collisionMask) {
//		
//	}
//	
//	private float stepY(Vec2 old, Vec2 delta, PixelImage collisionMask) {
//		
//	}
	
	public AABB getAABB() {
		AABB aabb = new AABB();
		if(image != null) {
			aabb.center.x = image.getWidth()/2f;
			aabb.center.y = image.getHeight()/2f;
			aabb.extents.x = aabb.center.x;
			aabb.extents.y = aabb.center.y;
		}
		return aabb;
	}

	public void render(PixelImage canvas) {
		canvas.blit(image, (int)position.getX(), (int)position.getY());
	}
	
	private static class IDGen {
		private static int i = 0;
		public static int get() {
			if((0xFF000000 | i) == 0xFFFFFFFF) {
				System.err.println("All entities from now will have the ID 0xFFFFFFFF");
			}
			return 0xFF000000 | i++; 
		}
	}

}
