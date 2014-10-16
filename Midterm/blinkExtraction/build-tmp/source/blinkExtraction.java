import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import gifAnimation.*; 
import processing.video.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class blinkExtraction extends PApplet {

//------ LIBRARIES --------------------------------------------------------------------------



//------ OBJECTS ----------------------------------------------------------------------------
Movie eyeBlinks;
PixCheck[] eyes = new PixCheck[4];
PrintWriter[] outputs = new PrintWriter[4];

//------ SETUP ------------------------------------------------------------------------------
public void setup() {
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
public void draw() {
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
public void movieEvent(Movie m) {
  m.read();
}
class PixCheck {
	int x, y, loc, lastTime, counter;
	boolean currentState, lastState;
	float initColor;
	Movie gif;
	PrintWriter printer;

	PixCheck(int _x, int _y, Movie _gif, PrintWriter _printer) {
		currentState = false;
		lastState = false;
		lastTime = 0;
		counter = 0;
		x = _x;
		y = _y;
		gif = _gif;
		printer = _printer;
		loc = x + (y*gif.width);
		initColor = blue(gif.pixels[loc]);
	}

	public void update() {
		if (blue(gif.pixels[loc]) >= initColor+20 ||  blue(gif.pixels[loc]) <= initColor-20) {
			currentState = true;
		} else {
			currentState = false;
		}

		if (currentState != lastState) {
			if (currentState) {
				counter = counter/12;  // 12 refers to the interval of ellapsed milliseconds
				printer.print(counter + ", ");
				counter = 0;
				lastTime = millis();
			}
			lastState = currentState;
		}

		if (currentState == false) {
			counter = millis() - lastTime;
		}

	}
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "blinkExtraction" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
