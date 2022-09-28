package ppPackage;

import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.*;

public class ppBall extends Thread {

	//Instance variables
	private static double time = 0;
	static int TICKMULTIPLIER = 1;
	private static int scoreAI = 0;
	private static int scorePlayer = 0;
	private static double Vy;
	private double Xinit;
	private double Yinit;
	private double Vo;
	private double theta;
	private double loss;
	private static boolean traceOn = false;
	private boolean hasEnergy;
	private ppTable myTable;
	private ppPaddle myPaddle;
	ppPaddleAgent theAgent;
	private Color color;
	static GOval myBall;
	
	//Constructor of a ppBall object
	public ppBall(double Xinit, double Yinit, double Vo, double theta, Color color, double loss, ppTable myTable,
			boolean traceOn) {

		this.Xinit = Xinit;
		this.Yinit = Yinit;
		this.Vo = Vo;
		this.theta = theta;
		this.loss = loss;
		this.color = color;
		this.myTable = myTable;
		this.traceOn = traceOn;

		myBall = new GOval(myTable.toScrX(XINIT), myTable.toScrY((YMAX / 2)), (bSize * 2) * SCALE, (bSize * 2) * SCALE);
		myBall.setColor(this.color);
		myBall.setFilled(true);
		myTable.getDisplay().add(myBall);

	}
	
	public static void setTraceOn(boolean aBoolean) {
		traceOn = aBoolean;
	}
	
	public static boolean getTraceOn() {
		return traceOn;
	}
	
	public void trail(double xPos, double yPos, double diameter) {
		
		if(traceOn) {
			
			GOval ball = new GOval(xPos, yPos, diameter, diameter);
			ball.setColor(Color.black);
			ball.setFilled(true);
			myTable.getDisplay().add(ball);
		
		}
		
	}
	
	//Method that sets the value of TICKMULTIPLIER used to modify the TICK of the program
	public static void setTickMultiplier(int value) {
		TICKMULTIPLIER = value;
	}
	
	//Method that sets the value of TICKMULTIPLIER used to modify the TICK of the program
	public static int getTICKMULTIPLIER() {
			return TICKMULTIPLIER;
	}
	
	//Exported methods
	
	//Returns the time parameter of the simulation
	public static double getTime() {
		return time;
	}
	
	//Resets the score to 0 for the AI
	public void resetScoreAI() {
		scoreAI = 0;
		ppSimPaddleAgent.setAgentScore(getScoreAI());
	}
	
	//Resets the players score to 0
	public void resetScorePlayer() {
		scorePlayer = 0;
		ppSimPaddleAgent.setHumanScore(getScorePlayer());
	}
	
	//Returns the score of the AI
	public int getScoreAI() {
		return scoreAI;
	}
	
	//Returns the score of the player
	public int getScorePlayer() {
		return scorePlayer;
	}
	
	//Returns the Ypos of the Ball
	public static double getY() {
		return myBall.getY()/SCALE;
	}
	
	//Returns the Yvelocity of the Ball
	public static double getVy() {
		return Vy;
	}
	
	//Returns true if the ball is moving, false otherwise
	public boolean ballInPlay() {
		return hasEnergy;
	}
	
	//Returns the GObject of the ball
	public GObject getBall() {
		return myBall;
	}
	
	public void setPaddle(ppPaddle myPaddle) {
		this.myPaddle = myPaddle;
	}
	
	public void setAgent(ppPaddleAgent theAgent) {
		this.theAgent = theAgent;
	}
	
