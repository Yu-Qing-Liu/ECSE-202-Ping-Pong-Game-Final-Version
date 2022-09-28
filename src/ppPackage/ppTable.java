package ppPackage;

import java.awt.Color;
import acm.graphics.*;

import static ppPackage.ppSimParams.*;


public class ppTable {
	
	private ppSimPaddleAgent dispRef;
	
	public ppTable(ppSimPaddleAgent dispRef) {
		
		this.dispRef = dispRef;
		
		//Sets the ground in the program
		GRect groundPlane = new GRect(0, 600, 1280, 3);
		groundPlane.setColor(Color.black);
		groundPlane.setFilled(true);
		dispRef.add(groundPlane);
				
	}
	
	//Exported methods
	
	double ScrtoX(double X) {
		return X/SCALE;
	}
	
	double ScrtoY(double Y) {
		return Math.abs((Y - (scrHEIGHT))/SCALE);					
	}
	
	double toScrX(double X) {
		return X*SCALE;
	}
	
	double toScrY(double Y) {
		return Y*SCALE;
	}
	
	//Method that removes all GObjects from the screen in order to recreate a fresh instance of the pong field.
	void newScreen() {
		
		dispRef.removeAll();
		GRect groundPlane = new GRect(0, 600, 1280, 3);
		groundPlane.setColor(Color.black);
		groundPlane.setFilled(true);
		dispRef.add(groundPlane);
		
	}
	
	ppSimPaddleAgent getDisplay() {
		return dispRef;
	}

}
