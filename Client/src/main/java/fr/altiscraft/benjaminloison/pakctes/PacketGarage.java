package fr.altiscraft.benjaminloison.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketGarage implements IMessage
{
    int x, y, z, direction;
    boolean air;

    public PacketGarage()
    {}

    public PacketGarage(int x, int y, int z, int direction, boolean air)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.direction = direction;
        this.air = air;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(direction);
        buf.writeBoolean(air);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {}

    public static class Handler implements IMessageHandler<PacketGarage, IMessage>
    {
        @Override
        public IMessage onMessage(PacketGarage message, MessageContext ctx)
        {
            return null;
        }
    }
}