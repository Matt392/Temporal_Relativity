package com.radex392.temporalrelativity.network;

import com.radex392.temporalrelativity.network.message.MessageTileEntityTR;
import com.radex392.temporalrelativity.network.message.MessageTileTemporalInfusor;
import com.radex392.temporalrelativity.reference.Reference;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.LOWERCASE_MOD_ID);

	public static void init()
	{
		INSTANCE.registerMessage(MessageTileEntityTR.class, MessageTileEntityTR.class, 0, Side.CLIENT);
		INSTANCE.registerMessage(MessageTileTemporalInfusor.class, MessageTileTemporalInfusor.class, 1, Side.CLIENT);
	}
}
