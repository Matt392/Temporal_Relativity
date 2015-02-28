package com.radex392.temporalrelativity.block;

import com.radex392.temporalrelativity.creativetab.CreativeTabTR;
import com.radex392.temporalrelativity.reference.Reference;
import com.radex392.temporalrelativity.reference.Textures;
import com.radex392.temporalrelativity.tileEntitiy.TileEntityTR;
import com.radex392.temporalrelativity.utility.SideHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class BlockTR extends Block
{
	@SideOnly(Side.CLIENT)
	protected IIcon sides[];
	protected boolean multiSided;

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

		multiSided = false;
	}

	@Override
	public String getUnlocalizedName()
	{
		return String.format("tile.%s%s", Reference.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
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

	@Override
	 public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		dropInventory(world, x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
	{
		if (world.getTileEntity(x, y, z) instanceof TileEntityTR)
		{
			int direction = 0;
			int facing = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

			if (facing == 0)
			{
				direction = ForgeDirection.NORTH.ordinal();
			}
			else if (facing == 1)
			{
				direction = ForgeDirection.EAST.ordinal();
			}
			else if (facing == 2)
			{
				direction = ForgeDirection.SOUTH.ordinal();
			}
			else if (facing == 3)
			{
				direction = ForgeDirection.WEST.ordinal();
			}

			if (itemStack.hasDisplayName())
			{
				((TileEntityTR) world.getTileEntity(x, y, z)).setCustomName(itemStack.getDisplayName());
			}


			world.setBlockMetadataWithNotify(x, y, z, direction, 2);
			((TileEntityTR) world.getTileEntity(x, y, z)).setOrientation(direction);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if(side == SideHelper.TOP || side == SideHelper.BOTTOM || multiSided == false)
		{
			return sides[side];
		}
		if(meta == ForgeDirection.SOUTH.ordinal())
		{
			return sides[side];
		}
		else if(meta == ForgeDirection.NORTH.ordinal())
		{
			switch (side)
			{
				case SideHelper.FRONT:
				{
					return sides[SideHelper.BACK];
				}
				case SideHelper.BACK:
				{
					return sides[SideHelper.FRONT];
				}
				case SideHelper.RIGHT:
				{
					return sides[SideHelper.LEFT];
				}
				case SideHelper.LEFT:
				{
					return sides[SideHelper.RIGHT];
				}
				default:
					break;
			}
		}
		else if(meta == ForgeDirection.EAST.ordinal())
		{
			switch (side)
			{
				case SideHelper.FRONT:
				{
					return sides[SideHelper.LEFT];
				}
				case SideHelper.BACK:
				{
					return sides[SideHelper.RIGHT];
				}
				case SideHelper.RIGHT:
				{
					return sides[SideHelper.FRONT];
				}
				case SideHelper.LEFT:
				{
					return sides[SideHelper.BACK];
				}
				default:
					break;
			}
		}
		else if(meta == ForgeDirection.WEST.ordinal())
		{
			switch (side)
			{
				case SideHelper.FRONT:
				{
					return sides[SideHelper.RIGHT];
				}
				case SideHelper.BACK:
				{
					return sides[SideHelper.LEFT];
				}
				case SideHelper.RIGHT:
				{
					return sides[SideHelper.BACK];
				}
				case SideHelper.LEFT:
				{
					return sides[SideHelper.FRONT];
				}
				default:
					break;
			}
		}

		return sides[side];
	}

	protected void dropInventory(World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);

		if (!(tileEntity instanceof IInventory))
		{
			return;
		}

		IInventory inventory = (IInventory) tileEntity;

		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			ItemStack itemStack = inventory.getStackInSlot(i);

			if (itemStack != null && itemStack.stackSize > 0)
			{
				Random rand = new Random();

				float dX = rand.nextFloat() * 0.8F + 0.1F;
				float dY = rand.nextFloat() * 0.8F + 0.1F;
				float dZ = rand.nextFloat() * 0.8F + 0.1F;

				EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, itemStack.copy());

				if (itemStack.hasTagCompound())
				{
					entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
				}

				float factor = 0.05F;
				entityItem.motionX = rand.nextGaussian() * factor;
				entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
				entityItem.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(entityItem);
				itemStack.stackSize = 0;
			}
		}
	}
}
