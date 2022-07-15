package com.mikolajkolek.fixaltgr.mixin;

import com.mikolajkolek.fixaltgr.FixAltGr;
import net.minecraft.Bootstrap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bootstrap.class)
public class BootstrapMixin {
	@Inject(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/registry/Registry;freezeBuiltins()V"))
	private static void initialize(CallbackInfo ci) {
		FixAltGr.onInitialize();
	}
}
