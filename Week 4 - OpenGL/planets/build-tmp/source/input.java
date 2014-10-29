import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class input extends PApplet {

Planet p, p2;

public void setup() {
	size(1024, 768, P3D);
	p = new Planet(100, 200, PI/100, 0);
	p2 = new Planet(100, 200, PI/90, PI);
}

public void draw() {
	background(0);
	lights();
	translate(width/2, height/2);
	p.draw();	
	p2.draw();
}

class Planet {
	int size, orbitSize;
	float rotation = 0, rotationRate;

	public Planet(int size, int orbitSize, float rotationRate, float rotation) {
		this.size = size;
		this.orbitSize = orbitSize;
		this.rotationRate = rotationRate;
		this.rotation = rotation;
	}

	public void draw() {	
		pushMatrix();
		rotateY(rotation);
		translate(orbitSize, 0);
		sphere(size);
		popMatrix();
		rotation = (rotation + rotationRate) % (2*PI);
	}
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "input" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
