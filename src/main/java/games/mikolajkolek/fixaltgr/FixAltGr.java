package games.mikolajkolek.fixaltgr;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FixAltGr implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("fix-alt-gr");
	public static GlobalKeyboardListener listener = new GlobalKeyboardListener();

	@Override
	public void onInitialize() {
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(listener);
	}
}
