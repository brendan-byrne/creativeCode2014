//------ LIBRARIES --------------------------------------------------------------------------
import peasy.*;
import gifAnimation.*;

//------ OBJECTS ----------------------------------------------------------------------------
PeasyCam cam;
GifMaker gifExport;

//------ VARIABLES --------------------------------------------------------------------------
int gridSize = 20;
int grid = gridSize*gridSize;
int scaleSize = 10;
float[][] randVals = new float[gridSize][gridSize];
color[] colGrad   = new color[grid];
boolean record = false;

//------ SETUP ------------------------------------------------------------------------------
void setup() {
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
    colGrad[i] = lerpColor(#5CC4BB, #F4696C, index);
    index += 1/float(grid);
  }
}

//------ DRAW -------------------------------------------------------------------------------
void draw() {
  background(0);
  lights();
  for (int j=0; j<gridSize; j++) {
    for (int h=0; h<gridSize; h++) {
      int loc = j + (h*gridSize);
      
      pushMatrix();
        fill(colGrad[loc]);
        rotateX(PI/(randVals[j][h]));
        translate(j*scaleSize, h*scaleSize, -(.5*randVals[j][h]));
        box(scaleSize, scaleSize, randVals[j][h]);  
      popMatrix();
    }
  }  

	if (record == true) frameAdd(1);
}
//------ FUNCTIONS --------------------------------------------------------------------------
void frameAdd(int frameSkip) {
  if (frameCount % frameSkip == 0) {
    gifExport.setDelay(1);
    gifExport.addFrame();
  }
}