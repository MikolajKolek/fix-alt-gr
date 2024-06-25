package com.mikolajkolek.fixaltgr;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FixAltGrClient implements ClientModInitializer {
	public static final String MODID = "fix-alt-gr";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static final GlobalKeyboardListener listener = new GlobalKeyboardListener();

	@Override public void onInitializeClient() {
		try {
			if(StringUtils.containsIgnoreCase(MinecraftClient.getInstance().getVersionType(), "quilt")) {
				FixAltGrClient.LOGGER.info("FixAltGr detected running on Quilt, correcting library locator...");
				QuiltLibraryLocator.setAaDefaultLocator();
			}

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
