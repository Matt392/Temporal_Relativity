package com.radex392.temporalrelativity.init;

import com.radex392.temporalrelativity.block.BlockTR;
import com.radex392.temporalrelativity.reference.Names;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.ArrayList;

public class ModBlocks
{
	public static ArrayList<BlockTR> blocks = new ArrayList<BlockTR>();
	//public static final BlockTR temporalIronOre = new TemporalIronOre();

	public static void init()
	{
		blocks.clear();
		//items.add(temporalIronOre);

		for(int i = 0; i < blocks.size(); ++i)
		{
			blocks.get(i).register();
		}
	}

	public static void registerRecipies()
	{

	}
}
