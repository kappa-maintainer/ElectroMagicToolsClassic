package weissmoon.electromagictools.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import weissmoon.electromagictools.item.tool.IMusicProxyItem;

/**
 * Created by Weissmoon on 9/18/20.
 */
public class JukeboxNonRecordEventMessage implements IMessage, IMessageHandler<JukeboxNonRecordEventMessage,IMessage> {

    private int x, y, z, id;

    public JukeboxNonRecordEventMessage(){}

    public JukeboxNonRecordEventMessage(int id, BlockPos pos){
        this.id = id;
        x = pos.getX();
        y = pos.getY();
        z = pos.getZ();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        id = buf.readInt();
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(id);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public IMessage onMessage(JukeboxNonRecordEventMessage message, MessageContext ctx){
        try {
            Item item = Item.getItemById(message.id);
            if(item instanceof IMusicProxyItem){
                SoundEvent event = ((IMusicProxyItem)item).getSound();
                Minecraft.getMinecraft().player.world.playRecord(new BlockPos(message.x, message.y, message.z), event);
            }
        }catch (Error ignored){

        }
        return this;
    }
}
