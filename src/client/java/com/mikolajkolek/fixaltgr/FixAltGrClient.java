package com.mikolajkolek.fixaltgr;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FixAltGrClient implements ClientModInitializer {
	public static final String MODID = "fixaltgr";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static final GlobalKeyboardListener listener = new GlobalKeyboardListener();

	@Override public void onInitializeClient() {
		try {
			//FixAltGrLibraryLocator.setAaDefaultLocator();
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
