package XFactHD.pinghud;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigHandler
{
    public static Configuration configuration;

    public static int textPosition = 0;

    public static void init(File configFile)
    {
        if (configuration == null)
        {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equalsIgnoreCase(Reference.MOD_ID))
        {
            loadConfiguration();
        }
    }

    private static void loadConfiguration()
    {
        textPosition  = configuration.getInt("textPosition", "General", 0, 0, 3, "Sets the position of the ping display on the screen (0 --> top left, 1 --> top right, 2 --> bottom left, 3 --> bottom right).");

        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }
}