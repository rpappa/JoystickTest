# What parts do I need?
* Arduino mega
* Ethernet shield
* PWM/Sensor shield
* Router with wifi capability
* ethernet cable
* 12v Battery
* 1 motor (eventually 3)
* 1 jaguar speed controller (eventually 3)
* Some wires and connectors and stuff
* An android device (ideally a smartphone)
* Optional: xbox controller (doesn't work yet) connected to android with an
* Optional: OTG cable

# How do I hook it all up?
Make a nice little stack of all your arduino shields.

Connect your router to the battery. Connect the arduino ethernet shield to the router.

Connect the motor and speed controller, connect them to power, and connect the PWM to port 12 on the arduino PWM/sensor shield.

Connect the android device to the router and in the app settings (dots in upper right) set IP address to 10.3.40.42 and port to 5000. Hook up the
xbox controller via OTG if you want.

(should work now)
