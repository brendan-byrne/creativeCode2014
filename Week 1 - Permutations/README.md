## Permutation MIDI Sequencer ##
![](http://i.imgur.com/7D4N7dy.png)

This is a simple Processing sketch that...

-  Displays all possible permutations of five gray scale colors.
- Selects one set randomly or by sequential order and plays through its colors.
- Interfaces with custom hardware to control...
	-  The length of each color
	-  The overall speed
	-  Resetting the sequence of sets
	-  Toggling between random and sequential mode
-  The interface also utilizes LED indication to display which step is currently active in a set.

**Assignment:** <br>
The permutation display is drawn in the setup portion of the code. <br>
The permutation function contains a vector in the form of an ArrayList.

**Fun:** <br>
The program and hardware underwent some heavy MIDI routing in Max to pass the signals between Reaktor (sound playback), Processing, and the interface. I think this a PC related issue. OSX seems to allow for MIDI Thru functionality without the user even worrying about it.