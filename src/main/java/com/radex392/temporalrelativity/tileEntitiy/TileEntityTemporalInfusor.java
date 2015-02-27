package com.radex392.temporalrelativity.tileEntitiy;

import com.radex392.temporalrelativity.network.PacketHandler;
import com.radex392.temporalrelativity.network.message.MessageTileTemporalInfusor;
import com.radex392.temporalrelativity.reference.Names;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityTemporalInfusor extends TileEntityTR implements ISidedInventory
{
	public static final String name = Names.TileEntities.TEMPORAL_INFUSOR;

	public static final int INVENTORY_SIZE = 3;
	public static final int FUEL_INVENTORY_INDEX = 0;
	public static final int INPUT_INVENTORY_INDEX = 1;
	public static final int OUTPUT_INVENTORY_INDEX = 2;
	public int deviceCookTime;
	public int fuelBurnTime;
	public int itemCookTime;

	public byte outputStackSize, outputStackMeta;

	public int itemSuckCoolDown = 0;

	private ItemStack[] inventory;

	public TileEntityTemporalInfusor()
	{
		super();

		inventory = new ItemStack[INVENTORY_SIZE];
	}

	public static int getItemBurnTime(ItemStack itemStack)
	{
		return TileEntityFurnace.getItemBurnTime(itemStack);
	}

	public static boolean isItemFuel(ItemStack itemStack)
	{
		return TileEntityFurnace.isItemFuel(itemStack);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return side == ForgeDirection.DOWN.ordinal() ? new int[]{FUEL_INVENTORY_INDEX, OUTPUT_INVENTORY_INDEX} : new int[]{INPUT_INVENTORY_INDEX, OUTPUT_INVENTORY_INDEX};
	}

	@Override
	public boolean canInsertItem(int slotIndex, ItemStack itemStack, int side)
	{
		return isItemValidForSlot(slotIndex, itemStack);
	}

	@Override
	public boolean canExtractItem(int slotIndex, ItemStack itemStack, int side)
	{
		return slotIndex == OUTPUT_INVENTORY_INDEX;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);

		// Read in the ItemStacks in the inventory from NBT
		NBTTagList tagList = nbtTagCompound.getTagList(Names.NBT.ITEMS, 10);
		inventory = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < tagList.tagCount(); ++i)
		{
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			byte slotIndex = tagCompound.getByte("Slot");
			if (slotIndex >= 0 && slotIndex < inventory.length)
			{
				inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
			}
		}

		deviceCookTime = nbtTagCompound.getInteger("deviceCookTime");
		fuelBurnTime = nbtTagCompound.getInteger("fuelBurnTime");
		itemCookTime = nbtTagCompound.getInteger("itemCookTime");
	}

	@Override
	public int getSizeInventory()
	{
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slotIndex)
	{
		//sendDustPileData();
		return inventory[slotIndex];
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int decrementAmount)
	{
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (itemStack != null)
		{
			if (itemStack.stackSize <= decrementAmount)
			{
				setInventorySlotContents(slotIndex, null);
			}
			else
			{
				itemStack = itemStack.splitStack(decrementAmount);
				if (itemStack.stackSize == 0)
				{
					setInventorySlotContents(slotIndex, null);
				}
			}
		}

		return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex)
	{
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (itemStack != null)
		{
			setInventorySlotContents(slotIndex, null);
		}
		return itemStack;
	}

	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack itemStack)
	{
		inventory[slotIndex] = itemStack;
		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName()
	{
		return this.hasCustomName() ? this.getCustomName() : name;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return this.hasCustomName();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public void openInventory()
	{
		// NOOP
	}

	@Override
	public void closeInventory()
	{
		// NOOP
	}

	@Override
	public boolean isItemValidForSlot(int slotIndex, ItemStack itemStack)
	{
		return false;
	}

	private void sendDustPileData()
	{
		if (this.getBlockType() != null)
		{
			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 2, getOutputStackSize());
			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 3, getOutputStackMeta());
		}
	}

	private int getOutputStackSize()
	{
		if (this.inventory[OUTPUT_INVENTORY_INDEX] != null)
		{
			return this.inventory[OUTPUT_INVENTORY_INDEX].stackSize;
		}

		return 0;
	}

	private int getOutputStackMeta()
	{
		if (this.inventory[OUTPUT_INVENTORY_INDEX] != null)
		{
			return this.inventory[OUTPUT_INVENTORY_INDEX].getItemDamage();
		}

		return 0;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound)
	{
		super.writeToNBT(nbtTagCompound);

		// Write the ItemStacks in the inventory to NBT
		NBTTagList tagList = new NBTTagList();
		for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex)
		{
			if (inventory[currentIndex] != null)
			{
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) currentIndex);
				inventory[currentIndex].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}
		nbtTagCompound.setTag(Names.NBT.ITEMS, tagList);
		nbtTagCompound.setInteger("deviceCookTime", deviceCookTime);
		nbtTagCompound.setInteger("fuelBurnTime", fuelBurnTime);
		nbtTagCompound.setInteger("itemCookTime", itemCookTime);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		sendDustPileData();
		return PacketHandler.INSTANCE.getPacketFrom(new MessageTileTemporalInfusor(this));
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int scale)
	{
		return this.itemCookTime * scale / 200;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int scale)
	{
		if (this.fuelBurnTime > 0)
		{
			return this.deviceCookTime * scale / this.fuelBurnTime;
		}

		return 0;
	}

	@Override
	public void updateEntity()
	{
		boolean isBurning = this.deviceCookTime > 0;
		boolean sendUpdate = false;

		// If the Calcinator still has burn time, decrement it
		if (this.deviceCookTime > 0)
		{
			this.deviceCookTime--;
		}

		if (!this.worldObj.isRemote)
		{
			// Start "cooking" a new item, if we can
			if (this.deviceCookTime == 0 && this.canInfuse())
			{
				this.fuelBurnTime = this.deviceCookTime = getItemBurnTime(this.inventory[FUEL_INVENTORY_INDEX]);

				if (this.deviceCookTime > 0)
				{
					sendUpdate = true;

					if (this.inventory[FUEL_INVENTORY_INDEX] != null)
					{
						--this.inventory[FUEL_INVENTORY_INDEX].stackSize;

						if (this.inventory[FUEL_INVENTORY_INDEX].stackSize == 0)
						{
							this.inventory[FUEL_INVENTORY_INDEX] = this.inventory[FUEL_INVENTORY_INDEX].getItem().getContainerItem(inventory[FUEL_INVENTORY_INDEX]);
						}
					}
				}
			}

			// Continue "cooking" the same item, if we can
			if (this.deviceCookTime > 0 && this.canInfuse())
			{
				this.itemCookTime++;

				if (this.itemCookTime == 200)
				{
					this.itemCookTime = 0;
					this.infuseItem();
					sendUpdate = true;
				}
			}
			else
			{
				this.itemCookTime = 0;
			}

			// If the state has changed, catch that something changed
			if (isBurning != this.deviceCookTime > 0)
			{
				sendUpdate = true;
			}

			/*
			//Item sucking
			if (this.itemSuckCoolDown > 0)
			{
				itemSuckCoolDown--;
			}
			else
			{
				if (suckInItems(this))
				{
					markDirty();
				}
				itemSuckCoolDown = DEFAULT_ITEM_SUCK_COOL_DOWN;
			}
			*/
		}

		if (sendUpdate)
		{
			this.markDirty();
			this.state = this.deviceCookTime > 0 ? (byte) 1 : (byte) 0;
			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.state);
			//sendDustPileData();
			this.worldObj.notifyBlockChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
		}
	}

	@Override
	public boolean receiveClientEvent(int eventId, int eventData)
	{
		if (eventId == 1)
		{
			this.state = (byte) eventData;
			// NAME UPDATE - this.worldObj.updateAllLightTypes(this.xCoord, this.yCoord, this.zCoord);
			this.worldObj.func_147451_t(this.xCoord, this.yCoord, this.zCoord);
			return true;
		}
		else if (eventId == 2)
		{
			this.outputStackSize = (byte) eventData;
			return true;
		}
		else if (eventId == 3)
		{
			this.outputStackMeta = (byte) eventData;
			return true;
		}
		{
			return super.receiveClientEvent(eventId, eventData);
		}
	}

	public static ItemStack getInfusionResult(ItemStack itemStack)
	{
		return FurnaceRecipes.smelting().getSmeltingResult(itemStack);
	}

	private boolean canInfuse()
	{
		if (inventory[INPUT_INVENTORY_INDEX] == null)
		{
			return false;
		}
		else
		{
			ItemStack infusionResult = getInfusionResult(inventory[INPUT_INVENTORY_INDEX]);

			/**
			 * If we don't get a calcination result, then return false
			 */
			if (infusionResult == null)
			{
				return false;
			}

			/**
			 * If either slot is empty, return true (we have a valid calcination result
			 */
			if (this.inventory[OUTPUT_INVENTORY_INDEX] == null)
			{
				return true;
			}

			if (!inventory[OUTPUT_INVENTORY_INDEX].isItemEqual(infusionResult))
			{
				return false;
			}

			int result = inventory[OUTPUT_INVENTORY_INDEX].stackSize + infusionResult.stackSize;
			return result <= getInventoryStackLimit() && result <= inventory[OUTPUT_INVENTORY_INDEX].getMaxStackSize();
		}
	}

	public void infuseItem()
	{
		if (this.canInfuse())
		{
			ItemStack infusedItemStack = getInfusionResult(this.inventory[INPUT_INVENTORY_INDEX]);
			addItemStackToOutput(infusedItemStack.copy());

			this.inventory[INPUT_INVENTORY_INDEX].stackSize--;

			if (this.inventory[INPUT_INVENTORY_INDEX].stackSize <= 0)
			{
				this.inventory[INPUT_INVENTORY_INDEX] = null;
			}
		}
	}

	private void addItemStackToOutput(ItemStack infusedItemStack)
	{
		int maxStackSize = Math.min(getInventoryStackLimit(), infusedItemStack.getMaxStackSize());

		if (this.inventory[OUTPUT_INVENTORY_INDEX] == null)
		{
			this.inventory[OUTPUT_INVENTORY_INDEX] = infusedItemStack;
			return;
		}
		if (this.inventory[OUTPUT_INVENTORY_INDEX].isItemEqual(infusedItemStack) && this.inventory[OUTPUT_INVENTORY_INDEX].stackSize < maxStackSize)
		{
			int addedSize = Math.min(infusedItemStack.stackSize, maxStackSize - this.inventory[OUTPUT_INVENTORY_INDEX].stackSize);
			infusedItemStack.stackSize -= addedSize;
			this.inventory[OUTPUT_INVENTORY_INDEX].stackSize += addedSize;
			if (infusedItemStack.stackSize == 0)
			{
				return;
			}
		}
	}
}
