/************************************************************************

	Author: WIN
	Date: 09.04.2019

	The goal of this game is for the player not to fall through the ground for as
	long as possible. In order to do so the player can use a pad to bounce off from.
	The pad however moves randomly after each bounce.
	
	- Basic Implementation:
		Status: DONE @date 09.04.2019

************************************************************************/

package bounce;

import processing.core.PApplet;


public class Bounce extends PApplet {

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
	}
	
	public void setup() {
		background(0);
		frameRate(60);
		currState = GameState.WELCOME;
		setGame();
	}

	public void draw() {
		switch (currState) {
		case WELCOME:
			WelcomeScreen();
			break;
		case INPROGRESS:
			runGame();
			break;
		case OVER:
			gameOverScreen();
			break;
		}
	}
	
	public void keyPressed() {
		switch (keyCode) {
		case LEFT:
			player.moveSide(-1);
			break;
		case RIGHT:
			player.moveSide(1);
			break;
		case ENTER:
			/* start the game when in welcome state */
			if(currState == GameState.WELCOME) {
				currState = GameState.INPROGRESS;
			}
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
		}
	}
	
	/*******************************************************************************/
	/*                Program Functions                                            */
	/*******************************************************************************/
	
	public void setGame() {
		player = new Player(this);
		pad = new Pad(this);
	}
	
	boolean isColliding() {
		
		boolean xCondition = player.xPos >= pad.xPos;
		xCondition = xCondition && player.xPos + player.size <= pad.xPos + pad.w;
		
		boolean yCondition = player.yPos - player.size/2 <= pad.yPos+pad.h;
		
		return xCondition && yCondition;
	}
	
	public boolean isOver() {

		boolean xCondition = player.xPos < pad.xPos;
		xCondition = xCondition || player.xPos > pad.xPos + pad.w;
		boolean yCondition = player.yPos - player.size/2 < pad.yPos + pad.h;

		return xCondition && yCondition;
	}

	public void WelcomeScreen(){
		text("HI", width/2, height/2);
	}
	
	public void runGame() {
		background(0);
		scale(1,-1);
		translate(0, -height);
		
		/* Update game */
		boolean b = isColliding();
		if(b) {
			System.out.println("true");
		}
		player.moveVert(b);
		pad.move(b);
		
		currState = isOver() ? GameState.OVER : GameState.INPROGRESS;
		
		/* Display objects */
		pad.show();
		player.show();
	}
	
	public void gameOverScreen(){
		background(255,0,0);
		scale(1,-1);
		textSize(50);
		text("GAME OVER!", width/2- 100, height/2);
	}
	

	/*******************************************************************************/
	/*                MAIN                                                         */
	/*******************************************************************************/
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { bounce.Bounce.class.getName() });
	}
}
