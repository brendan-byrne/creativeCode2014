#ifndef Modi_h
#define Modi_h

#include "Arduino.h"

class Modi {
  public:
    Modi(byte _selPinA, byte _selPinB, byte _selPinC, byte _numMux);
    byte inputPins[8];
    byte selPins[3];
    byte muxChannels;
    byte muxShift[8];
    byte numMux;
    
    int numReadings;    
    
    int readings[8][8][10];
    int total[8][8];
    int average[8][8];
    int lastAverage[8][8];
    
    int readIndex;
    
    void attach(byte _pin, byte _channel);
    void refresh();
    int getReading(byte _mux, byte _channel);
    int readMux(int _readPin, int _channel);
    
  private:
  
};

#endif
