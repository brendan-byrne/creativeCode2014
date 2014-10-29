import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import gifAnimation.*; 
import peasy.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class empty2 extends PApplet {

//------ LIBRARIES --------------------------------------------------------------------------		



//------ VARIABLES --------------------------------------------------------------------------
int gridDim = 20;
int gridSize = gridDim*gridDim;
boolean record = false;
int index1 = 19;
int index2 = 19;

//------ OBJECTS ----------------------------------------------------------------------------
GifMaker gifExport;
PeasyCam cam;
FadeSquare[][] squares = new FadeSquare[gridDim][gridDim];

//------ SETUP ------------------------------------------------------------------------------
public void setup() {
	size(400, 400, P3D);
	stroke(255);
	

	for (int h=0; h<gridDim; h++) {
		for (int j=0; j<gridDim; j++) {
			squares[h][j] = new FadeSquare(h*20, j*20, 20);
			squares[h][j].rate = random(0.0f, 4.0f);
			println(squares[h][j].rate);
		}
	}

	gifExport = new GifMaker(this, "export.gif");
    gifExport.setRepeat(0);

	cam = new PeasyCam(this, 0);
  	cam.setMinimumDistance(50);
  	cam.setMaximumDistance(5000);
}

//------ DRAW -------------------------------------------------------------------------------
public void draw() {
	background(0);
	lights();
	for (int h=0; h<gridDim; h++) {
		for (int j=0; j<gridDim; j++) {
			squares[h][j].update();
		}
	}

	index1 = PApplet.parseInt(random(20));
	index2 = PApplet.parseInt(random(20));
	squares[index1][index2].fade = true;

	// index1--;
	// if (index1 == -1) {
	// 	index2--;
	// 	index1 = 19;
	// 	if (index2 == -1) {
	// 		index2 = 19;
	// 	}
	// }

	if (record == true) frameAdd(1);
}

//------ FUNCTIONS --------------------------------------------------------------------------
public void frameAdd(int frameSkip) {
  if (frameCount % frameSkip == 0) {
    gifExport.setDelay(1);
    gifExport.addFrame();
  }
}
class FadeSquare {
	int x, y, z, brightness, size;
	float rate;
	boolean fade;
	int[] colors = new int[500];

	FadeSquare(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
		z = 0;
		brightness = 0;

		float index = 0;
		for (int i=0; i<500; i++) {
			colors[i] = lerpColor(0xff000000, 0xffE1056F, index);
			index += .002f;
		}
	}

	public void update() {
		fill(colors[z]);
		pushMatrix();
		translate(x, y, z);
		box(size, size, 10);
		popMatrix();

		// if ((mouseX >= x && mouseX <= x+20) && (mouseY >= y && mouseY <= y+20)) {
		// 	fade = true;
		// }

		if (fade == true) {
			z += rate;
			if (z >= 500) {
				z = 0;
				fade = false;
			}
		}
	}
}
//-------------------------------------------------------------------------------------------
public void keyPressed() {
	if (key == 'b' || key == 'B') {
		 gifExport.finish();
 		 println("gif saved");
	}
	if (key == 'j' || key == 'J') {
		save("capture.jpg");	
		println("jpg saved");
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
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "empty2" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
