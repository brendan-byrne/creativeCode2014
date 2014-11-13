#include <Modi.h>

const byte numMuxes = 2;
const byte muxChannels = 8;
Modi Grid(19, 20, 21, numMuxes);

int lastReading[numMuxes][muxChannels];

void setup() {
  Grid.attach(A9, 0);
  Grid.attach(A8, 1);
}

void loop() {
  Grid.refresh();
  
  for (int x=0; x<numMuxes; x++) {
    for (int y=0; y<muxChannels; y++) {
      int reading = Grid.getReading(x, y);
      if (reading > lastReading[x][y]+2 || reading < lastReading[x][y]-2) {
        lastReading[x][y] = reading;
        Serial.print(x); 
        Serial.print(y);
        Serial.print("\t");
        Serial.print(map(reading, 0, 1023, 0, 255));
        Serial.println();
      }
    }
  }
}



