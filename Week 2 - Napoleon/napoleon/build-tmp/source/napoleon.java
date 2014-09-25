import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import promidi.*; 
import gifAnimation.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class napoleon extends PApplet {

//------ LIBRARIES --------------------------------------------------------------------------
		


//------ OBJECTS ----------------------------------------------------------------------------
MidiIO midiIO;
GifMaker gifExport;
PImage img;

//------ VARIABLES --------------------------------------------------------------------------
boolean record = true;
float squareSize;
int noiseR, noiseB, noiseG;
float rando;
int selector = 2;

//------ SETUP ------------------------------------------------------------------------------
public void setup() {
	img = loadImage("napoleon.PNG");
	size(img.width, img.height, OPENGL);
	smooth();
	midiIn(1, 0);
	gifExport = new GifMaker(this, "export.gif");
    gifExport.setRepeat(0);
    background(255);
    
}

//------ DRAW -------------------------------------------------------------------------------
public void draw() {
	squareSize = 10;
	img.loadPixels();
	for (int x=0; x<img.width; x+=squareSize) {
		for (int y=0; y<img.height; y+=squareSize) {
			rando = random(50);
			int loc = x + y*width;

			float r = red(img.pixels[loc]);
			float g = green(img.pixels[loc]);
			float b = blue(img.pixels[loc]);

			switch (selector) {
				case 1:
					r = noise(r)*noiseR;
					g = noise(g)*noiseG;
					b = noise(b)*noiseB;
					break;
				case 2:
					break;
			}

			fill(r, g, b);
					pushMatrix();
					translate(x,y);
						stroke(r, g, b);
						rotate(rando);
					strokeWeight(1);
					ellipse(0+rando, 0+rando, squareSize+rando, 0);
					popMatrix();
		}
	}
	if (record == true) frameAdd(1);
}
//-------------------------------------------------------------------------------------------


//-------------------------------------------------------------------------------------------
public void keyPressed() {
	if (key == 'g' || key == 'G') {
		 gifExport.finish();
 		 println("gif saved");
	}
	if (key == 'j' || key == 'J') {
		save("capture.jpg");	
	}

}
//-------------------------------------------------------------------------------------------
public void keyReleased() {

}
//-------------------------------------------------------------------------------------------
public void mouseMoved() {

}
//-------------------------------------------------------------------------------------------
public void mouseDragged() {

}
//-------------------------------------------------------------------------------------------
public void mousePressed() {

}
//-------------------------------------------------------------------------------------------
public void mouseReleased() {

}
//-------------------------------------------------------------------------------------------
// proMIDI Library
// Documentation - http://creativecomputing.cc/p5libs/promidi/
//-------------------------------------------------------------------------------------------
public void midiIn(int device, int channel) {
  midiIO = MidiIO.getInstance(this);
  midiIO.printDevices();
  midiIO.plug(this, "noteOn", device, channel);
  midiIO.plug(this, "noteOff", device, channel);
  midiIO.plug(this, "controllerIn", device, channel);
 }

//-------------------------------------------------------------------------------------------
 public void noteOn(Note note){
  int vel = note.getVelocity();
  int pit = note.getPitch();

}

//-------------------------------------------------------------------------------------------
public void noteOff(Note note){
  int pit = note.getPitch();

}

//-------------------------------------------------------------------------------------------
public void controllerIn(Controller controller){
  int num = controller.getNumber();
  int val = controller.getValue();

  if (num == 20) squareSize = constrain(val, 0, 127);
  if (num == 21) noiseR = PApplet.parseInt(map(val, 0, 127, 0, 512));
  if (num == 22) noiseG = PApplet.parseInt(map(val, 0, 127, 0, 1500));
  if (num == 23) noiseB = PApplet.parseInt(map(val, 0, 127, 0, 512));



  if (num == 27) selector = val;
}

//-------------------------------------------------------------------------------------------

// Gif-animation Library
// Documetation - http://extrapixel.github.io/gif-animation/
//-------------------------------------------------------------------------------------------
public void frameAdd(int frameSkip) {
  if (frameCount % frameSkip == 0) {
    gifExport.setDelay(1);
    gifExport.addFrame();
  }
}

//-------------------------------------------------------------------------------------------
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "napoleon" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
