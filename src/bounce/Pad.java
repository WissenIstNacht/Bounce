package bounce;
import processing.core.PApplet;

public class Pad {
	
	private PApplet parent;
	
	float xPos = 350, yPos = 6;
	float w = 100, h = 20;
	
	public Pad(PApplet parent_) 
	{
		parent = parent_;
	}
	
	
	/** Move the pad to new random location after a bounce.
	 * @param col
	 */
	public void move(boolean col) {
		xPos = col ? parent.random(parent.width-w) : xPos;
	}
	
	public void show() {
		parent.rect(xPos, yPos, w, h);
	}
}
