package com.radex392.temporalrelativity;


import com.radex392.temporalrelativity.config.ConfigHandler;
import com.radex392.temporalrelativity.entity.EntityInitialiserTR;
import com.radex392.temporalrelativity.init.ModBlocks;
import com.radex392.temporalrelativity.init.ModItems;
import com.radex392.temporalrelativity.init.ModOres;
import com.radex392.temporalrelativity.proxy.IProxy;
import com.radex392.temporalrelativity.reference.Reference;
import com.radex392.temporalrelativity.utility.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import sun.rmi.runtime.Log;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class TemporalRelativity
{
	@Mod.Instance(Reference.MOD_ID)
	public static TemporalRelativity instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigHandler());

        proxy.registerRenderThings();

		ModItems.init();
		ModBlocks.init();
		ModOres.init();

        //init mobs
        EntityInitialiserTR.init();

		LogHelper.info("Pre Init Complete");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		LogHelper.info("Init Complete");
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		LogHelper.info("Post Init Complete");
	}
}
