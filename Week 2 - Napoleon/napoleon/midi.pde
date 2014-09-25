// proMIDI Library
// Documentation - http://creativecomputing.cc/p5libs/promidi/
//-------------------------------------------------------------------------------------------
void midiIn(int device, int channel) {
  midiIO = MidiIO.getInstance(this);
  midiIO.printDevices();
  midiIO.plug(this, "noteOn", device, channel);
  midiIO.plug(this, "noteOff", device, channel);
  midiIO.plug(this, "controllerIn", device, channel);
 }

//-------------------------------------------------------------------------------------------
 void noteOn(Note note){
  int vel = note.getVelocity();
  int pit = note.getPitch();

}

//-------------------------------------------------------------------------------------------
void noteOff(Note note){
  int pit = note.getPitch();

}

//-------------------------------------------------------------------------------------------
void controllerIn(Controller controller){
  int num = controller.getNumber();
  int val = controller.getValue();

  if (num == 20) squareSize = constrain(val, 0, 127);
  if (num == 21) noiseR = int(map(val, 0, 127, 0, 512));
  if (num == 22) noiseG = int(map(val, 0, 127, 0, 1500));
  if (num == 23) noiseB = int(map(val, 0, 127, 0, 512));



  if (num == 27) selector = val;
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