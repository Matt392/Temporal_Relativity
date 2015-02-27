package com.radex392.temporalrelativity.init;

import com.radex392.temporalrelativity.reference.Reference;
import com.radex392.temporalrelativity.tileEntitiy.TileEntityTR;
import com.radex392.temporalrelativity.tileEntitiy.TileEntityTemporalInfusor;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.ArrayList;

public class ModTileEntitys
{
	//public static ArrayList<TileEntityTR> tileEntities = new ArrayList<TileEntityTR>();

	public static void init()
	{
		GameRegistry.registerTileEntity(TileEntityTemporalInfusor.class, TileEntityTemporalInfusor.name);
		/*
		tileEntities.clear();

		for(int i = 0; i < tileEntities.size(); ++i)
		{
			tileEntities.get(i).register();
		}
		*/
	}

	public static void registerRecipies()
	{
		/*
		for(int i = 0; i < tileEntities.size(); ++i)
		{
			tileEntities.get(i).registerRecipies();
		}
		*/
	}
}
