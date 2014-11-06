import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import themidibus.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class midiVis extends PApplet {



MidiBus myBus;
Ring[] rings;

int numRings = 800;
int currentRing = 0;

int superCol1, superCol2, superCol3, superCol4, superCol5; 


public void setup() {
	size(640, 480, P3D);
	smooth();
	rectMode(CENTER);

	MidiBus.list();
	myBus = new MidiBus(this, "Max", -1);

	rings = new Ring[numRings];
	for (int i = 0; i < numRings; i++) {
		rings[i] = new Ring();
	}
}

public void draw() {
	background(255);
	for (int i = 0; i < numRings; i++) {
		rings[i].grow();
		rings[i].display();
	}
}

public void mousePressed() {


}

public void noteOn(int channel, int pitch, int velocity) {
	float loc = pitch;

	float randVal = random(0, 50);
	randVal = randVal/100;
	superCol1 = lerpColor(0xff490A3D, 0xffffffff, randVal);
	superCol2 = lerpColor(0xffBD1550, 0xffffffff, randVal);
	superCol3 = lerpColor(0xffE97F02, 0xffffffff, randVal);
	superCol4 = lerpColor(0xffF8CA00, 0xffffffff, randVal);
	superCol5 = lerpColor(0xff8A9B0F, 0xffffffff, randVal);

	if (channel == 0) {
		loc = map(loc, 20, 110, height, 0);
		rings[currentRing].start(0, loc, superCol1);
		currentRing++;
	}
	if (channel == 1) {
		loc = map(loc, 20, 110, height, 0);
		rings[currentRing].start(0, loc, superCol2);
		currentRing++;
	}
	if (channel == 2) {
		loc = map(loc, 20, 110, height, 0);
		rings[currentRing].start(0, loc, superCol3);
		currentRing++;
	}
	if (channel == 3) {
		loc = map(loc, 20, 110, height, 0);
		rings[currentRing].start(0, loc, superCol4);
		currentRing++;
	}
	if (channel == 4) {
		loc = map(loc, 20, 110, height, 0);
		rings[currentRing].start(0, loc, superCol5);
		currentRing++;
	}

	if (currentRing >= numRings) {
		currentRing = 0;
	}
}
class Ring {
	float x, y; 
	float diameter;
	boolean on = false;
	int col;

	public void start(float xpos, float ypos, int _col) {
		x = xpos;
		y = ypos;
		on = true;
		diameter = 1;
		col = _col;
	}

	public void grow() {
		x+=4;
		if (on == true) {
			diameter += 0.5f;
			if (x-(.5f*diameter) > width) {
				on = false;
			}
		}
	}

	public void display() {
		if (on == true) {
			fill(col);
			noStroke();
			rect(x, y, diameter, diameter);
		}
	}
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "midiVis" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
