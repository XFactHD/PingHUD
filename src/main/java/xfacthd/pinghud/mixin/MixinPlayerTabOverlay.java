package xfacthd.pinghud.mixin;

import net.minecraft.client.gui.components.PlayerTabOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import xfacthd.pinghud.CustomPlayerTabOverlay;

@Mixin(PlayerTabOverlay.class)
public class MixinPlayerTabOverlay
{
    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I", ordinal = 1), index = 0)
    private int adjustColumnWidth(int first) { return first - 11 + CustomPlayerTabOverlay.getPingDisplayWidth(); }
}