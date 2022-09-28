package ppPackage;

import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.*;

public class ppPaddle {

	//Instance variables

	private double X;
	private double Y;
	private double Vx;
	private double Vy;
	private double lastX;
	private double lastY;
	private ppTable myTable;
	private Color myColor;
	GRect myPaddle;
	
	//Constructor for a ppPaddle object
	public ppPaddle(double X, double Y, Color myColor, ppTable myTable) {
		
		this.X = X;
		this.Y = Y;
		this.myColor = myColor;
		this.myTable = myTable;

		myPaddle = new GRect(X, Y, ppPaddleW * SCALE, ppPaddleH * SCALE);
		myPaddle.setColor(myColor);
		myPaddle.setFilled(true);
		myTable.getDisplay().add(myPaddle);
		
	}

	// Exported methods
	
	public double getVx() {
		return Vx;
	}

	public double getVy() {
		return Vy;
	}

	public void setX(double x) {
		X = x;
	}

	public void setY(double y) {
		Y = y;
		myPaddle.setLocation(myTable.toScrX(X), myTable.toScrY(scrHEIGHT/SCALE-Y));
	}
	
	public double getX() {
		return X;
	}

	public double getY() {
		return Y;
	}
	
	public GObject getPaddle() {
		return myPaddle;
	}
	
	//Returns sign of the velocity of the paddle
	public double getSgnVy() {

		if (getVy() < 0) {
			return -1;
		} else {
			return 1;
		}

	}
	
	//Checks if the ball touches the paddle
	public boolean contact(double Sx, double Sy) { 
		
		boolean YMAX = Sy - bSize <= getY();
		boolean YMIN = Sy + bSize >= getY() - ppPaddleH;
		boolean XMIN = Sx + 2*bSize >= getX();

		return (XMIN && YMAX && YMIN ); 
		

	}
	
	public void run() {
		
		while (true) {

			Vx = (X - lastX) / ppBall.getTime();
			Vy = (Y - lastY) / ppBall.getTime();
			lastX = X;
			lastY = Y;
			
			setX(X);
			setY(Y);
			
			//System.out.printf("t: %.4f X: %.4f Y: %.4f Vx: %.4f Vy:%.4f\n", ppBall.getTime() ,getX(), getY() * SCALE, Vx, Vy);

			myTable.getDisplay().pause(TICK/ppBall.getTICKMULTIPLIER() * TIMESCALE);

		}

	}

}
