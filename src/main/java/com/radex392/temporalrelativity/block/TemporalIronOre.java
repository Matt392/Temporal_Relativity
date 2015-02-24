package com.radex392.temporalrelativity.block;

import com.radex392.temporalrelativity.reference.Names;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;

public class TemporalIronOre extends BlockTR
{
	public TemporalIronOre()
	{
		super(Material.iron);
		setCreativeTab(CreativeTabs.tabBlock);
		setBlockName(Names.Blocks.TEMPORAL_IRON_ORE);
		setHarvestLevel("pickaxe", 3);
		setHardness(4.0F);
		setResistance(6.0F);
		setStepSound(soundTypePiston);
	}
}
