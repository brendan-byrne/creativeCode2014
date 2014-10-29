//------ LIBRARIES --------------------------------------------------------------------------		
import gifAnimation.*;
import peasy.*;

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
void setup() {
	size(1000, 800, P3D);
	smooth();
	noStroke();

	for (int h=0; h<gridDim; h++) {
		for (int j=0; j<gridDim; j++) {
			squares[h][j] = new FadeSquare(h*20, j*20, 20);
			squares[h][j].rate = random(0.0, 4.0);
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
void draw() {
	background(0);
	lights();
	for (int h=0; h<gridDim; h++) {
		for (int j=0; j<gridDim; j++) {
			squares[h][j].update();
		}
	}

	index1 = int(random(20));
	index2 = int(random(20));
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
void frameAdd(int frameSkip) {
  if (frameCount % frameSkip == 0) {
    gifExport.setDelay(1);
    gifExport.addFrame();
  }
}
