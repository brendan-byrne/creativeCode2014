import peasy.*;

Planet[] planets = new Planet[20]; 
PeasyCam cam;

void setup() {
	size(1024, 768, P3D);
	noFill();
	stroke(255);
	float index = PI/10;
	for (int i=0; i<20; i++) {
		planets[i] = new Planet(20*i, i*100, 200, PI/100, index);
		index += PI/10;
	}

		cam = new PeasyCam(this, 0);
  cam.setMinimumDistance(50);
  cam.setMaximumDistance(5000);
}

void draw() {
	background(0);
	lights();
	translate(width/2, 0);
	for (int i=0; i<20; i++) {
		planets[i].draw();
	}
}

class Planet {
	int size, orbitSize, axis;
	float rotation = 0, rotationRate;

	public Planet(int size, int axis, int orbitSize, float rotationRate, float rotation) {
		this.size = size;
		this.orbitSize = orbitSize;
		this.rotationRate = rotationRate;
		this.rotation = rotation;
		this.axis = axis;
	}

	public void draw() {
		pushMatrix();
		rotateY(rotation);
		translate(orbitSize, axis);
		box(size);
		popMatrix();
		rotation = (rotation + rotationRate) % (2*PI);
	}
}