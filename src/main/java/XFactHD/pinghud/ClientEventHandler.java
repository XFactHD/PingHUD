package XFactHD.pinghud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientEventHandler
{
    private static final int TOP_OFFSET = 2;
    private static final int LEFT_OFFSET = 2;
    private static final int BOTTOM_OFFSET = 10;
    private static final int RIGHT_OFFSET = 78;
    private int lastDisplayValue = -1;
    private long lastDisplayTime = 0;

    @SubscribeEvent
    public void drawScreen(RenderGameOverlayEvent.Pre event)
    {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT)
        {
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            String playerName = Minecraft.getMinecraft().player.getName();
            NetHandlerPlayClient netHandler = Minecraft.getMinecraft().getConnection();

            int ping = -1;
            if (System.currentTimeMillis() - lastDisplayTime > 1000)
            {
                lastDisplayTime = System.currentTimeMillis();
                if (netHandler != null)
                {
                    NetworkPlayerInfo info = netHandler.getPlayerInfo(playerName);
                    ping = info != null ? info.getResponseTime() : -1;
                }
                lastDisplayValue = ping;
            }
            else
            {
                ping = lastDisplayValue;
            }

            ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
            int textX = 0;
            int textY = 0;

            switch (ConfigHandler.textPosition)
            {
                case 0:
                {
                    textX = LEFT_OFFSET;
                    textY = TOP_OFFSET;
                    break;
                }
                case 1:
                {
                    textX = res.getScaledWidth() - RIGHT_OFFSET;
                    textY = TOP_OFFSET;
                    break;
                }
                case 2:
                {
                    textX = res.getScaledWidth() - RIGHT_OFFSET;
                    textY = res.getScaledHeight() - BOTTOM_OFFSET;
                    break;
                }
                case 3:
                {
                    textX = LEFT_OFFSET;
                    textY = res.getScaledHeight() - BOTTOM_OFFSET;
                    break;
                }
            }

            int offset = ping < 0 ? 6 : (int)Math.log10(ping) + 1;
            String text = "Ping:";
            fontRenderer.drawString(text, textX, textY, EnumDyeColor.WHITE.getColorValue(), true);
            textX += fontRenderer.getStringWidth(text) + 6 + offset;
            fontRenderer.drawString((ping >= 0 ? ping : "NaN") + " ms", textX, textY, EnumDyeColor.WHITE.getColorValue(), true);
        }
    }
}