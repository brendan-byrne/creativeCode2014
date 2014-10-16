## Blink Synth ##
![](https://33.media.tumblr.com/313badc91549ac68eb616300ba01cb09/tumblr_ndk8zeQds41tllbtqo1_1280.jpg)

This project captures the blinks from an animated GIF or movie and translates them to an ATMEGA for playback.

A Processing sketch scans each frame of the GIF/movie for a change in the color value of a selected pixel on a character’s eye. A tolerance for the change in color value was implemented in order to ensure that only actual blinks are recorded. This information is stored as the number of 12 millisecond cycles between each blink. Each blink triggers the start of a new blink interval to be recorded.

12 milliseconds is the minimum interval that allowed the longest blink cycle to stay under 255. This is crucial for transferring the blink information on to the Arduino as a byte, a compact data type. This allowed for the maximum number of blink cycles to be stored on the ATMEGA’s limited memory.

The blink information is hard-coded into an array in the Arduino IDE. The loop steps through the elements of the array. At each element a counter take the value of the element and subtracts one from it every 12 milliseconds. At 0 the LED turns on and the index of the array advances by one. Once the end of the array is reached, it loops back to zero.

Four channels of blinks were recorded over the course of 30 minutes. Each array totaled 860 elements. The total memory used was 3k of the 32k of the ATMEGA328. However, there were some problems and I ended up using a Teensy++.

The case was designed and assembled, but not wired.

Future Goals:
Implement potentiometer for changing playback cycle time.
Implement potentiometer for changing duration of LED’s on time.