//------ LIBRARIES --------------------------------------------------------------------------
import promidi.*;		
import gifAnimation.*;

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
void setup() {
	img = loadImage("napoleon.PNG");
	size(img.width, img.height, OPENGL);
	smooth();
	midiIn(1, 0);
	gifExport = new GifMaker(this, "export.gif");
    gifExport.setRepeat(0);
    background(255);
    
}

//------ DRAW -------------------------------------------------------------------------------
void draw() {
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
