package xfacthd.pinghud;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.slf4j.Logger;

import java.lang.reflect.Field;

@Mod(PingHUD.MODID)
public final class PingHUD
{
    public static final String MODID = "pinghud";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PingHUD()
    {
        if (FMLEnvironment.dist != Dist.CLIENT)
        {
            LOGGER.warn("PingHUD is a client-only mod, it should not be installed on the server!");
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ClientEvents
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
                    throw new RuntimeException("Failed to replace Gui#tabList!", e);
                }
            });
        }

        private ClientEvents() { }
    }
}
