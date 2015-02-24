package com.radex392.temporalrelativity.config;

import com.radex392.temporalrelativity.reference.Reference;
import com.radex392.temporalrelativity.utility.LogHelper;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler
{
	public static Configuration configuration;
	public static boolean testVal = false;

	public static void init(File configFile)
	{
		if(configuration == null)
		{
			configuration = new Configuration(configFile);
			loadConfiguration();
		}
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.modID.equalsIgnoreCase(Reference.MOD_ID))
		{
			loadConfiguration();
		}
	}

	private static void loadConfiguration()
	{
		testVal = configuration.getBoolean("configValue", Configuration.CATEGORY_GENERAL, false, "");

		if(configuration.hasChanged())
		{
			configuration.save();
		}
	}
}
