package ppPackage;

import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.*;

public class ppPaddleAgent extends ppPaddle {
	
	//Instance variables
	private double X;
	private double Y;
	private double Vy;
	public static int difficulty = 1;
	ppBall myBall;
	Color myColor;
	ppTable myTable;
	GRect theAgent;
	
	//Constructor of a ppPaddleAgent object
	public ppPaddleAgent(double X, double Y, Color myColor, ppTable myTable) {
		
		//Using ppPaddle constructor's template
		super(myTable.toScrX(X - bSize / 2 - ppPaddleW / 2), myTable.toScrY(Y - ppPaddleH / 2 + bSize), myColor, myTable);
		this.myTable = myTable;
		
	}
	
	//Exported methods
	
	//Sets the value of difficulty
	public static void setDifficulty(int value) {
		difficulty = value;
	}
	
	//Sets the value of the Xpos of the paddle
	public void setX(double x) {
		X = x;
	}
	
	//Sets the value of the Ypos of the paddle
	public void setY(double y) {
		Y = y;
		myPaddle.setLocation(myTable.toScrX(X), myTable.toScrY(Y));
	}
	
	//Returns the value of the Xpos of the paddle
	public double getX() {
		return X;
	}
	
	//Returns the value of the Ypos of the paddle
	public double getY() {
		return scrHEIGHT/SCALE - Y;
	}
	
	//Attaches the ball to the paddleAgent
	public void attachBall(ppBall myBall) {
		this.myBall = myBall;
	}
	
	//Checks if the ball comes in contact with the paddleAgent
	public boolean contact(double Sx, double Sy) { 
		
		boolean YMAX = Sy - bSize <= getY();
		boolean YMIN = Sy + bSize >= getY() - ppPaddleH;
		boolean XMIN = Sx + 2*bSize >= getX();

		return (XMIN && YMAX && YMIN ); 
		
	}

	public double getVy() {
		return Vy;
	}
	
	//Returns sign of the velocity of the paddleAgent
	public double getSgnVy() {

		if (getVy() < 0) {
			return -1;
		} else {
			return 1;
		}

	}
	
	public void run() {
		
		while (true) {
		
		Vy = ppBall.getVy();
			
		X = ppPaddleAgentXinit - bSize/2 - ppPaddleW/2;
		Y = ppBall.getY() - ppPaddleH/2 + bSize;
			
		setX(X);
		
		//Interrupts the setY method for the paddle agent to make the game fair
		try {
		    Thread.sleep((5-difficulty) * 75);
		} catch (InterruptedException ex) {
			
		}
		
		setY(Y);
		
		//System.out.printf("t: %.4f Vx: %.4f Vy: %.4f\n", ppBall.getTime() ,getVx(), getVy());

		myTable.getDisplay().pause(TICK/ppBall.getTICKMULTIPLIER() * TIMESCALE);
		
		}
		
	}

	
	
	
	
}
