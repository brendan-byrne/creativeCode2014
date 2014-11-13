class Ring {
	float x, y; 
	float diameter;
	boolean on = false;
	color col;

	void start(float xpos, float ypos, color _col) {
		x = xpos;
		y = ypos;
		on = true;
		diameter = 20;
		col = _col;
	}

	void grow() {
		x+=2;
		if (on == true) {
			diameter += 1;
			if (x-(.5*diameter) > width) {
				on = false;
			}
		}
	}

	void display() {
		if (on == true) {
			fill(col);
			noStroke();
			rect(x, y, diameter, diameter);
		}
	}
}
