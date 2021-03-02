package weissmoon.electromagictools.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.EncoderException;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by Weissmoon on 12/21/20.
 */
public class TileUpdatePacket implements IMessage, IMessageHandler<TileUpdatePacket,IMessage>{

    private BlockPos blockPos;
    private NBTTagCompound nbt;

    public TileUpdatePacket(){
    }

    public TileUpdatePacket(BlockPos blockPosIn, NBTTagCompound compoundIn){
        this.blockPos = blockPosIn;
        this.nbt = compoundIn;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {
            this.nbt = CompressedStreamTools.read(new ByteBufInputStream(buf), new NBTSizeTracker(2097152L));
        } catch (IOException ioexception) {
            throw new EncoderException(ioexception);
        }
        this.blockPos = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        try {
            CompressedStreamTools.write(nbt, new ByteBufOutputStream(buf));
        }catch(IOException ioexception){
            throw new EncoderException(ioexception);
        }
        buf.writeLong(blockPos.toLong());
    }

    @Override
    public IMessage onMessage(TileUpdatePacket message, MessageContext ctx){
        nbt = message.nbt;
        blockPos = message.blockPos;
        if(nbt == null)
            return null;

        Objects.requireNonNull(Minecraft.getMinecraft().world.getTileEntity(blockPos)).handleUpdateTag(nbt);
        return null;
    }
}
