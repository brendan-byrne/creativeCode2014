import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import peasy.*; 
import gifAnimation.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class spiral extends PApplet {

//------ LIBRARIES --------------------------------------------------------------------------



//------ OBJECTS ----------------------------------------------------------------------------
PeasyCam cam;
GifMaker gifExport;

//------ VARIABLES --------------------------------------------------------------------------
int gridSize = 20;
int grid = gridSize*gridSize;
int scaleSize = 10;
float[][] randVals = new float[gridSize][gridSize];
int[] colGrad   = new int[grid];
boolean record = false;

//------ SETUP ------------------------------------------------------------------------------
public void setup() {
	size(400, 400, P3D);
  noStroke();

	cam = new PeasyCam(this, 0);
  cam.setMinimumDistance(50);
  cam.setMaximumDistance(500);
	
	gifExport = new GifMaker(this, "export.gif");
  gifExport.setRepeat(0);

  for (int j=0; j<gridSize; j++) {
    for (int h=0; h<gridSize; h++) {
      randVals[j][h] = random(100);
    }
  }

  float index = 0;
  for (int i=0; i<grid; i++) {
    colGrad[i] = lerpColor(0xff5CC4BB, 0xffF4696C, index);
    index += 1/PApplet.parseFloat(grid);
  }
}

//------ DRAW -------------------------------------------------------------------------------
public void draw() {
  background(0);
  lights();
  for (int j=0; j<gridSize; j++) {
    for (int h=0; h<gridSize; h++) {
      int loc = j + (h*gridSize);
      
      pushMatrix();
        fill(colGrad[loc]);
        rotateX(PI/(randVals[j][h]));
        translate(j*scaleSize, h*scaleSize, -(.5f*randVals[j][h]));
        box(scaleSize, scaleSize, randVals[j][h]);  
      popMatrix();
    }
  }  

	if (record == true) frameAdd(1);
}
//------ FUNCTIONS --------------------------------------------------------------------------
public void frameAdd(int frameSkip) {
  if (frameCount % frameSkip == 0) {
    gifExport.setDelay(1);
    gifExport.addFrame();
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
    String[] appletArgs = new String[] { "spiral" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
