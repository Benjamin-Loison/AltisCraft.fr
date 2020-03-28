package fr.altiscraft.benjaminloison.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketATMRobbery implements IMessage
{
    boolean robbery;
    int x, y, z;

    public PacketATMRobbery()
    {}

    public PacketATMRobbery(boolean robbery, int x, int y, int z)
    {
        this.robbery = robbery;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(robbery);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {}

    public static class Handler implements IMessageHandler<PacketATMRobbery, IMessage>
    {
        @Override
        public IMessage onMessage(PacketATMRobbery m, MessageContext ctx)
        {
            return null;
        }
    }
}