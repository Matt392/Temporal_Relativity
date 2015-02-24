package com.radex392.temporalrelativity.init;

import com.radex392.temporalrelativity.reference.Names;

import java.util.ArrayList;

public class ModOres
{
	public  static ArrayList<TROreInitialiser> ores = new ArrayList<TROreInitialiser>();

	public static void init()
	{
		ores.add(new TROreInitialiser(Names.Ores.TEMPORAL_IRON, Names.OreDictionary.TEMPORAL_IRON, 3, 4.0F, 6.0F));

		for(int i = 0; i < ores.size(); ++i)
		{
			ores.get(i).registerOreType();
		}
	}
}
