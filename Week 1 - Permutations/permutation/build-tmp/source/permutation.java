import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import promidi.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class permutation extends PApplet {


MidiIO midiIO;
MidiOut midiOut;
Note note;

int switchy = 1;

int changeTime[] = {500, 500, 500, 500, 500};
float changeVar = 1;
int lastTime = 0;
int setSize = 5;		// larger than 0 and smaller than 9
int totalPerms;
int yIndex = 0;
int xIndex = 0;
int col = 1;
int timeChange = 500;

IntList used;

public void setup() {
	midiIO = MidiIO.getInstance(this);
   	midiIO.printDevices();
	midiIO.plug(this,"noteOn",0,0);
	midiIO.plug(this,"noteOff",0,0);
	midiIO.plug(this,"controllerIn",0,0);
  	midiOut = midiIO.getMidiOut(0,0);		
	
	totalPerms = factorial(setSize);
	background(125);
	size(totalPerms*10, 110);
	for (int x=0; x<totalPerms; x++) {
		String s = getPermutation(setSize, x);
		for (int y=0; y<setSize; y++) {
			int col = stringToInt(s, y);
			fill(map(col, 1, 5, 0, 255));
			rect(x*10, y*10+60, 10, 10);
		}
	}	

}

public void draw() {
	stroke(0);

	if (switchy == 1) {
	if (changeTime[col-1]+changeVar < millis() - lastTime) {			
			fill(255);
			rect(xIndex*10, 50, 10, 10);
			String s = getPermutation(setSize, xIndex);
			col = stringToInt(s, yIndex);
			playNote(47+col, 127, PApplet.parseInt(changeTime[col-1]+changeVar*.75f));
			fill(map(col, 1, 5, 0, 255));
			rect(0, 0, width, 50);
			lastTime = millis();
			yIndex++;

			if (yIndex >= setSize) {
				yIndex = 0;
				xIndex++;
			}
			if (xIndex >= totalPerms) {

				fill(125);
    			noStroke();
   				rect(0, 0, totalPerms*10, 60);
    			yIndex = 0;
    			xIndex = 0;
			} 
		}  	
	} else {
		if (changeTime[col-1]+changeVar < millis() - lastTime) {			
				fill(255);
				rect(xIndex*10, 50, 10, 10);
				String s = getPermutation(setSize, xIndex);
				col = stringToInt(s, yIndex);
				playNote(47+col, 127, PApplet.parseInt(changeTime[col-1]+changeVar*.75f));
				fill(map(col, 1, 5, 0, 255));
				rect(0, 0, width, 50);
				lastTime = millis();
				yIndex++;

				if (yIndex >= setSize) {
					yIndex = 0;
					xIndex = PApplet.parseInt(random(totalPerms));
				}
				if (xIndex >= totalPerms) {

					fill(125);
	    			noStroke();
	   				rect(0, 0, totalPerms*10, 60);
	    			yIndex = 0;
	    			xIndex = 0;
				} 
			} 
		}
	}


public String getPermutation(int n, int k)  {
	ArrayList<Integer> numberList = new ArrayList<Integer>();
	for (int i = 1; i <= n; i++) numberList.add(i);
 
	int mod = factorial(n);
 
	String result = "";
 
	for (int i = 0; i < n; i++) {
		mod = mod / (n - i);
		int curIndex = k / mod;
		k = k % mod;
		result += numberList.get(curIndex);
		numberList.remove(curIndex);
	} 
	return result.toString();
}

public int stringToInt(String input, int index) {
	char c = input.charAt(index);
	return PApplet.parseInt(c)-48;
}

public int factorial(int input) {
	int totalFact = 1;
	for (int i = 1; i <=input; i++) totalFact = totalFact * i;
	return totalFact;
}


public void noteOn(Note note) {
  int vel = note.getVelocity();
  int pit = note.getPitch();

  if (pit == 40) switchy = 1;
  if (pit == 41) switchy = 0;

  if (pit == 47 && vel == 127) {
    fill(125);
    noStroke();
    rect(0, 0, totalPerms*10, 60);
    yIndex = 0;
    xIndex = 0;
  } 
}

public void noteOff(Note note){
}

public void controllerIn(Controller controller) {
  int num = controller.getNumber();
  int val = controller.getValue();

  if (num == 27) {
  	changeVar = map(val, 0, 127, 0, 200);
  }

  for (int x=0; x<5; x++) {
  	if (num == 20+x) {
  		changeTime[x] = PApplet.parseInt(map(val, 0, 127, 20, 300));
  	}
  }
}

public void playNote(int pitch, int velocity, int time) {
	note = new Note(pitch, velocity, time);
	midiOut.sendNote(note);
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "permutation" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
