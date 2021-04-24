package fr.altiscraft.benjaminloison.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketNPC implements IMessage
{
    int id, key;
    String value;

    public PacketNPC()
    {}

    public PacketNPC(int id, int key, String value)
    {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public PacketNPC(int id)
    {
        this.id = id;
        key = 0;
        value = "";
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
        buf.writeInt(key);
        ByteBufUtils.writeUTF8String(buf, value);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {}

    public static class Handler implements IMessageHandler<PacketNPC, IMessage>
    {
        @Override
        public IMessage onMessage(PacketNPC message, MessageContext ctx)
        {
            return null;
        }
    }
}