package com.radex392.temporalrelativity.block;

import com.radex392.temporalrelativity.TemporalRelativity;
import com.radex392.temporalrelativity.init.ModBlocks;
import com.radex392.temporalrelativity.init.ModOres;
import com.radex392.temporalrelativity.reference.Names;
import com.radex392.temporalrelativity.utility.SideHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
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
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.Random;
import java.util.function.IntToDoubleFunction;

public class TemporalInfusor extends BlockTR
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

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		//player.openGui(TemporalRelativity.instance, 0, world, x, y, z);
		return true;
	}

	public Item getItemDropped(int par1, Random random, IntToDoubleFunction par3)
	{
		return Item.getItemFromBlock(ModBlocks.temporalInfusor);
	}

	public Item getItem(World world, int par2, int par3, int par4)
	{
		return Item.getItemFromBlock(ModBlocks.temporalInfusor);
	}

	@SideOnly(Side.CLIENT)
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		this.direction(world, x, y, z);
	}

	private void direction(World world, int x, int y, int z)
	{
		if(world.isRemote == false)
		{
			Block direction = world.getBlock(x, y, z - 1);
			Block direction1 = world.getBlock(x, y, z + 1);
			Block direction2 = world.getBlock(x - 1, y, z);
			Block direction3 = world.getBlock(x + 1, y, z);
			byte byte0 = SideHelper.FRONT;

			if (direction.func_149730_j() && !direction1.func_149730_j())
			{
				byte0 = SideHelper.FRONT;
			}

			if (direction1.func_149730_j() && !direction.func_149730_j())
			{
				byte0 = SideHelper.BACK;
			}

			if (direction2.func_149730_j() && !direction3.func_149730_j())
			{
				byte0 = SideHelper.RIGHT;
			}

			if (direction3.func_149730_j() && !direction2.func_149730_j())
			{
				byte0 = SideHelper.LEFT;
			}

			world.setBlockMetadataWithNotify(x, y, z, byte0, 2);
		}
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack)
	{
		int direction = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (direction == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		else if (direction == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		else if (direction == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		else if (direction == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}

		if (itemstack.hasDisplayName())
		{
			//((TileEntityTemporalInfusor) world.getTileEntity(x, y, z)).furnaceName(itemstack.getDisplayName());
		}
	}

	public static void updateBlockState(boolean active, World world, int x, int y, int z)
	{
		int direction = world.getBlockMetadata(x, y, z);
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		isActive = true;

		if (active)
		{
			world.setBlock(x, y, z, ModBlocks.temporalInfusorActive);
		}
		else
		{
			world.setBlock(x, y, z, ModBlocks.temporalInfusor);
		}

		isActive = false;
		world.setBlockMetadataWithNotify(x, y, z, direction, 2);

		if (tileEntity != null)
		{
			tileEntity.validate();
			world.setTileEntity(x, y, z, tileEntity);
		}
	}

	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		/*
		if (!isActive)
		{
			TileEntityTemporalInfusor tileEntityTemporalInfusor= (TileEntityTemporalInfusor) world.getTileEntity(x, y, z);

			if (tileEntityTemporalInfusor != null) {
				for (int i = 0; i < tileEntityTemporalInfusor.getSizeInventory(); ++i) {
					ItemStack itemstack = tileEntityTemporalInfusor.getStackInSlot(i);

					if (itemstack != null) {
						float f = this.random.nextFloat() * 0.6F + 0.1F;
						float f1 = this.random.nextFloat() * 0.6F + 0.1F;
						float f2 = this.random.nextFloat() * 0.6F + 0.1F;

						while (itemstack.stackSize > 0) {
							int j = this.random.nextInt(21) + 10;

							if (j > itemstack.stackSize) {
								j = itemstack.stackSize;
							}

							itemstack.stackSize -= j;
							EntityItem entityitem = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1), (double) ((float) z + f2), new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));

							if (itemstack.hasTagCompound()) {
								entityitem.getEntityItem().setTagCompound(((NBTTagCompound) itemstack.getTagCompound().copy()));
							}

							float f3 = 0.025F;
							entityitem.motionX = (double) ((float) this.random.nextGaussian() * f3);
							entityitem.motionY = (double) ((float) this.random.nextGaussian() * f3 + 0.1F);
							entityitem.motionZ = (double) ((float) this.random.nextGaussian() * f3);
							world.spawnEntityInWorld(entityitem);
						}
					}
				}
				world.func_147453_f(x, y, z, block);
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
		*/
	}
}