	public void run() {

		hasEnergy = true;
		boolean TEST = false;
		double PE = bMass * g * ((YMAX / 2) * SCALE);
		double Vt = bMass * g / (4 * Pi * k * Math.pow(bSize, 2));
		double KEx = ETHR, KEy = ETHR;
		double Xo, X, Vx;
		double Yo, Y;
		double Vox = Vo * Math.cos(theta * Pi / 180);
		double Voy = Vo * Math.sin(theta * Pi / 180);
		double ScrX;
		double ScrY;
		Xo = Xinit;
		Yo = Yinit;

		while (hasEnergy) {
			
			X = Vox * Vt / g * (1 - Math.exp(-g * time / Vt));
			Y = Vt / g * (Voy + Vt) * (1 - Math.exp(-g * time / Vt)) - Vt * time;
			Vx = Vox * Math.exp(-g * time / Vt);
			Vy = (Voy + Vt) * Math.exp(-g * time / Vt) - Vt;
			time += TICK/TICKMULTIPLIER;

			// When the ball hits the floor
			if (Vy < 0 && Yo + Y <= bSize) {
				KEx = 0.5 * bMass * Vx * Vx * (1 - loss);
				KEy = 0.5 * bMass * Vy * Vy * (1 - loss);
				PE = 0;
				
				Vox = Math.sqrt(2 * KEx / bMass);
				Voy = Math.sqrt(2 * KEy / bMass);

				if (Vx < 0) {
					Vox = -Vox;
				}

				time = 0;
				Xo += X;
				Yo = bSize;
				X = 0;
				Y = 0;

			}

			// When ball hits the paddle
			if (Vx > 0 && (Xo + X) > myPaddle.getX() - bSize - (ppPaddleW / 2)) {

				if (myPaddle.contact(Xo+X,Yo+Y)) {
					KEx = 0.5 * bMass * Vx * Vx * (1 - loss);
					KEy = 0.5 * bMass * Vy * Vy * (1 - loss);
					PE = bMass * g * Y;
					
					Vox = Math.sqrt(2 * KEx / bMass);
					Voy = Math.sqrt(2 * KEy / bMass);
					
					Vox = -Vox*ppPaddleXgain;
					Voy = Voy*ppPaddleYgain*myPaddle.getSgnVy();

					time = 0;
					Xo = myPaddle.getX() - bSize - ppPaddleW / 2;
					Yo += Y;
					X = 0;
					Y = 0;
				} 
				else {
					if (Vx > 0 && (Xo + X) > myPaddle.getX()) {
						hasEnergy = false;
						scoreAI++;
						ppSimPaddleAgent.setAgentScore(getScoreAI());
					}
				}

			}

			// When the ball hits the paddle agent
			if (Vx < 0 && (Xo + X) <= theAgent.getX() + bSize + (ppPaddleW / 2)) {

				if (theAgent.contact(Xo+X,Yo+Y)) {
					
					KEx = 0.5 * bMass * Vx * Vx * (1 - loss);
					KEy = 0.5 * bMass * Vy * Vy * (1 - loss);
					PE = bMass * g * Y;
					
					Vox = Math.sqrt(2 * KEx / bMass);
					Voy = Math.sqrt(2 * KEy / bMass);
					
					Vox = Vox*ppPaddleAgentXgain;
					
					if (Vy < 0) {
						Voy = -Voy*ppPaddleAgentYgain;
					}
					
					time = 0;
					Xo = theAgent.getX() + bSize + ppPaddleW / 2;
					Yo += Y;
					X = 0;
					Y = 0;
					
				} 
				else {
					if (Vx < 0 && (Xo + X) <= myPaddle.getX() + bSize + (ppPaddleW / 2)) {
						hasEnergy = false;
						scorePlayer++;
						ppSimPaddleAgent.setHumanScore(getScorePlayer());
					}
				}

			}

			if ((KEx + KEy + PE) < ETHR)
				hasEnergy = false;

			if (TEST) {
				System.out.printf("t: %.4f  XBall: %.4f  YBall: %.4f  Vx: %.4f  Vy:%.4f\n", time, (Xo + X), Y*SCALE, Vx, Vy);
			}

			ScrX = (int) (myTable.toScrX((Xo + X - bSize)));
			ScrY = (int) (scrHEIGHT - myTable.toScrY((Yo + Y + bSize)));
			myBall.setLocation(ScrX, ScrY);
			trail(ScrX + bSize * SCALE, ScrY + bSize * SCALE, PD);
			myTable.getDisplay().pause((TICK/TICKMULTIPLIER) *TIMESCALE);

		}

	}

}
