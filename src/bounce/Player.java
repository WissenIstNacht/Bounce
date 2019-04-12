package bounce;
import processing.core.PApplet;

public class Player {
	
	private PApplet parent;
	
	boolean isMoving;
	int direction;
	float g;

	int score = 0;
	
	int size = 50;
	float xPos, yPos;
	float xVel = 2;
	float ySpeed;
	float yVel;
	
	
	/** CONSTRUCTOR
	 * @param parent_
	 */
	public Player(PApplet parent_) {
		parent = parent_;
		xPos = parent.width/2;
		yPos = 300;
		yVel = 0;
		g = -1.0f/15;
	}
	
	/** Determines next location of player, taking into account collision and 
	 * wrapping around walls.
	 * 
	 * @param collision		true iff player just collided with pad
	 */
	public void move(boolean collision) {
		if(isMoving) {
			xPos += direction*xVel;
		}
		if(xPos < 0) {
			xPos = parent.width - xPos;
		}else if(xPos > parent.width) {
			xPos = xPos - parent.width;
		}
		
		if(collision) {
			yVel *= -1;
			yPos = 55;
		}else {
			yVel += g;
			yPos += yVel;
		}
	}
	
	/** Determines whether player is making contact with pad.
	 * 
	 * @param pad
	 * 
	 * @return boolean	true iff southern most point of player touches top of pad.
	 */
	boolean isColliding(Pad pad) {
		boolean xCondition = xPos >= pad.xPos;
		xCondition = xCondition && xPos <= pad.xPos + pad.w;
		
		boolean yCondition = yPos - size/2 <= pad.yPos+pad.h;
		
		return xCondition && yCondition;
	}
	
	public void show() {
		parent.fill(255,0,0);
		parent.ellipse(xPos, yPos, 3, 3);
		parent.fill(255);
		parent.ellipse(xPos, yPos, size, size);
		
	}
}

