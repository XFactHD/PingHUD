package xfacthd.pinghud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.util.ObfuscationReflectionHelper;

@Mod(value = PingHUD.MOD_ID, dist = Dist.CLIENT)
@SuppressWarnings("UtilityClassWithPublicConstructor")
public final class PingHUD
{
    public static final String MOD_ID = "pinghud";

    public PingHUD(IEventBus modBus)
    {
        modBus.addListener(PingHUD::onClientSetup);
    }

    private static void onClientSetup(final FMLClientSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            try
            {
                ObfuscationReflectionHelper.setPrivateValue(
                        Gui.class,
                        Minecraft.getInstance().gui,
                        new CustomPlayerTabOverlay(Minecraft.getInstance()),
                        "tabList"
                );
            }
            catch (Throwable e)
            {
                throw new RuntimeException("Failed to replace Gui#tabList!", e);
            }
        });
    }
}
