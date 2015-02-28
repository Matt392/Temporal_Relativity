package com.radex392.temporalrelativity.handler;

import com.radex392.temporalrelativity.inventory.ContainerTemporalInfusor;
import com.radex392.temporalrelativity.inventory.GUITemporalInfusor;
import com.radex392.temporalrelativity.reference.GUIs;
import com.radex392.temporalrelativity.tileEntitiy.TileEntityTemporalInfusor;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GUIHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == GUIs.TEMPORAL_INFUSOR.ordinal())
		{
			TileEntityTemporalInfusor tileEntityTemporalInfusor = (TileEntityTemporalInfusor) world.getTileEntity(x, y, z);
			return new ContainerTemporalInfusor(player.inventory, tileEntityTemporalInfusor);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == GUIs.TEMPORAL_INFUSOR.ordinal())
		{
			TileEntityTemporalInfusor tileEntityTemporalInfusor = (TileEntityTemporalInfusor) world.getTileEntity(x, y, z);
			return new GUITemporalInfusor(player.inventory, tileEntityTemporalInfusor);
		}
		return null;
	}
}
