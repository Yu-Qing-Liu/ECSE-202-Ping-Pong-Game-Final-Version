package ppPackage;

import java.awt.Color;

public class ppSimParams {

	// 1. Parameters defined in screen coordinates (pixels, acm coordinates)

	static final int scrWIDTH = 1080;
	static final int scrHEIGHT = 600;
	static final int OFFSET = 200;

	// 2. Parameters defined in simulation coordinates (MKS, x-->range, y-->height)

	static final double g = 9.8;
	static final double k = 0.1316;
	static final double Pi = 3.1416;
	static final double XMAX = 2.74;
	static final double YMAX = 1.52;
	static final double XLWALL = 0.1;
	static final double XRWALL = XMAX;                   
	static final double bSize = 0.02;
	static final double bMass = 0.0027;
	static final double XINIT = XLWALL + bSize;
	static final double TICK = 0.01;
	static final double ETHR = 0.001;
	static final double YINIT = YMAX / 2;
	static final double PD = 1;
	static final double SCALE = scrHEIGHT/YMAX;
	static final double ppPaddleH = 8*2.54/100;
	static final double ppPaddleW = 0.5*2.54/100;
	static final double ppPaddleXinit = XMAX-ppPaddleW/2;
	static final double ppPaddleYinit = YINIT;
	static final double ppPaddleXgain = 2.00; //1.50;
	static final double ppPaddleYgain = 2.00; //1.50;
	static final double ppPaddleAgentXinit = XINIT;
	static final double ppPaddleAgentYinit = YINIT;
	static final double ppPaddleAgentXgain = 2.00; //1.50;
	static final double ppPaddleAgentYgain = 2.00; //1.50;
	static final Color ColorPaddle = Color.green;
	static final Color ColorAgent = Color.blue;
	static final Color ColorBall = Color.red;
	
	static final double TIMESCALE = 5000;

	// 3. Parameters used by the ppSim (main) class
	
	static final int NUMBALLS = 1;
	static final double YinitMAX = 0.75 * YMAX;
	static final double YinitMIN = 0.25 * YMAX;
	static final double EMIN = 0.2;
	static final double EMAX = 0.2;
	static final double VoMIN = 5.0;
	static final double VoMAX = 5.0;
	static final double ThetaMIN = 0.0;
	static final double ThetaMAX = 20.0;

	// 3. Miscellaneous

	static final boolean DEBUG = false;
	static final long RSEED = 8976232;
	
	
	
	

}
