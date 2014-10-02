import processing.video.*;
Capture video;
import gifAnimation.*;
GifMaker gifExport;

//------ VARIABLES --------------------------------------------------------------------------
boolean record = true;
float squareSize;
float rando;

//------ SETUP ------------------------------------------------------------------------------
void setup() {
  size(640, 480);
  video = new Capture(this, width, height);
  video.start(); 
  smooth();
  background(255);
  noStroke();
    gifExport = new GifMaker(this, "export.gif");
    gifExport.setRepeat(0);
}

//------ DRAW -------------------------------------------------------------------------------
void draw() {
            fill(0, 25);
  rect(0, 0, width, height);
  if (video.available()) {
    

    squareSize = 10;

    video.read();
  
      int brightestX = 0; 
      int brightestY = 0; 
      float brightestValue = 0;     




    int index = 0;
    
    for (int y = 0; y < video.height; y++) {
      for (int x = 0; x < video.width; x++) {
        int pixelValue = video.pixels[index];
        float pixelBrightness = brightness(pixelValue);
        if (pixelBrightness > brightestValue) {
          brightestValue = pixelBrightness;
          brightestY = y;
          brightestX = x;
        }
        index++;
      }
    } 

  for (int x=0; x<video.width; x+=squareSize) {
    for (int y=0; y<video.height; y+=squareSize) {


      int loc = x + y*width;

      float r = red(video.pixels[loc]);
      float g = green(video.pixels[loc]);
      float b = blue(video.pixels[loc]);
      
      float mousePos = map(brightestY, 0, height, 0, 100);
 

      fill(r, g, b);
      pushMatrix();
      translate(x, y);
      stroke(r, g, b);
      rotate(random(20));
      stroke(r, g, b);
      strokeWeight(random(mousePos));
      line(0 + random(map(brightestY, 0, height, 0, 20)), 0 + random(map(brightestY, 0, height, 0, 20)), map(brightestX, 0, width, 0, 50), map(brightestX, 0, height, 0, 50));
      popMatrix();
      }
    }
    if (record == true) frameAdd(1);
  }
}
//-------------------------------------------------------------------------------------------

