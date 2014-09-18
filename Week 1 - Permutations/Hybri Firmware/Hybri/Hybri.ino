const int bitPin[] = {13, 14, 15}; 
const int numChannels = 8; 
const int midiLEDs[] = {8, 9, 10, 11, 12};
const int muxArray[] = {4, 6, 7, 5, 3, 0, 1, 2};

const int numReadings = 15;  
int readings[numChannels][numReadings];    
int tracker = 0;                             
int total[numChannels];                     
int average[numChannels];                   
int lastAverage[numChannels];


boolean lastState[numChannels];             
boolean currentState[numChannels];          
boolean formerLastState[numChannels];       
long lastDebounce[numChannels];             
int debounceDelay = 50;

//----------------------------------------------------------------------------------------------------------------

void setup() {
  usbMIDI.setHandleNoteOn(OnNoteOn);  
  usbMIDI.setHandleNoteOff(OnNoteOff);
  for (int x=0; x<3; x++) pinMode(bitPin[x], OUTPUT); 
  for (int x=0; x<5; x++) pinMode(midiLEDs[x], OUTPUT); 
}

//----------------------------------------------------------------------------------------------------------------

void loop() {
  usbMIDI.read();
  
    for (int x=0; x<numChannels; x++) {       
      total[x] = total[x] - readings[x][tracker];                   
      readings[x][tracker] = readMux(A1, muxArray[x], "analog");               
      total[x] += readings[x][tracker];                             
      average[x] = (total[x] / numReadings);                      
      if (average[x] > lastAverage[x] + 5 || average[x] < lastAverage[x] - 5) {
        int midiVal = map(average[x], 0, 1023, 0, 127);
        usbMIDI.sendControlChange(20+x, midiVal, 1);
        lastAverage[x] = average[x];       
      }
   
      currentState[x] = readMux(A3, muxArray[x], "digital");                 
      if (currentState[x] != lastState[x]) {                      
        lastDebounce[x] = millis();                               
        formerLastState[x] = lastState[x];                        
        lastState[x] = currentState[x];                           
       }
      if ((millis() - lastDebounce[x]) > debounceDelay) {         
        if (currentState[x] != formerLastState[x]) {    
          if (currentState[x] == HIGH) usbMIDI.sendNoteOn(40+x, 127, 1);
          if (currentState[x] == LOW) usbMIDI.sendNoteOff(40+x, 0, 1);                             
        }
        formerLastState[x] = currentState[x];                     
      }
    }
    
  tracker += 1;                                                  
  if (tracker >= numReadings) tracker = 0;  
}

//----------------------------------------------------------------------------------------------------------------

int readMux(int readPin, int channel, String readType) {
  for(int x=0; x<3; x++) digitalWrite(bitPin[x], bitRead(channel, x));
  if (readType == "analog")   
    return analogRead(readPin);
  else if (readType == "digital");
    return digitalRead(readPin);  
}

void OnNoteOn(byte channel, byte note, byte velocity) {
  note = note-48;
  digitalWrite(midiLEDs[note], HIGH);
}

void OnNoteOff(byte channel, byte note, byte velocity) {
  note = note-48;
  digitalWrite(midiLEDs[note], LOW);
}
