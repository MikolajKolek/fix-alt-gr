package com.mikolajkolek.fixaltgr;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeLibraryLocator;
import com.github.kwhat.jnativehook.NativeSystem;
import org.quiltmc.loader.api.QuiltLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FixAltGrLibraryLocator implements NativeLibraryLocator {
	public static void setAaDefaultLocator() {
		System.setProperty("jnativehook.lib.locator", FixAltGrLibraryLocator.class.getCanonicalName());
	}

	// This code is based on the JNativeHook class DefaultLibraryLocator
	// You can find the class together with the whole source code at https://github.com/kwhat/jnativehook/
	@Override
	public Iterator<File> getLibraries() {
		List<File> libraries = new ArrayList<>(1);

		String libName = System.getProperty("jnativehook.lib.name", "JNativeHook");

		// Get the package name for the GlobalScreen.
		String basePackage = GlobalScreen.class.getPackage().getName().replace('.', '/');

		String libNativeArch = NativeSystem.getArchitecture().toString().toLowerCase();
		String libNativeName = System
				.mapLibraryName(libName) // Get what the system "thinks" the library name should be.
				.replaceAll("\\.jnilib$", "\\.dylib"); // Hack for OS X JRE 1.6 and earlier.

		// Resource path for the native library.
		String libResourcePath = "/" + basePackage + "/lib/" +
				NativeSystem.getFamily().toString().toLowerCase() +
				'/' + libNativeArch + '/' + libNativeName;

		String classLocation;
		if(QuiltLoader.isDevelopmentEnvironment())
			classLocation = GlobalScreen.class.getProtectionDomain().getCodeSource().getLocation().toString();
		else
			classLocation = QuiltLoader.getModContainer(FixAltGr.MODID).get().getSourcePaths().get(0).get(0).toString();

		File classFile;
		try {
			classFile = new File(new URI(classLocation));
		}
		catch (URISyntaxException e) {
			FixAltGr.LOGGER.warn(e.getMessage());
			classFile = new File(classLocation);
		}

		File libFile;
		if (classFile.isFile()) {
			// Jar Archive
			String libPath = System.getProperty("jnativehook.lib.path", classFile.getParentFile().getPath());

			InputStream resourceInputStream = GlobalScreen.class.getResourceAsStream(libResourcePath);
			if (resourceInputStream == null) {
				throw new RuntimeException("Unable to extract the native library " + libResourcePath + "!\n");
			}

			String version = GlobalScreen.class.getPackage().getImplementationVersion();
			if (version != null) {
				version = '-' + version;
			} else {
				version = "";
			}

			libFile = new File(
					libPath,
					libNativeName.replaceAll("^(.*)\\.(.*)$", "$1" + version + '.' + libNativeArch + ".$2")
			);
			if (!libFile.exists()) {
				try {
					// Check and see if a copy of the native lib already exists.
					FileOutputStream libOutputStream = new FileOutputStream(libFile);

					// Read from the digest stream and write to the file steam.
					int size;
					byte[] buffer = new byte[4 * 1024];
					while ((size = resourceInputStream.read(buffer)) != -1) {
						libOutputStream.write(buffer, 0, size);
					}

					// Close all the streams.
					resourceInputStream.close();
					libOutputStream.close();
				}
				catch (IOException e) {
					throw new RuntimeException(e.getMessage(), e);
				}

				FixAltGr.LOGGER.info("Extracted library: " + libFile.getPath() + ".\n");
			}
		}  else {
			// Loose Classes
			libFile = Paths.get(classFile.getAbsolutePath(), libResourcePath).toFile();
		}

		if (!libFile.exists()) {
			throw new RuntimeException("Unable to locate JNI library at " + libFile.getPath() + "!\n");
		}

		FixAltGr.LOGGER.info("Loading library: " + libFile.getPath() + ".\n");
		libraries.add(libFile);

		return libraries.iterator();
	}
}
