package com.radex392.temporalrelativity.init;

import com.radex392.temporalrelativity.block.BlockTR;
import com.radex392.temporalrelativity.block.TemporalInfusor;
import com.radex392.temporalrelativity.reference.Names;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.ArrayList;

public class ModBlocks
{
	public static ArrayList<BlockTR> blocks = new ArrayList<BlockTR>();

	public static final BlockTR temporalInfusor = new TemporalInfusor(false);
	public static final BlockTR temporalInfusorActive = new TemporalInfusor(true);

	public static void init()
	{
		blocks.clear();
		blocks.add(temporalInfusor);

		for(int i = 0; i < blocks.size(); ++i)
		{
			blocks.get(i).register();
		}
	}

	public static void registerRecipies()
	{
		for(int i = 0; i < blocks.size(); ++i)
		{
			blocks.get(i).registerRecipies();
		}
	}
}
