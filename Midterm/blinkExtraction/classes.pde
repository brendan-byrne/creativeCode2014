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

	void update() {
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