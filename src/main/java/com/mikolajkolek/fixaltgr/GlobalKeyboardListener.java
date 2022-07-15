package com.mikolajkolek.fixaltgr;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyboardListener implements NativeKeyListener {
    public boolean altKeyPressed = false;
    public boolean controlKeyPressed = false;

    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ALT)
            altKeyPressed = true;
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL)
            controlKeyPressed = true;
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ALT)
            altKeyPressed = false;
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL)
            controlKeyPressed = false;
    }
}
