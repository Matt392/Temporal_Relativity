package com.radex392.temporalrelativity.init;

import com.radex392.temporalrelativity.reference.Names;

import java.util.ArrayList;

public class ModOres
{
	public  static ArrayList<OreInitialiserTR> ores = new ArrayList<OreInitialiserTR>();

	public static void init()
	{
		ores.add(new OreInitialiserTR(Names.Ores.TEMPORAL_IRON, Names.OreDictionary.TEMPORAL_IRON, 2, 5.0F, 6.0F));

		for(int i = 0; i < ores.size(); ++i)
		{
			ores.get(i).registerOreType();
		}
	}
}
