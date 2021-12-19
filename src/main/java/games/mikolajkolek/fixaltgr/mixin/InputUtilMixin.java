package games.mikolajkolek.fixaltgr.mixin;

import games.mikolajkolek.fixaltgr.FixAltGr;
import net.minecraft.client.util.InputUtil;
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

        if(!FixAltGr.listener.controlKeyPressed || FixAltGr.listener.altKeyPressed) {
            //FixAltGr.LOGGER.info("Corrected bug");
            cir.setReturnValue(false);
        }
        else {
            //FixAltGr.LOGGER.info("Sleeping to possibly correct bug");

            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                FixAltGr.LOGGER.error("The chat keyPressed sleep was interrupted!");
            }

            if (!FixAltGr.listener.controlKeyPressed || FixAltGr.listener.altKeyPressed) {
                //FixAltGr.LOGGER.info("Corrected bug");
                cir.setReturnValue(false);
            }
            else
                cir.setReturnValue(true);
        }
    }
}
