package xfacthd.pinghud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;

import java.util.Locale;

public class CustomPlayerTabOverlay extends PlayerTabOverlay
{
    private static final int PADDING = 6; //Must be an even number!!!
    private static final int DIGIT_COUNT = 4;

    private final Minecraft minecraft;

    public CustomPlayerTabOverlay(Minecraft mc)
    {
        super(mc, mc.gui);
        this.minecraft = mc;
    }

    @Override
    protected void renderPingIcon(PoseStack poseStack, int colWidth, int entryX, int entryY, PlayerInfo playerInfo)
    {
        int ping = playerInfo.getLatency();

        int color;
        if (ping < 100)
        {
            color = 0xFF00FF00;
        }
        else if (ping < 300)
        {
            color = 0xFFFFFF00;
        }
        else
        {
            color = 0xFFFF0000;
        }

        String text = formatPing(ping);
        Minecraft.getInstance().font.drawShadow(
                poseStack,
                text,
                entryX + colWidth - minecraft.font.width(text),
                entryY,
                color
        );
    }

    private static String formatPing(int ping) { return String.format(Locale.ROOT, "%" + DIGIT_COUNT + "dms", ping); }

    public static int getPingDisplayWidth() { return PADDING + Minecraft.getInstance().font.width(formatPing(0)); }
}