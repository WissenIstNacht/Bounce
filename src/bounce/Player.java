package bounce;
import processing.core.PApplet;

public class Player {
	
	private PApplet parent;
	
	int size = 50;
	float xPos, yPos;
	float xVel = 5;
	float ySpeed = 3;
	float yVel;
	
	
	/** CONSTRUCTOR
	 * @param parent_
	 */
	public Player(PApplet parent_) {
		parent = parent_;
		xPos = parent.width/2;
		yPos = 300;
		yVel = -1;
	}
	
	/** Moves the player on user input. Direction can be only 1 (right) or -1 (left).
	 * 
	 * @param dir	integer in {-1,1}.
	 */
	public void moveSide(int dir){
		xPos += dir*xVel;
	}
	
	/** Moves the player vertically. Direction depends on environment (collision with
	 * pad or sky).
	 * 
	 * @param collision		true iff player just collided with pad
	 */
	public void moveVert(boolean collision) {
		if(yPos > parent.height*0.75) {
			yVel = -1;
		}
		if(collision) {
			yVel = 1;
		}
		System.out.println(yVel);
		yPos += yVel*ySpeed;
	}
	
	public void show() {
		parent.fill(255,0,0);
		parent.ellipse(xPos, yPos, 3, 3);
		parent.fill(255);
		parent.ellipse(xPos, yPos, size, size);
		
	}
}

