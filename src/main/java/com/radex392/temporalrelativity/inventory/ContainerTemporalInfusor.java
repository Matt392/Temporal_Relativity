package com.radex392.temporalrelativity.inventory;

import com.radex392.temporalrelativity.tileEntitiy.TileEntityTemporalInfusor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTemporalInfusor extends ContainerTR
{
	private TileEntityTemporalInfusor tileEntityTemporalInfusor;
	private int lastCookTime;               // How much longer the Calcinator will burn
	private int lastBurnTime;               // The fuel value for the currently burning fuel
	private int lastItemCookTime;           // How long the current item has been "cooking"

	public ContainerTemporalInfusor(InventoryPlayer inventoryPlayer, TileEntityTemporalInfusor tileEntityTemporalInfusor)
	{
		this.tileEntityTemporalInfusor = tileEntityTemporalInfusor;

		// Add the input slot to the container
		this.addSlotToContainer(new Slot(tileEntityTemporalInfusor, TileEntityTemporalInfusor.INPUT_INVENTORY_INDEX, 56, 17));

		// Add the fuel slot to the container
		this.addSlotToContainer(new Slot(tileEntityTemporalInfusor, TileEntityTemporalInfusor.FUEL_INVENTORY_INDEX, 56, 53));

		// Add the output results slot to the container
		this.addSlotToContainer(new Slot(tileEntityTemporalInfusor, TileEntityTemporalInfusor.OUTPUT_INVENTORY_INDEX, 116, 35));

		// Add the player's inventory slots to the container
		for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex)
		{
			for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex)
			{
				this.addSlotToContainer(new Slot(inventoryPlayer, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 84 + inventoryRowIndex * 18));
			}
		}

		// Add the player's action bar slots to the container
		for (int actionBarSlotIndex = 0; actionBarSlotIndex < 9; ++actionBarSlotIndex)
		{
			this.addSlotToContainer(new Slot(inventoryPlayer, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 142));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting iCrafting)
	{
		super.addCraftingToCrafters(iCrafting);
		iCrafting.sendProgressBarUpdate(this, 0, this.tileEntityTemporalInfusor.deviceCookTime);
		iCrafting.sendProgressBarUpdate(this, 1, this.tileEntityTemporalInfusor.fuelBurnTime);
		iCrafting.sendProgressBarUpdate(this, 2, this.tileEntityTemporalInfusor.itemCookTime);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (Object crafter : this.crafters)
		{
			ICrafting icrafting = (ICrafting) crafter;

			if (this.lastCookTime != this.tileEntityTemporalInfusor.deviceCookTime)
			{
				icrafting.sendProgressBarUpdate(this, 0, this.tileEntityTemporalInfusor.deviceCookTime);
			}

			if (this.lastBurnTime != this.tileEntityTemporalInfusor.fuelBurnTime)
			{
				icrafting.sendProgressBarUpdate(this, 1, this.tileEntityTemporalInfusor.fuelBurnTime);
			}

			if (this.lastItemCookTime != this.tileEntityTemporalInfusor.itemCookTime)
			{
				icrafting.sendProgressBarUpdate(this, 2, this.tileEntityTemporalInfusor.itemCookTime);
			}
		}

		this.lastCookTime = this.tileEntityTemporalInfusor.deviceCookTime;
		this.lastBurnTime = this.tileEntityTemporalInfusor.fuelBurnTime;
		this.lastItemCookTime = this.tileEntityTemporalInfusor.itemCookTime;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex)
	{
		ItemStack itemStack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack())
		{

			ItemStack slotItemStack = slot.getStack();
			itemStack = slotItemStack.copy();

			/**
			 * If we are shift-clicking an item out of the Aludel's container,
			 * attempt to put it in the first available slot in the player's
			 * inventory
			 */
			if (slotIndex < TileEntityTemporalInfusor.INVENTORY_SIZE)
			{
				if (!this.mergeItemStack(slotItemStack, TileEntityTemporalInfusor.INVENTORY_SIZE, inventorySlots.size(), false))
				{
					return null;
				}
			}
			else
			{
				if (TileEntityTemporalInfusor.isItemFuel(slotItemStack))
				{
					if (!this.mergeItemStack(slotItemStack, TileEntityTemporalInfusor.FUEL_INVENTORY_INDEX, TileEntityTemporalInfusor.OUTPUT_INVENTORY_INDEX, false))
					{
						return null;
					}
				}
				else if (!this.mergeItemStack(slotItemStack, TileEntityTemporalInfusor.INPUT_INVENTORY_INDEX, TileEntityTemporalInfusor.OUTPUT_INVENTORY_INDEX, false))
				{
					return null;
				}
			}

			if (slotItemStack.stackSize == 0)
			{
				slot.putStack(null);
			}
			else
			{
				slot.onSlotChanged();
			}
		}

		return itemStack;
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int valueType, int updatedValue)
	{
		if (valueType == 0)
		{
			this.tileEntityTemporalInfusor.deviceCookTime = updatedValue;
		}

		if (valueType == 1)
		{
			this.tileEntityTemporalInfusor.fuelBurnTime = updatedValue;
		}

		if (valueType == 2)
		{
			this.tileEntityTemporalInfusor.itemCookTime = updatedValue;
		}
	}
}
