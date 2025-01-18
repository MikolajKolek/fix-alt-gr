package com.mikolajkolek.fixaltgr;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FixAltGrClient implements ClientModInitializer {
	public static final String MODID = "fix-alt-gr";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static final GlobalKeyboardListener listener = new GlobalKeyboardListener();
	public static boolean axiomLoaded = false;

	@Override public void onInitializeClient() {
		try {
			if(StringUtils.containsIgnoreCase(MinecraftClient.getInstance().getVersionType(), "quilt")) {
				FixAltGrClient.LOGGER.info("FixAltGr detected running on Quilt, correcting library locator...");
				QuiltLibraryLocator.setAaDefaultLocator();
			}
			if(FabricLoader.getInstance().isModLoaded("axiom")) {
				FixAltGrClient.LOGGER.warn("FixAltGr detected that Axiom is loaded. This means that FixAltGr has to disable parts of its functionality, possibly causing it to work worse.");
				axiomLoaded = true;
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
