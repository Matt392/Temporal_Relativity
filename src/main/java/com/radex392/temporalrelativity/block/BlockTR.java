package com.radex392.temporalrelativity.block;

import com.radex392.temporalrelativity.creativetab.CreativeTabTR;
import com.radex392.temporalrelativity.reference.Textures;
import com.radex392.temporalrelativity.utility.SideHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockTR extends Block
{
	@SideOnly(Side.CLIENT)
	protected IIcon sides[];

	public BlockTR(Material material, String name, String tools, int harvestLevel, float hardness, float resistance)
	{
		super(material);
		sides = new IIcon[SideHelper.MAX];

		setCreativeTab(CreativeTabTR.TR_TAB);
		setBlockName(name);
		setHarvestLevel(tools, harvestLevel);
		setHardness(hardness);
		setResistance(resistance);
		setStepSound(soundTypePiston);
	}

	@Override
	public String getUnlocalizedName()
	{
		return String.format("tile.%s%s", Textures.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	@Override
	@SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));

		sides[SideHelper.TOP] = blockIcon;
		sides[SideHelper.BOTTOM] = blockIcon;
		sides[SideHelper.LEFT] = blockIcon;
		sides[SideHelper.RIGHT] = blockIcon;
		sides[SideHelper.FRONT] = blockIcon;
		sides[SideHelper.BACK] = blockIcon;
	}

	public void register()
	{
		GameRegistry.registerBlock(this, this.getUnlocalizedName());
	}

	public void registerRecipies()
	{

	}

	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
	}
}
