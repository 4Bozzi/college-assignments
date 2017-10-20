FOTB - Final Project
Andrew Bozzi
Paul Frost
Kyle Haston
Robert Kubo

Welcome to a magical world of hangovers and bro-finding in FOTB's Monster PARTY!

The application runs well with no obvious, reproducible faults.  There are some 
requirements running the project.  Primarily, the maps require an API key.  The 
key is unique to your installation of the SDK, and can be found following this 
guide.

http://code.google.com/android/add-ons/google-apis/mapkey.html#getdebugfingerprint

The key Google gives you must inserted in the res/layouts/maptabview.xml file 
the maps to work properly.

When testing on the emulator you will have to send GPS coordinates to the emulator.
It will use these like you just moved to that location. To do this, after you run
the emulator with the application, switch to DDMS mode and go to emulator control and enter 
a latitude and longitude and hit send. The ECE building should be roughly at 
Latitude:32.2268 and Longitude:-110.950.

Note: If there is no emulator control window go to 
Window->Show View->Other->Android->Emulator Control

The only hardware-caused is issue we have experienced occurs when the phone is 
in USB drive mode, and the program cannot access the card.  This is unfortunately
a 'hard' fault, and the program will hang until force quit.  

This program has been designed and tested in API level 3 in both an emulator and 
on real phone. It has been partially tested in API level 7 as well in an emulator.