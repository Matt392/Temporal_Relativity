package com.radex392.temporalrelativity.block;

import com.radex392.temporalrelativity.TemporalRelativity;
import com.radex392.temporalrelativity.init.ModBlocks;
import com.radex392.temporalrelativity.init.ModOres;
import com.radex392.temporalrelativity.reference.GUIs;
import com.radex392.temporalrelativity.reference.Names;
import com.radex392.temporalrelativity.reference.Particles;
import com.radex392.temporalrelativity.tileEntitiy.TileEntityTemporalInfusor;
import com.radex392.temporalrelativity.utility.SideHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.Random;
import java.util.function.IntToDoubleFunction;

public class TemporalInfusor extends BlockTR implements ITileEntityProvider
{
	@SideOnly(Side.CLIENT)
	private IIcon top;
	@SideOnly(Side.CLIENT)
	private IIcon side;
	@SideOnly(Side.CLIENT)
	private IIcon front;

	private static Boolean isActive;
	private final Boolean isActiveBeta;
	private final Random random = new Random();

	public TemporalInfusor(Boolean isActive)
	{
		super(Material.iron, Names.Blocks.TEMPORAL_INFUSOR, "pickaxe", 0, 4.0F, 6.0F );

		isActiveBeta = isActive;
	}

	@Override
	@SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		if(isActiveBeta == false)
		{
			blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
		}
		else
		{
			blockIcon = iconRegister.registerIcon(String.format("%s_Active", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
		}
		top = iconRegister.registerIcon(String.format("%s_Top", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
		side = iconRegister.registerIcon(String.format("%s_Side", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));

		sides[SideHelper.TOP] = top;
		sides[SideHelper.BOTTOM] = top;
		sides[SideHelper.LEFT] = top;
		sides[SideHelper.RIGHT] = side;
		sides[SideHelper.FRONT] = blockIcon;
		sides[SideHelper.BACK] = side;
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		return sides[side];
	}

	@Override
	public void registerRecipies()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this), "ttt", "tft", "rrr", 't', new ItemStack(ModOres.temporalIron.getIngot()), 'f', new ItemStack(Block.getBlockFromName("furnace")), 'r', new ItemStack(Items.redstone)));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metaData)
	{
		return new TileEntityTemporalInfusor();
	}

	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		if (world.getTileEntity(x, y, z) instanceof TileEntityTemporalInfusor)
		{
			if (((TileEntityTemporalInfusor) world.getTileEntity(x, y, z)).getState() == 1)
			{
				// Fire pot particles
				world.spawnParticle(Particles.WITCH_MAGIC, (double) x + 0.5F, (double) y + 0.4F, (double) ((z + 0.5F) + (random.nextFloat() * 0.5F - 0.3F)), 0.0D, 0.0D, 0.0D);
				world.spawnParticle(Particles.FLAME, (double) x + 0.5F, (double) y + 0.4F, (double) z + 0.5F, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		if (player.isSneaking())
		{
			return false;
		}
		else
		{
			if (!world.isRemote)
			{
				if (world.getTileEntity(x, y, z) instanceof TileEntityTemporalInfusor)
				{
					player.openGui(TemporalRelativity.instance, GUIs.TEMPORAL_INFUSOR.ordinal(), world, x, y, z);
				}
			}

			return true;
		}
	}

	@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventId, int eventData)
	{
		super.onBlockEventReceived(world, x, y, z, eventId, eventData);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		return tileentity != null && tileentity.receiveClientEvent(eventId, eventData);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		if ((world.getTileEntity(x, y, z) instanceof TileEntityTemporalInfusor) && (((TileEntityTemporalInfusor) world.getTileEntity(x, y, z)).getState() == 1))
		{
			return 15;
		}

		return super.getLightValue(world, x, y, z);
	}
}
