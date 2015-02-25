package com.radex392.temporalrelativity.init;

import com.radex392.temporalrelativity.reference.Names;

import java.util.ArrayList;

public class ModOres
{
	public static ArrayList<OreInitialiserTR> ores = new ArrayList<OreInitialiserTR>();

	public static OreInitialiserTR temporalIron = new OreInitialiserTR(Names.Ores.TEMPORAL_IRON, Names.OreDictionary.TEMPORAL_IRON, 2, 5.0F, 6.0F);

	public static void init()
	{
		ores.clear();
		ores.add(temporalIron);

		for(int i = 0; i < ores.size(); ++i)
		{
			ores.get(i).registerOreType();
		}
	}

	public static void registerRecipies()
	{
		for(int i = 0; i < ores.size(); ++i)
		{
			ores.get(i).registerRecipes();
		}
	}
}
