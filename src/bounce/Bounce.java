/************************************************************************

	Author: WIN
	Date: 09.04.2019

	The goal of this game is for the player not to fall through the ground for as
	long as possible. In order to do so the player can use a pad to bounce off from.
	The pad however moves randomly after each bounce.
	
	- Basic Implementation:
		DONE @date 09.04.2019
	- Improve player movement
		DONE @date 11.04.2019
	- Improve Bouncing feel
		DONE @date 11.04.2019
	- Fix Collision
		DONE @date 11.04.2019
	- Add Screens
		DONE @date 11.04.2019
	- Wrap around walls
		DONE @date 12.04.2019

************************************************************************/

package bounce;

import controlP5.Button;
import controlP5.ControlP5;
import processing.core.PApplet;

public class Bounce extends PApplet {

	float wp, hp;
	int bg;
	
	Button b_start;
	ControlP5 gui;
	
	Player player;
	Pad pad;
	
	enum GameState{
		WELCOME,
		INPROGRESS,
		OVER
	}
	GameState currState;
	
	/*******************************************************************************/
	/*                Processing Functions                                         */
	/*******************************************************************************/
	
	public void settings() {
		size(750,600);
		smooth();
	}
	
	public void setup() {
		bg = color(random(255), random(255), random(255));
		background(bg);
		frameRate(60);
		
		wp = width/100f;
		hp = height/100f;
		
		gui = new ControlP5(this);
		b_start = new Button(gui, "start button");
		b_start.setPosition(38*wp, 60*hp)
			.setCaptionLabel("START GAME!")
			.setSize(round(24*wp), round(15*hp));
		currState = GameState.WELCOME;
	}

	public void draw() {
		switch (currState) {
		case WELCOME:
			WelcomeScreen();
			break;
		case INPROGRESS:
			updateGame();
			break;
		case OVER:
			gameOverScreen();
			break;
		}
	}
	
	public void keyPressed() {
		switch (keyCode) {
		case LEFT:
			player.isMoving = true;
			player.direction = -1;
			break;
		case RIGHT:
			player.isMoving = true;
			player.direction = 1;
			break;
		case 'R':
			/* Restart the game */
			setGame();
			currState = GameState.INPROGRESS;
			break;
		case ESC:
			exit();
		default:
			break;
		};
	}
	
	public void keyReleased() {
		switch (keyCode) {
		case LEFT:
			player.isMoving = false;
			return;
		case RIGHT:
			player.isMoving = false;
			return;	
		default:
			break;
		}
	}
	
	/*******************************************************************************/
	/*                Program Functions                                            */
	/*******************************************************************************/
	
	public void setGame() {
		player = new Player(this);
		pad = new Pad(this);
	}
	
	
	
	public boolean isOver() {

		boolean xCondition = player.xPos < pad.xPos;
		xCondition = xCondition || player.xPos > pad.xPos + pad.w;
		boolean yCondition = player.yPos - player.size/2 < pad.yPos + pad.h;

		return xCondition && yCondition;
	}

	public void WelcomeScreen(){
		textSize(60);
		textAlign(CENTER, CENTER);
		text("Welcome to Bounce!", width/2, 25*hp);
		textSize(25);
		textAlign(CENTER, CENTER);
		text("Use the left/right arrow keys to move", width/2, 40*hp);
		
		if(b_start.isPressed()) {
			if(currState == GameState.WELCOME) {
				currState = GameState.INPROGRESS;
			}
			setGame();
			b_start.hide();
		}
	}
	
	public void updateGame() {
		background(bg);
		scale(1,-1);
		translate(0, -height);
		
		/* Update game */
		boolean b = player.isColliding(pad);
		
		player.move(b);
		pad.move(b);
		
		if(!b) {
			currState = isOver() ? GameState.OVER : GameState.INPROGRESS;
		}else {
			bg = color(random(255), random(255), random(255));
			player.score++;
		}
		
		/* Display objects */
		pad.show();
		player.show();
		
		translate(0, height);
		scale(1,-1);
		textAlign(RIGHT, TOP);
		String score = String.valueOf(player.score);
		textSize(30);
		text(score, width, 0);
		
	}
	
	public void gameOverScreen(){
		background(bg);
		textSize(60);
		textAlign(CENTER, CENTER);
		text("GAME OVER!", width/2, 25*hp);
		textSize(25);
		textAlign(CENTER, CENTER);
		text("Press 'r' to restar the game, 'ESC' to quit.", width/2, 40*hp);
		
		
	}
	
	/*******************************************************************************/
	/*                MAIN                                                         */
	/*******************************************************************************/
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { bounce.Bounce.class.getName() });
	}
}
