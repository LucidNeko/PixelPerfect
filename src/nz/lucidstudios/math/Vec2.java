package nz.lucidstudios.math;

/**
 * Vec2 is a class representing a 2 component Vector. (x, y)
 * @author Hamish Rae-Hodgson.
 *
 */
public class Vec2 {
	
	public float x;
	public float y;
	
	/** Construct a new vector with (x, y) = (0, 0) */
	public Vec2() {
		x = y = 0;
	}
	
	/** Construct a new vector at (x, y) */
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/** Construct a new vector with (x, y) = (source.x, source.y) */
	public Vec2(Vec2 source) {
		this.x = source.x;
		this.y = source.y;
	}
	
	/** Get the x component of this vector */
	public float x() {
		return x;
	}
	
	/** Get the y component of this vector */
	public float y() {
		return y;
	}
	
	/** Get the x component of this vector */
	public float getX() {
		return x;
	}
	
	/** Get the y component of this vector */
	public float getY() {
		return y;
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(Vec2 v) {
		this.x = v.x;
		this.y = v.y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public Vec2 sub(Vec2 v) {
		return sub(v.x, v.y);
	}
	
	public Vec2 sub(float x, float y) {
		return new Vec2(this.x - x, this.y - y);
	}
	
	public Vec2 add(Vec2 v) {
		return add(v.x, v.y);
	}
	
	public Vec2 add(float x, float y) {
		return new Vec2(this.x + x, this.y + y);
	}
	
	public Vec2 addLocal(Vec2 v) {
		return addLocal(v.x, v.y);
	}
	
	public Vec2 addLocal(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vec2 mul(float factor) {
		return new Vec2(this.x * factor, this.y * factor);
	}
	
	public Vec2 clone() {
		return new Vec2(x, y);
	}
	
	public String toString() {
		return String.format("(%.2f,  %.2f)", x, y);
	}

}
