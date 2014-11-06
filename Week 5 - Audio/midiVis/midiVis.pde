import themidibus.*;

MidiBus myBus;
Ring[] rings;

int numRings = 800;
int currentRing = 0;

color superCol1, superCol2, superCol3, superCol4, superCol5; 


void setup() {
	size(640, 480, P3D);
	smooth();
	rectMode(CENTER);

	MidiBus.list();
	myBus = new MidiBus(this, "Max", -1);

	rings = new Ring[numRings];
	for (int i = 0; i < numRings; i++) {
		rings[i] = new Ring();
	}
}

void draw() {
	background(255);
	for (int i = 0; i < numRings; i++) {
		rings[i].grow();
		rings[i].display();
	}
}

void mousePressed() {


}

void noteOn(int channel, int pitch, int velocity) {
	float loc = pitch;

	float randVal = random(0, 50);
	randVal = randVal/100;
	superCol1 = lerpColor(#490A3D, #ffffff, randVal);
	superCol2 = lerpColor(#BD1550, #ffffff, randVal);
	superCol3 = lerpColor(#E97F02, #ffffff, randVal);
	superCol4 = lerpColor(#F8CA00, #ffffff, randVal);
	superCol5 = lerpColor(#8A9B0F, #ffffff, randVal);

	if (channel == 0) {
		loc = map(loc, 20, 110, height, 0);
		rings[currentRing].start(0, loc, superCol1);
		currentRing++;
	}
	if (channel == 1) {
		loc = map(loc, 20, 110, height, 0);
		rings[currentRing].start(0, loc, superCol2);
		currentRing++;
	}
	if (channel == 2) {
		loc = map(loc, 20, 110, height, 0);
		rings[currentRing].start(0, loc, superCol3);
		currentRing++;
	}
	if (channel == 3) {
		loc = map(loc, 20, 110, height, 0);
		rings[currentRing].start(0, loc, superCol4);
		currentRing++;
	}
	if (channel == 4) {
		loc = map(loc, 20, 110, height, 0);
		rings[currentRing].start(0, loc, superCol5);
		currentRing++;
	}

	if (currentRing >= numRings) {
		currentRing = 0;
	}
}