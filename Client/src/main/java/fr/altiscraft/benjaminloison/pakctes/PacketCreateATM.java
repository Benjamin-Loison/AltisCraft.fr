package fr.altiscraft.benjaminloison.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketCreateATM implements IMessage
{
    boolean create;
    int x, y, z;

    public PacketCreateATM()
    {}

    public PacketCreateATM(boolean create, int x, int y, int z)
    {
        this.create = create;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(create);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {}

    public static class Handler implements IMessageHandler<PacketCreateATM, IMessage>
    {
        @Override
        public IMessage onMessage(PacketCreateATM message, MessageContext ctx)
        {
            return null;
        }
    }
}
