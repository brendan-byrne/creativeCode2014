#include "Arduino.h"
#include "Modi.h"

Modi::Modi(byte _selPinA, byte _selPinB, byte _selPinC, byte _numMux) {
  readIndex = 0;
  numReadings = 10;
  muxChannels = 8;
  numMux = _numMux;

  selPins[0] = _selPinA;
  selPins[1] = _selPinB;
  selPins[2] = _selPinC;  
  
  int muxArray[] = {4, 6, 7, 5, 3, 0, 1, 2};
  for (int i=0; i<sizeof(muxShift); i++) muxShift[i] = muxArray[i];
  for (int i=0; i<sizeof(inputPins); i++) inputPins[i] = 0;
  
  for (int i=0; i<sizeof(selPins); i++) pinMode(selPins[i], OUTPUT);
}

void Modi::attach(byte _pin, byte _channel) {
  inputPins[_channel] = _pin;
  pinMode(inputPins[_channel], INPUT);
}

void Modi::refresh() {
  for (int x=0; x<numMux; x++) {
    for (int y=0; y<muxChannels; y++) {
      total[x][y] = total[x][y] - readings[x][y][readIndex];         
      readings[x][y][readIndex] = readMux(inputPins[x], muxShift[y]); 
      total[x][y] = total[x][y] + readings[x][y][readIndex];                                
      average[x][y] = total[x][y] / numReadings; 
    }  
  } 
  readIndex = readIndex + 1; 
  if (readIndex >= numReadings) readIndex = 0; 
}

int Modi::getReading(byte _mux, byte _channel) {
  return average[_mux][_channel];   
}

int Modi::readMux(int _readPin, int _channel) {
  for(int i=0; i<sizeof(selPins); i++) {
    digitalWrite(selPins[i], bitRead(_channel, i));
  } 
  return analogRead(_readPin); 
}
