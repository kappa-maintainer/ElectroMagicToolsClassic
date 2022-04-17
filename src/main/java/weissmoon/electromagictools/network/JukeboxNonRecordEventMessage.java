package weissmoon.electromagictools.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import weissmoon.electromagictools.item.ModItems;

/**
 * Created by Weissmoon on 9/18/20.
 */
public class JukeboxNonRecordEventMessage implements IMessage, IMessageHandler<JukeboxNonRecordEventMessage,IMessage> {

    private byte keyPress;
    private int x, y, z;

    public JukeboxNonRecordEventMessage(){}

    public JukeboxNonRecordEventMessage(byte val, BlockPos pos){
        keyPress = val;
        x = pos.getX();
        y = pos.getY();
        z = pos.getZ();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        keyPress = buf.readByte();
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(keyPress);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public IMessage onMessage(JukeboxNonRecordEventMessage message, MessageContext ctx){
        try {
            SoundEvent event = null;
            if(message.keyPress == 0)
                event = ModItems.stormCaster.getSound();
            else if(message.keyPress == 1)
                event = ModItems.mjölnir.getSound();
            else if(message.keyPress == 2)
                event = ModItems.stormBreaker.getSound();

            Minecraft.getMinecraft().player.world.playRecord(new BlockPos(message.x, message.y, message.z), event);
        }catch (Error ignored){

        }
        return this;
    }
}
