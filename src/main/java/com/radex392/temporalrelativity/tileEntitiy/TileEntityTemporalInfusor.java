package com.radex392.temporalrelativity.tileEntitiy;

import com.radex392.temporalrelativity.block.TemporalInfusor;
import com.radex392.temporalrelativity.network.PacketHandler;
import com.radex392.temporalrelativity.network.message.MessageTileTemporalInfusor;
import com.radex392.temporalrelativity.reference.Names;
import com.radex392.temporalrelativity.utility.InfusionHelper;
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
		inventory = new ItemStack[getSizeInventory()];
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
		return hasCustomName() ? getCustomName() : name;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return hasCustomName();
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
		if (getBlockType() != null)
		{
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 2, getOutputStackSize());
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 3, getOutputStackMeta());
		}
	}

	private int getOutputStackSize()
	{
		if (inventory[OUTPUT_INVENTORY_INDEX] != null)
		{
			return inventory[OUTPUT_INVENTORY_INDEX].stackSize;
		}

		return 0;
	}

	private int getOutputStackMeta()
	{
		if (inventory[OUTPUT_INVENTORY_INDEX] != null)
		{
			return inventory[OUTPUT_INVENTORY_INDEX].getItemDamage();
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
		return itemCookTime * scale / 200;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int scale)
	{
		if (fuelBurnTime > 0)
		{
			return deviceCookTime * scale / fuelBurnTime;
		}

		return 0;
	}

	@Override
	public void updateEntity()
	{
		boolean isBurning = deviceCookTime > 0;
		boolean sendUpdate = false;

		if (deviceCookTime > 0 && canInfuse())
		{
			deviceCookTime--;
		}

		if (!worldObj.isRemote)
		{
			// Start "cooking" a new item, if we can
			if (deviceCookTime == 0 && canInfuse())
			{
				fuelBurnTime = deviceCookTime = InfusionHelper.getItemBurnTime(inventory[FUEL_INVENTORY_INDEX]);

				if (deviceCookTime > 0)
				{
					sendUpdate = true;

					if (inventory[FUEL_INVENTORY_INDEX] != null)
					{
						--inventory[FUEL_INVENTORY_INDEX].stackSize;

						if (inventory[FUEL_INVENTORY_INDEX].stackSize == 0)
						{
							inventory[FUEL_INVENTORY_INDEX] = inventory[FUEL_INVENTORY_INDEX].getItem().getContainerItem(inventory[FUEL_INVENTORY_INDEX]);
						}
					}
				}
			}

			// Continue "cooking" the same item, if we can
			if (deviceCookTime > 0 && canInfuse())
			{
				itemCookTime++;

				if (itemCookTime == 200)
				{
					itemCookTime = 0;
					infuseItem();
					sendUpdate = true;
				}
			}
			else
			{
				itemCookTime = 0;
			}

			// If the state has changed, catch that something changed
			if (isBurning != deviceCookTime > 0)
			{
				sendUpdate = true;
			}
		}

		if (isBurning != deviceCookTime > 0)
		{
			TemporalInfusor.updateBlockState(deviceCookTime > 0, worldObj, xCoord, yCoord, zCoord);
		}

		if (sendUpdate)
		{
			markDirty();
			state = deviceCookTime > 0 ? (byte) 1 : (byte) 0;
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 1, state);
			//sendDustPileData();
			worldObj.notifyBlockChange(xCoord, yCoord, zCoord, getBlockType());
		}
	}

	@Override
	public boolean receiveClientEvent(int eventId, int eventData)
	{
		if (eventId == 1)
		{
			state = (byte) eventData;
			// NAME UPDATE - worldObj.updateAllLightTypes(xCoord, yCoord, zCoord);
			worldObj.func_147451_t(xCoord, yCoord, zCoord);
			return true;
		}
		else if (eventId == 2)
		{
			outputStackSize = (byte) eventData;
			return true;
		}
		else if (eventId == 3)
		{
			outputStackMeta = (byte) eventData;
			return true;
		}
		{
			return super.receiveClientEvent(eventId, eventData);
		}
	}

	private boolean canInfuse()
	{
		if (inventory[INPUT_INVENTORY_INDEX] == null)
		{
			return false;
		}
		else
		{
			ItemStack infusionResult = InfusionHelper.getInfusionResult(inventory[INPUT_INVENTORY_INDEX]);

			if (infusionResult == null)
			{
				return false;
			}

			if (inventory[OUTPUT_INVENTORY_INDEX] == null)
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
		if (canInfuse())
		{
			ItemStack infusedItemStack = InfusionHelper.getInfusionResult(inventory[INPUT_INVENTORY_INDEX]);
			addItemStackToOutput(infusedItemStack.copy());

			inventory[INPUT_INVENTORY_INDEX].stackSize--;

			if (inventory[INPUT_INVENTORY_INDEX].stackSize <= 0)
			{
				inventory[INPUT_INVENTORY_INDEX] = null;
			}
		}
	}

	private void addItemStackToOutput(ItemStack infusedItemStack)
	{
		int maxStackSize = Math.min(getInventoryStackLimit(), infusedItemStack.getMaxStackSize());

		if (inventory[OUTPUT_INVENTORY_INDEX] == null)
		{
			inventory[OUTPUT_INVENTORY_INDEX] = infusedItemStack;
			return;
		}
		if (inventory[OUTPUT_INVENTORY_INDEX].isItemEqual(infusedItemStack) && inventory[OUTPUT_INVENTORY_INDEX].stackSize < maxStackSize)
		{
			int addedSize = Math.min(infusedItemStack.stackSize, maxStackSize - inventory[OUTPUT_INVENTORY_INDEX].stackSize);
			infusedItemStack.stackSize -= addedSize;
			inventory[OUTPUT_INVENTORY_INDEX].stackSize += addedSize;
			if (infusedItemStack.stackSize == 0)
			{
				return;
			}
		}
	}
}
