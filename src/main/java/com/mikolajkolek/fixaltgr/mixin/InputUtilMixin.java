package com.mikolajkolek.fixaltgr.mixin;

import com.mikolajkolek.fixaltgr.FixAltGr;
import com.mojang.blaze3d.platform.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.concurrent.TimeUnit;

@Mixin(InputUtil.class)
public class InputUtilMixin {
    @Inject(at = @At(value = "HEAD"), method = "isKeyPressed", cancellable = true)
    private static void isKeyPressed(long handle, int code, CallbackInfoReturnable<Boolean> cir) {
        if(code != 341) return;

        if(!FixAltGr.listener.controlKeyPressed || FixAltGr.listener.altKeyPressed)
            cir.setReturnValue(false);
        else {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            }
			catch (InterruptedException e) {
                FixAltGr.LOGGER.error("The isKeyPressed sleep was interrupted!");
            }

            if (!FixAltGr.listener.controlKeyPressed || FixAltGr.listener.altKeyPressed)
                cir.setReturnValue(false);
            else
                cir.setReturnValue(true);
        }
    }
}
