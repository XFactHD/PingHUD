package io.github.xfacthd.pinghud.mixin;

import net.minecraft.client.gui.components.PlayerTabOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import io.github.xfacthd.pinghud.CustomPlayerTabOverlay;

@Mixin(PlayerTabOverlay.class)
public class MixinPlayerTabOverlay {
    @SuppressWarnings("MethodMayBeStatic")
    @ModifyArg(method = "extractRenderState", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"), index = 0)
    private int adjustColumnWidth(int first) {
        return first - 11 + CustomPlayerTabOverlay.getPingDisplayWidth();
    }
}
