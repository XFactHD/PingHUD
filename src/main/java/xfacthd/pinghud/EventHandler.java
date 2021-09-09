package xfacthd.pinghud;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Locale;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "pinghud")
public class EventHandler
{
    @SubscribeEvent
    public static void renderOverlay(final RenderGameOverlayEvent.Pre event)
    {
        if (event.getType() == RenderGameOverlayEvent.ElementType.PLAYER_LIST)
        {
            //noinspection ConstantConditions
            int ping = mc().player.connection.getPlayerInfo(mc().player.getUniqueID()).getResponseTime();

            String text = String.format(Locale.ROOT, "Ping: %4dms", ping);
            Minecraft.getInstance().ingameGUI.getTabList().setFooter(new StringTextComponent(text));
        }
    }

    private static Minecraft mc() { return Minecraft.getInstance(); }
}