package fr.altiscraft.benjaminloison.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketGPS implements IMessage
{
    @Override
    public void toBytes(ByteBuf buf)
    {}

    @Override
    public void fromBytes(ByteBuf buf)
    {}

    public static class Handler implements IMessageHandler<PacketGPS, IMessage>
    {
        @Override
        public IMessage onMessage(PacketGPS message, MessageContext ctx)
        {
            return null;
        }
    }
}