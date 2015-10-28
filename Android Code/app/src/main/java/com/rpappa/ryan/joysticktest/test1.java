package com.rpappa.ryan.joysticktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class test1 extends AppCompatActivity {
    private JoystickView joystick;
    private SliderView slider;
    private Button fireButton;

    public int sliderValue  = 0;
    public int joyPower = 0;
    public int joyAngle = 0;
    public boolean fire = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        joystick = (JoystickView) findViewById(R.id.joy);
        slider = (SliderView) findViewById(R.id.slide1);
        fireButton = (Button) findViewById(R.id.button);

        fireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fire = true;
            }
        });

        //Event listener that always returns the variation of the angle in degrees, motion power in percentage and direction of movement
        joystick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {

            @Override
            public void onValueChanged(int angle, int power, int direction) {
                // TODO Auto-generated method stub
                System.out.println("angle: " + angle);
                System.out.println("power: " + power);
                joyAngle = angle;
                joyPower = power;
            }
        }, JoystickView.DEFAULT_LOOP_INTERVAL);

        slider.setOnJoystickMoveListener(new SliderView.OnJoystickMoveListener() {

            @Override
            public void onValueChanged(int angle, int power, int direction) {
                // TODO Auto-generated method stub
//                System.out.println("angle: " + angle);
                System.out.println("power: " + power);
                sliderValue = power;

            }
        }, SliderView.DEFAULT_LOOP_INTERVAL);
        Thread udpLoop = new Thread(new Runnable() {
            @Override
            public void run() {
                while (1==1) {
                    String jsonMessage = "{\"joyAngle\":" + joyAngle + ",\"joyPower\":" + joyPower + ",\"cannonPower\":" + sliderValue + ",\"fire\":" + fire + "}";
                    fire = false;
                    final byte[] message = jsonMessage.getBytes();
//                    System.out.println(message);


                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                InetAddress address = InetAddress.getByName("192.168.110.127");
                                DatagramPacket packet = new DatagramPacket(message, message.length, address, 5000);
                                DatagramSocket datagramSocket = new DatagramSocket();
                                datagramSocket.send(packet);
                            } catch (Exception e) {
                                System.err.println(e);
                            }
                        }
                    });
                    t.start();
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {
                        System.err.println(e);
                    }

                }
            }
        });
        udpLoop.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
