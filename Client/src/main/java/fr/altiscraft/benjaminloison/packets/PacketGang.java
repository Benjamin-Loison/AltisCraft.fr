package fr.altiscraft.benjaminloison.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketGang implements IMessage
{
    public PacketGang()
    {}

    @Override
    public void fromBytes(ByteBuf buf)
    {}

    @Override
    public void toBytes(ByteBuf buf)
    {}

    public static class Handler implements IMessageHandler<PacketGang, IMessage>
    {
        @Override
        public IMessage onMessage(PacketGang message, MessageContext ctx)
        {
            return null;
        }
    }
}