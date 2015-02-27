package com.radex392.temporalrelativity.network.message;

import com.radex392.temporalrelativity.tileEntitiy.TileEntityTR;
import com.radex392.temporalrelativity.tileEntitiy.TileEntityTemporalInfusor;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

import java.util.UUID;

public class MessageTileTemporalInfusor implements IMessage, IMessageHandler<MessageTileTemporalInfusor, IMessage>
{
	public int x, y, z;
	public byte orientation, state;
	public String customName;
	public UUID ownerUUID;
	public byte outputStackSize, outputStackMeta;

	public MessageTileTemporalInfusor()
	{
	}

	public MessageTileTemporalInfusor(TileEntityTemporalInfusor tileEntityTemporalInfusor)
	{
		this.x = tileEntityTemporalInfusor.xCoord;
		this.y = tileEntityTemporalInfusor.yCoord;
		this.z = tileEntityTemporalInfusor.zCoord;
		this.orientation = (byte) tileEntityTemporalInfusor.getOrientation().ordinal();
		this.state = (byte) tileEntityTemporalInfusor.getState();
		this.customName = tileEntityTemporalInfusor.getCustomName();
		this.ownerUUID = tileEntityTemporalInfusor.getOwnerUUID();
		this.outputStackSize = tileEntityTemporalInfusor.outputStackSize;
		this.outputStackMeta = tileEntityTemporalInfusor.outputStackMeta;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.orientation = buf.readByte();
		this.state = buf.readByte();
		int customNameLength = buf.readInt();
		this.customName = new String(buf.readBytes(customNameLength).array());
		if (buf.readBoolean())
		{
			this.ownerUUID = new UUID(buf.readLong(), buf.readLong());
		}
		else
		{
			this.ownerUUID = null;
		}
		this.outputStackSize = buf.readByte();
		this.outputStackMeta = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeByte(orientation);
		buf.writeByte(state);
		buf.writeInt(customName.length());
		buf.writeBytes(customName.getBytes());
		if (ownerUUID != null)
		{
			buf.writeBoolean(true);
			buf.writeLong(ownerUUID.getMostSignificantBits());
			buf.writeLong(ownerUUID.getLeastSignificantBits());
		}
		else
		{
			buf.writeBoolean(false);
		}
		buf.writeByte(outputStackSize);
		buf.writeByte(outputStackMeta);
	}

	@Override
	public IMessage onMessage(MessageTileTemporalInfusor message, MessageContext ctx)
	{
		TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

		if (tileEntity instanceof TileEntityTemporalInfusor)
		{
			((TileEntityTR) tileEntity).setOrientation(message.orientation);
			((TileEntityTR) tileEntity).setState(message.state);
			((TileEntityTR) tileEntity).setCustomName(message.customName);
			((TileEntityTR) tileEntity).setOwnerUUID(message.ownerUUID);
			((TileEntityTemporalInfusor) tileEntity).outputStackSize = message.outputStackSize;
			((TileEntityTemporalInfusor) tileEntity).outputStackMeta = message.outputStackMeta;
		}

		return null;
	}

	@Override
	public String toString()
	{
		return String.format("TileEntityTemporalInfusor - x:%s, y:%s, z:%s, orientation:%s, state:%s, customName:%s, ownerUUID:%s, outputStackSize: %s, outputStackMeta: %s", x, y, z, orientation, state, customName, ownerUUID, outputStackSize, outputStackMeta);
	}
}
