package com.mikolajkolek.fixaltgr;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FixAltGr {
	public static final String MODID = "fix-alt-gr";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static final GlobalKeyboardListener listener = new GlobalKeyboardListener();

	public static void onInitialize() {
		try {
			FixAltGrLibraryLocator.setAaDefaultLocator();
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			LOGGER.error("There was a problem registering the native hook.");
			LOGGER.error(ex.getMessage());

			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(listener);
		LOGGER.info("Registered native key listener");
	}
}
