# JoystickTest
Control an Arduino-based robot with an Android app

# How does this work?
It connects to an Arduino via UDP and send packets that control a robot.

# Why did you write this code?
This was written for FRC Team 340. We're building a super cool robot that I'm not sure if I can talk about yet. More info to come. 

# What is in this repository?
In the "Android Code" directory there is an Android studio project that contains the drive app. Run this on almost any android device. In the "Other Code" directory there is a "udpserver.js" that can be used for testing (point the drive app at that). It is run with node.js. There is also a "led.ino" which is actually the arduino code with a really dumb name. It definitely should work on an Arduino Mega, and likely on an Arduino Ethernet and a lot more.

# Arduino Setup
````
_____________________
|PWM / Sensor Shield|
--------------------
||||||||| ||||||||||
____________________
|Ethernet Shield    |
--------------------
||||||||| ||||||||||
_________________________
|Arudino Mega           |
-------------------------
````
