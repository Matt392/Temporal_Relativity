package com.radex392.temporalrelativity.init;

import com.radex392.temporalrelativity.block.BlockTROre;
import com.radex392.temporalrelativity.item.ItemTRIngot;
import com.radex392.temporalrelativity.reference.Names;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class OreInitialiserTR
{
	private String oreDictName = "";

	private ItemTRIngot ingot;
	private BlockTROre oreBlock;

	public OreInitialiserTR(String name, String oreDictName, int harvestLevel, float hardness, float resistance)
	{
		oreDictName = oreDictName;

		ingot = new ItemTRIngot(name + Names.Types.INGOT);
		oreBlock = new BlockTROre(name + Names.Types.ORE, harvestLevel, hardness, resistance);
	}

	public void registerOreType()
	{
		GameRegistry.registerItem(ingot, ingot.getUnlocalizedName());
		GameRegistry.registerBlock(oreBlock, oreBlock.getUnlocalizedName());

		if(oreDictName.length() > 0)
		{
			OreDictionary.registerOre(Names.OreDictionary.ORE + oreDictName, oreBlock);
			OreDictionary.registerOre(Names.OreDictionary.INGOT + oreDictName, ingot);
		}
	}
}
