package fr.altiscraft.benjaminloison.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketKey implements IMessage
{
    int id;
    String newOwner;

    public PacketKey()
    {}

    public PacketKey(int id, String newOwner)
    {
        this.id = id;
        this.newOwner = newOwner;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
        ByteBufUtils.writeUTF8String(buf, newOwner);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {}

    public static class Handler implements IMessageHandler<PacketKey, IMessage>
    {
        @Override
        public IMessage onMessage(PacketKey message, MessageContext ctx)
        {
            return null;
        }
    }
}