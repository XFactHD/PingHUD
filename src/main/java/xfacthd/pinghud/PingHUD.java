package xfacthd.pinghud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.fmllegacy.network.FMLNetworkConstants;

import java.lang.reflect.Field;

@Mod(PingHUD.MODID)
public class PingHUD
{
    public static final String MODID = "pinghud";

    public PingHUD()
    {
        ModLoadingContext.get().registerExtensionPoint(
                IExtensionPoint.DisplayTest.class,
                () -> new IExtensionPoint.DisplayTest(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true)
        );
    }

    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientEvents
    {
        @SubscribeEvent
        public static void onClientSetup(final FMLClientSetupEvent event)
        {
            event.enqueueWork(() ->
            {
                Field tabListField = ObfuscationReflectionHelper.findField(Gui.class, "f_92998_");
                tabListField.setAccessible(true);

                try
                {
                    tabListField.set(
                            Minecraft.getInstance().gui,
                            new CustomPlayerTabOverlay(Minecraft.getInstance())
                    );
                }
                catch (IllegalAccessException e)
                {
                    throw new RuntimeException("Failed to replace Gui#tabList!");
                }
            });
        }
    }
}
