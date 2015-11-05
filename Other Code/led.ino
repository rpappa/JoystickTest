#include <ArduinoJson.h>
#include <math.h>

/*
  UDPSendReceive.pde:
 This sketch receives UDP message strings, prints them to the serial port
 and sends an "acknowledge" string back to the sender

 A Processing sketch is included at the end of file that can be used to send
 and received messages for testing with a computer.

 created 21 Aug 2010
 by Michael Margolis

 This code is in the public domain.
 */


#include <SPI.h>         // needed for Arduino versions later than 0018
#include <Ethernet.h>
#include <EthernetUdp.h>         // UDP library from: bjoern@cs.stanford.edu 12/30/2008

#include <avr/interrupt.h>
const int jagsigpin = 11; //pwm connection on pin 9
const int jagpwrpin = 7;//using pin 7 as the pwm power reference
const int jaggndpin = 8;//using pin 8 as the pwm ground reference
const int ledpin = 13;//using the standard LED as a status indicator
const int buffsize = 3;//determining the digits buffer size

char mybuffer[buffsize]; //declare an array to take input from the serial
int value = 47;// decimal 47 corresponds with the pwm midpoint
int minvalue = 20;// decimal 20 corresponds with the pwm full reverse
int maxvalue = 74; //decimal 74 corresponds with the pwm full forward

boolean ledstatus = LOW; //initialize the LED status

// Enter a MAC address and IP address for your controller below.
// The IP address will be dependent on your local network:
byte mac[] = {
  0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED
};
IPAddress ip(10, 3, 40, 42);

unsigned int localPort = 5000;      // local port to listen on

// buffers for receiving and sending data
const int maxPacketSize = 1024; //2048 reppin
char packetBuffer[maxPacketSize];
//char packetBuffer[UDP_TX_PACKET_MAX_SIZE]; //buffer to hold incoming packet,
char  ReplyBuffer[] = "acknowledged";       // a string to send back

// An EthernetUDP instance to let us send and receive packets over UDP
EthernetUDP Udp;

void enableLED() {
  digitalWrite(8, HIGH);
}

void disableLED() {
  digitalWrite(8, LOW);
}

float min = 16;
float midone = 45;
float midtwo = 49;
float max = 77;

int floatToPWM(float speed) {
  Serial.println("speed:");
  Serial.println(speed);
  if(speed<0) {
    if(speed <=-1) {
      return round(min);
    } else {
      return round((midone-min)*speed+midone);
    }
  } else if(speed>0) {
    if(speed >=1) {
      return round(max);
    } else {
      return round((max-midtwo)*speed+midtwo);
    }
  }
  return round(midone+1);
}

void setup() {
  // start the Ethernet and UDP:
  Ethernet.begin(mac, ip);
  Udp.begin(localPort);
  pinMode(8, OUTPUT);

  Serial.begin(9600);
  TCCR1B = TCCR1B & 0b11111000 | 0x04;

 //setup the pins to be used with the PWM
  pinMode(jagsigpin, OUTPUT);
//  pinMode(jagpwrpin, OUTPUT);
//  pinMode(jaggndpin, OUTPUT);

//setup the LED status pin
  pinMode(ledpin, OUTPUT);

//define the pwm reference voltages
//  digitalWrite(jagpwrpin,HIGH);
//  digitalWrite(jaggndpin,LOW);

//set the pwm to midscale so the jaguar starts in a friendly state
  analogWrite(jagsigpin,value);
}

void loop() {
  // if there's data available, read a packet
  int packetSize = Udp.parsePacket();
  if (packetSize) {
    /*Serial.println(UDP_TX_PACKET_MAX_SIZE);
    Serial.print("Received packet of size ");
    Serial.println(packetSize);
    Serial.print("From ");
    IPAddress remote = Udp.remoteIP();
    for (int i = 0; i < 4; i++) {
      Serial.print(remote[i], DEC);
      if (i < 3) {
        Serial.print(".");
      }
    }
    Serial.print(", port ");
    Serial.println(Udp.remotePort());*/

    // read the packet into packetBufffer
    Udp.read(packetBuffer, maxPacketSize);
    //Serial.println("Contents:");
    Serial.println(String(packetBuffer));
    //Serial.println("end contents");
    /*if(String(packetBuffer).indexOf("LED:") != -1) {
      Serial.println("tits: "  + String(packetBuffer).substring(4));
      if(String(packetBuffer).substring(4) == "1") {
        enableLED();
      } else {
        disableLED();
      }
    }*/
    
    StaticJsonBuffer<200> jsonBuffer;

    //
    // Step 2: Deserialize the JSON string
    //
    JsonObject& root = jsonBuffer.parseObject(packetBuffer);
    
    if (!root.success())
    {
      Serial.println("parseObject() failed");
      return;
    }
    
    //
    // Step 3: Retrieve the values
    //
    boolean fire = root["fire"];
    float cannonPower  = root["cannonPower"];
    Serial.println("FIRE: " + String(fire));
    Serial.println("CANNON:" + String(floatToPWM(cannonPower)));
    analogWrite(jagsigpin,floatToPWM(cannonPower)); 
    
    if(fire) {
      enableLED();
    } else {
      disableLED();
    }

    // send a reply, to the IP address and port that sent us the packet we received
    /*Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
    Udp.write(ReplyBuffer);
    Udp.endPacket();*/
  }
  //delay(10);
}


