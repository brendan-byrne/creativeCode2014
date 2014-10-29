class FadeSquare {
	int x, y, z, brightness, size;
	float rate;
	boolean fade;
	color[] colors = new color[500];

	FadeSquare(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
		z = 0;
		brightness = 0;

		float index = 0;
		for (int i=0; i<500; i++) {
			colors[i] = lerpColor(#000000, #E1056F, index);
			index += .002;
		}
	}

	void update() {
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