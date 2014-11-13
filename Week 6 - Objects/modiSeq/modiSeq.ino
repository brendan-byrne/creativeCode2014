#include <Modi.h>
#include <Smoothie.h>
#include <Shifter.h>

// Modi + Mux Variables + Objects
const int numMuxes = 2;
const int muxChannels = 8;
Modi Grid(19, 20, 21, numMuxes);
int lastReading[numMuxes][muxChannels];

// 595 Variables + Objects
const byte clock = 1;
const byte data = 2;
const byte latch = 3;
const byte numRegisters = 2;
Shifter shifter(data, latch, clock, numRegisters); 

// Permutation and Variables
Smoothie permPot = A7;
int permSel = 0;
byte posPins[] = {4, 5, 6, 7};
byte perms[24][4] = {
  {0, 1, 2, 3}, {0, 1, 3, 2}, {0, 2, 1, 3}, {0, 2, 3, 1}, {0, 3, 1, 2}, {0, 3, 2, 1}, 
  {1, 0, 2, 3}, {1, 0, 3, 2}, {1, 2, 0, 3}, {1, 2, 3, 0}, {1, 3, 0, 2}, {1, 3, 2, 0}, 
  {2, 0, 1, 3}, {2, 0, 3, 1}, {2, 1, 0, 3}, {2, 1, 3, 0}, {2, 3, 0, 1}, {2, 3, 1, 0}, 
  {3, 0, 1, 2}, {3, 0, 2, 1}, {3, 1, 0, 2}, {3, 1, 2, 0}, {3, 2, 0, 1}, {3, 2, 1, 0}
};

boolean lastClock = false;

void setup() {
  Grid.attach(A9, 0);
  Grid.attach(A8, 1);
  for (int i=0; i<4; i++) pinMode(posPins[i], INPUT);
}

void loop() {
  shifter.clear(); 
  int permRead = permPot.read(); 
  permRead = map(permRead, 0, 1023, 0, 24);
  
  boolean clockRead = digitalRead(posPins[0]);
  if (clockRead != lastClock) {
    lastClock = clockRead;
    if (clockRead == true) {      
      permSel = permRead; 
    } 
  }
  
  int regLoc = 0;
  if (digitalRead(posPins[perms[permSel][0]])) regLoc += 1;  
  if (digitalRead(posPins[perms[permSel][1]])) regLoc += 2;  
  if (digitalRead(posPins[perms[permSel][2]])) regLoc += 4;  
  if (digitalRead(posPins[perms[permSel][3]])) regLoc += 8;
  
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
  
  shifter.setPin(regLoc, HIGH);
  shifter.write();  
}


