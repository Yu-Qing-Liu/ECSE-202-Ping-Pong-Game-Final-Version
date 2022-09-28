package ppPackage;

import static ppPackage.ppSimParams.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import acm.gui.IntField;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class ppSimPaddleAgent extends GraphicsProgram {
	
	/*Most of the following code in this and the following classes in this package has been copied 
	 *from the template in the instructions pdf by professor Frank P. Ferrie*/
	
	//Instance variables
	RandomGenerator rgen = RandomGenerator.getInstance();
	
	ppBall myBall;
	ppPaddle myPaddle;
	ppTable myTable;
	ppPaddleAgent theAgent;
	
	JTextField Agent = new JTextField("Agent");
	static IntField AgentScore = new IntField(0); 
	JTextField Human = new JTextField("Please Enter Your Name");
	static IntField HumanScore = new IntField(0);
	JSlider difficulty = new JSlider(1,5,1);
	JSlider lag = new JSlider(1,5,1);
	
	public static void main(String[] args) {
			new ppSimPaddleAgent().start(args);
	}
	
	
	// Methods that sets the value of the IntFields
	public static void setAgentScore(int anInt) {
		AgentScore.setValue(anInt);
	}
	
	public static void setHumanScore(int anInt) {
		HumanScore.setValue(anInt);
	}
	
	//Method that creates a ppBall instance
	ppBall newBall() {
		
		rgen.setSeed(RSEED);
		double iYinit = rgen.nextDouble(YinitMIN, YinitMAX);
		double iLoss = rgen.nextDouble(EMIN, EMAX);
		double iVel = rgen.nextDouble(VoMIN, VoMAX);
		double iTheta = rgen.nextDouble(ThetaMIN, ThetaMAX);
		
		ppBall newBall = new ppBall(XINIT, iYinit, iVel, iTheta, ColorBall, iLoss, myTable, ppBall.getTraceOn());
		return newBall;
	}
	
	//Program's entry point
	public void init() {

		this.resize(scrWIDTH + OFFSET, scrHEIGHT + OFFSET);

		//JButtons for the 4 menu items
		JButton clearButton = new JButton("Clear");
		JButton newServeButton = new JButton("New Serve");
		JButton traceButton = new JButton("Trace");
		JButton quitButton = new JButton("Quit");
		add(clearButton,SOUTH);
		add(newServeButton,SOUTH);
		add(traceButton,SOUTH);
		add(quitButton,SOUTH);
		
		//Other features
		add(Agent,NORTH);
		add(AgentScore,NORTH);
		add(Human,NORTH);
		add(HumanScore,NORTH);
		add(new JLabel("Easy"),SOUTH);
		add(difficulty,SOUTH);
		add(new JLabel("Hard"),SOUTH);
		add(new JLabel("Laggy"),SOUTH);
		add(lag,SOUTH);
		add(new JLabel("Smooth"),SOUTH);
		
		//Adding event listeners for the mouse, buttons and sliders
		addActionListeners();
		addMouseListeners();
		
			myTable = new ppTable(this);
			myBall = newBall();
			
			myPaddle = new ppPaddle(ppPaddleXinit,ppPaddleYinit,ColorPaddle,myTable);
			theAgent = new ppPaddleAgent(ppPaddleAgentXinit,ppPaddleAgentYinit,ColorAgent,myTable);
			
			myBall.setPaddle(myPaddle);
			myBall.setAgent(theAgent);
			theAgent.attachBall(myBall);

			add(myBall.getBall()); 
			theAgent.run(); 
			myPaddle.run();
			myBall.start();	
			
	}
	
	//Returns the Ypos of the mouse to myPaddle
	public void mouseMoved(MouseEvent e) {
		myPaddle.setY(myTable.ScrtoY((double)e.getY()));
		myPaddle.setX(ppPaddleXinit);
	}
	
	//Checks if buttons/sliders are interacted with, and perform the following operations.
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		if (command.equals("Clear")) {
			myTable.newScreen();
			myBall.resetScoreAI();
			myBall.resetScorePlayer();
		}
		
		if (command.equals("New Serve")) {
			
			if (!(myBall.ballInPlay())) {
				
				myTable.newScreen();

				myBall = newBall();
				
				myPaddle = new ppPaddle(ppPaddleXinit,ppPaddleYinit,ColorPaddle,myTable);
				theAgent = new ppPaddleAgent(ppPaddleAgentXinit,ppPaddleAgentYinit,ColorAgent,myTable);
				
				myBall.setPaddle(myPaddle);
				myBall.setAgent(theAgent);
				theAgent.attachBall(myBall);

				add(myBall.getBall());
				theAgent.run(); 
				myPaddle.run();
				myBall.start();
				
			}
		}
		
		if (command.equals("Trace")) {
			
			if(ppBall.getTraceOn() == true) {
				ppBall.setTraceOn(false);
			}
			if(ppBall.getTraceOn() == false) {
				ppBall.setTraceOn(true);
			}
			
		}
		
		if (command.equals("Quit")) {
			System.exit(0);
		}
		
		if (e.getSource() == Human) {
			Human.setText(Human.getText());
		}
		
		if (difficulty.getValue() != 0) {
			ppPaddleAgent.setDifficulty(difficulty.getValue());
		}
		
		if (lag.getValue() != 0) {
			ppBall.setTickMultiplier((lag.getValue()));
		}
		
	}
	
	
}
