//-------------------------------------------------------------------------------------------
void keyPressed() {
  if (key == 'b' || key == 'B') {
     gifExport.finish();
      println("gif saved");
  }
  if (key == 'j' || key == 'J') {
    save("capture.jpg");  
    println("jpg saved");
  }

}
//-------------------------------------------------------------------------------------------
void keyReleased() {

}
//-------------------------------------------------------------------------------------------
void mouseMoved() {

}
//-------------------------------------------------------------------------------------------
void mouseDragged() {

}
//-------------------------------------------------------------------------------------------
void mousePressed() {

}
//-------------------------------------------------------------------------------------------
void mouseReleased() {

}
//-------------------------------------------------------------------------------------------

// Gif-animation Library
// Documetation - http://extrapixel.github.io/gif-animation/
//-------------------------------------------------------------------------------------------
void frameAdd(int frameSkip) {
  if (frameCount % frameSkip == 0) {
    gifExport.setDelay(1);
    gifExport.addFrame();
  }
}

//-------------------------------------------------------------------------------------------
