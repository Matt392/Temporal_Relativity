package com.radex392.temporalrelativity.client.gui;

import com.radex392.temporalrelativity.config.ConfigHandler;
import com.radex392.temporalrelativity.reference.Reference;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import java.util.List;

public class ModGUIConfig extends GuiConfig
{
	public ModGUIConfig(GuiScreen parentScreen)
	{
		super(parentScreen
				, new ConfigElement(ConfigHandler.configuration.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements()
				, Reference.MOD_ID, Reference.MOD_NAME, false, false
				, GuiConfig.getAbridgedConfigPath(ConfigHandler.configuration.toString()));
	}
}
