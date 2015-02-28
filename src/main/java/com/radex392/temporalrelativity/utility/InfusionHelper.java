package com.radex392.temporalrelativity.utility;

import com.radex392.temporalrelativity.init.ModItems;
import com.radex392.temporalrelativity.init.ModOres;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

import java.util.ArrayList;

public class InfusionHelper
{
	protected static class InfusionBurnPair
	{
		public InfusionBurnPair(Item _item, int _burnTime)
		{
			item = _item;
			burnTime = _burnTime;
		}
		public Item item;
		public int burnTime;
	}

	protected static class InfusionRecipe
	{
		public InfusionRecipe(Block _block, ItemStack _result)
		{
			this(Item.getItemFromBlock(_block), _result);
		}
		public InfusionRecipe(Item _item, ItemStack _result)
		{
			this(new ItemStack(_item), _result);
		}
		public InfusionRecipe(ItemStack _itemStack, ItemStack _result)
		{
			input = _itemStack;
			result = _result;
		}
		public ItemStack input;
		public ItemStack result;

		public boolean compare(InfusionRecipe recipe)
		{
			return input.getItem() == recipe.input.getItem() && result.getItem() == recipe.result.getItem();
		}
	}

	public static ArrayList<InfusionBurnPair> burnPairs = new ArrayList<InfusionBurnPair>();
	public static ArrayList<InfusionRecipe> infusionRecipes = new ArrayList<InfusionRecipe>();

	public static final InfusionBurnPair enderPearl = new InfusionBurnPair(Items.ender_pearl, 800);

	public static void init()
	{
		burnPairs.clear();

		burnPairs.add(enderPearl);

		infusionRecipes.clear();

		addInfusionRecipe(new InfusionRecipe(Items.iron_ingot, new ItemStack(ModOres.temporalIron.getIngot())));
	}

	public static void addInfusionRecipe(InfusionRecipe recipe)
	{
		for(int i = 0; i < infusionRecipes.size(); ++i)
		{
			if(infusionRecipes.get(i).compare(recipe) == true)
			{
				return;
			}
		}
		infusionRecipes.add(recipe);
	}

	public static int getItemBurnTime(ItemStack itemStack)
	{
		if(itemStack == null)
		{
			return 0;
		}
		for(int i = 0; i < burnPairs.size(); ++i)
		{
			if(burnPairs.get(i).item == itemStack.getItem())
			{
				return burnPairs.get(i).burnTime;
			}
		}
		return 0;
	}

	public static boolean isItemFuel(ItemStack itemStack)
	{
		for(int i = 0; i < burnPairs.size(); ++i)
		{
			if(burnPairs.get(i).item == itemStack.getItem())
			{
				return true;
			}
		}
		return false;
	}

	public static ItemStack getInfusionResult(ItemStack itemStack)
	{
		for(int i = 0; i < infusionRecipes.size(); ++i)
		{
			if(infusionRecipes.get(i).input.getItem() == itemStack.getItem())
			{
				return infusionRecipes.get(i).result;
			}
		}

		return null;
	}
}
