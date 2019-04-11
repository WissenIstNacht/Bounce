package bounce;
import processing.core.PApplet;

public class Player {
	
	private PApplet parent;
	
	boolean isMoving;
	int direction;
	float g;

	
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
		g = -1.0f/30;
	}
	
	/** Moves the player on user input. Direction can be only 1 (right) or -1 (left).
	 * 
	 * @param dir	integer in {-1,1}.
	 */
	public void moveHor(){
		if(isMoving) {
			xPos += direction*xVel;
		}
	}
	
	/** Moves the player vertically. Direction depends on environment (collision with
	 * pad or sky).
	 * 
	 * @param collision		true iff player just collided with pad
	 */
	public void moveVert(boolean collision) {
		System.out.println(g);

		if(collision) {
			yVel *= -1;
			yPos = 55;
		}else {
			yVel += g;
			yPos += yVel;
		}
	}
	
	public void show() {
		parent.fill(255,0,0);
		parent.ellipse(xPos, yPos, 3, 3);
		parent.fill(255);
		parent.ellipse(xPos, yPos, size, size);
		
	}
}

