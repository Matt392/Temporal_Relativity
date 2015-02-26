package com.radex392.temporalrelativity.init;

import com.radex392.temporalrelativity.item.ItemTR;
import com.radex392.temporalrelativity.reference.Names;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.ArrayList;

public class ModItems
{
	public static ArrayList<ItemTR> items = new ArrayList<ItemTR>();
	//public static final ItemTR temporalIronIngot = new TemporalIronIngot(Names.Items.TEMPORAL_IRON_INGOT);

	public static void init()
	{
		items.clear();
		//items.add(temporalIronIngot);

		for(int i = 0; i < items.size(); ++i)
		{
			items.get(i).register();
		}
	}

	public static void registerRecipies()
	{

	}
}
