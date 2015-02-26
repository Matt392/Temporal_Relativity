package com.radex392.temporalrelativity.init;

import com.radex392.temporalrelativity.tileentitiy.TileEntityTR;

import java.util.ArrayList;

public class ModTileEntity
{
	public static ArrayList<TileEntityTR> tileEntities = new ArrayList<TileEntityTR>();

	public static void init()
	{
		tileEntities.clear();

		for(int i = 0; i < tileEntities.size(); ++i)
		{
			tileEntities.get(i).register();
		}
	}

	public static void registerRecipies()
	{
		for(int i = 0; i < tileEntities.size(); ++i)
		{
			tileEntities.get(i).registerRecipies();
		}
	}
}
