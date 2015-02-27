package com.radex392.temporalrelativity.network.message;

import com.radex392.temporalrelativity.tileEntitiy.TileEntityTR;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

import java.util.UUID;

public class MessageTileEntityTR implements IMessage, IMessageHandler<MessageTileEntityTR, IMessage>
{
	public int x, y, z;
	public byte orientation, state;
	public String customName;
	public UUID ownerUUID;

	public MessageTileEntityTR()
	{
	}

	public MessageTileEntityTR(TileEntityTR tileEntityTR)
	{
		x = tileEntityTR.xCoord;
		y = tileEntityTR.yCoord;
		z = tileEntityTR.zCoord;
		orientation = (byte) tileEntityTR.getOrientation().ordinal();
		state = (byte) tileEntityTR.getState();
		customName = tileEntityTR.getCustomName();
		ownerUUID = tileEntityTR.getOwnerUUID();
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
	}

	@Override
	public IMessage onMessage(MessageTileEntityTR message, MessageContext ctx)
	{
		TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

		if (tileEntity instanceof TileEntityTR)
		{
			((TileEntityTR) tileEntity).setOrientation(message.orientation);
			((TileEntityTR) tileEntity).setState(message.state);
			((TileEntityTR) tileEntity).setCustomName(message.customName);
			((TileEntityTR) tileEntity).setOwnerUUID(message.ownerUUID);
		}

		return null;
	}

	@Override
	public String toString()
	{
		return String.format("MessageTileEntityTR - x:%s, y:%s, z:%s, orientation:%s, state:%s, customName:%s, ownerUUID:%s", x, y, z, orientation, state, customName, ownerUUID);
	}
}
