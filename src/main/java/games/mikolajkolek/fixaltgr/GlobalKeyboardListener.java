package games.mikolajkolek.fixaltgr;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyboardListener implements NativeKeyListener {
    public boolean altKeyPressed = false;
    public boolean controlKeyPressed = false;
    //long lastPressTime;

    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ALT) {
            altKeyPressed = true;
            //FixAltGr.LOGGER.info("Alt pressed!");
            //FixAltGr.LOGGER.info(System.nanoTime() - lastPressTime);
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            controlKeyPressed = true;
            //lastPressTime = System.nanoTime();
            //FixAltGr.LOGGER.info("Control pressed!");
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ALT) {
            altKeyPressed = false;
            //FixAltGr.LOGGER.info("Alt unpressed!");
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            controlKeyPressed = false;
            //FixAltGr.LOGGER.info("Control unpressed!");
        }
    }
}
