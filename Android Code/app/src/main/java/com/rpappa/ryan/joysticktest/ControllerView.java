package com.rpappa.ryan.joysticktest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * TODO: document your custom view class.
 */
public class ControllerView extends View {

    public ControllerView(Context context) {
        super(context);
        init(null, 0);
    }

    public ControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ControllerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

    }
    public ArrayList getGameControllerIds() {
        ArrayList gameControllerDeviceIds = new ArrayList();
        int[] deviceIds = InputDevice.getDeviceIds();
        for (int deviceId : deviceIds) {
            InputDevice dev = InputDevice.getDevice(deviceId);
            int sources = dev.getSources();

            // Verify that the device has gamepad buttons, control sticks, or both.
            if (((sources & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD)
                    || ((sources & InputDevice.SOURCE_JOYSTICK)
                    == InputDevice.SOURCE_JOYSTICK)) {
                // This device is a game controller. Store its device ID.
                if (!gameControllerDeviceIds.contains(deviceId)) {
                    gameControllerDeviceIds.add(deviceId);
                }
            }
        }
        return gameControllerDeviceIds;
    }
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (event.isFromSource(InputDevice.SOURCE_CLASS_JOYSTICK)) {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                // process the joystick movement...
                System.out.println(MotionEvent.AXIS_X);
                System.out.println(MotionEvent.AXIS_RTRIGGER);
                return true;
            }
        }
//        if (event.isFromSource(InputDevice.SOURCE_CLASS_POINTER)) {
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_HOVER_MOVE:
//                    // process the mouse hover movement...
//                    return true;
//                case MotionEvent.ACTION_SCROLL:
//                    // process the scroll wheel movement...
//                    return true;
//            }
//        }
        return super.onGenericMotionEvent(event);
    }
}
