//------ LIBRARIES --------------------------------------------------------------------------
import gifAnimation.*;
import processing.video.*;

//------ OBJECTS ----------------------------------------------------------------------------
Movie eyeBlinks;
PixCheck[] eyes = new PixCheck[4];
PrintWriter[] outputs = new PrintWriter[4];

//------ SETUP ------------------------------------------------------------------------------
void setup() {
	background(255);
	size(240, 80, OPENGL);

	outputs[0] = createWriter("kent.txt");
	outputs[1] = createWriter("lyn.txt");
	outputs[2] = createWriter("nils.txt");
	outputs[3] = createWriter("sain.txt");

	eyeBlinks = new Movie(this, "goodBlink.mov");
	eyeBlinks.play();
	eyeBlinks.noLoop();
    eyeBlinks.loadPixels();

    eyes[0] = new PixCheck(28, 29, eyeBlinks, outputs[0]);
    eyes[1] = new PixCheck(75, 41, eyeBlinks, outputs[1]);
    eyes[2] = new PixCheck(169, 51, eyeBlinks, outputs[2]);
    eyes[3] = new PixCheck(213, 32, eyeBlinks, outputs[3]);
}

//------ DRAW -------------------------------------------------------------------------------
void draw() {
	image(eyeBlinks, 0, 0);
	eyeBlinks.loadPixels();

	for (int i=0; i<4; i++) {
		eyes[i].update();
	}

	if (eyes[0].counter > 20000) {
		for (int i=0; i<4; i++) {
			outputs[i].flush();  
  			outputs[i].close(); 
  			exit();  
  		}
	}
}

//-------------------------------------------------------------------------------------------
void movieEvent(Movie m) {
  m.read();
}