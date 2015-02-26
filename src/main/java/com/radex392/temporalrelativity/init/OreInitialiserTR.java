package com.radex392.temporalrelativity.init;

import com.radex392.temporalrelativity.block.BlockTROre;
import com.radex392.temporalrelativity.item.ItemTR;
import com.radex392.temporalrelativity.item.ItemTRIngot;
import com.radex392.temporalrelativity.reference.Names;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class OreInitialiserTR
{
	private String oreDictName = "";

	private ItemTRIngot ingot;
	private BlockTROre oreBlock;

	public OreInitialiserTR(String name, String oreDictionaryName, int harvestLevel, float hardness, float resistance)
	{
		oreDictName = oreDictionaryName;

		ingot = new ItemTRIngot(name + Names.Types.INGOT);
		oreBlock = new BlockTROre(name + Names.Types.ORE, harvestLevel, hardness, resistance);
	}

	public void registerOreType()
	{
		ingot.register();
		oreBlock.register();

		if(oreDictName.length() > 0)
		{
			OreDictionary.registerOre(Names.OreDictionary.ORE + oreDictName, oreBlock);
			OreDictionary.registerOre(Names.OreDictionary.INGOT + oreDictName, ingot);
		}
	}

	public void registerRecipes()
	{
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(oreBlock), new ItemStack(ingot), new ItemStack(ingot), new ItemStack(ingot)));
	}

	public ItemTR getIngot()
	{
		return ingot;
	}
}
