package weissmoon.electromagictools.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thaumcraft.client.fx.FXDispatcher;
import weissmoon.core.helper.RNGHelper;

/**
 * Created by Weissmoon on 10/21/20.
 */
public class FlameParticleMessage implements IMessage, IMessageHandler<FlameParticleMessage,IMessage> {

    private static int x, y, z;

    public FlameParticleMessage(){

    }
    public FlameParticleMessage(BlockPos pos){
        x = pos.getX();
        y = pos.getY();
        z = pos.getZ();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }


    @Override
    public IMessage onMessage(FlameParticleMessage message, MessageContext ctx) {
//        for(EnumFacing facing: EnumFacing.VALUES){
            int i = 0;
            while(i < 8){
                FXDispatcher.INSTANCE.drawNitorFlames(x + 1, y + RNGHelper.getRNGGaussianR(.5, .5), z + RNGHelper.getRNGGaussianR(.5, .5), RNGHelper.getRNGGaussian() * 0.0025D, RNGHelper.getRNGGaussian() * 0.0025D, RNGHelper.getRNGGaussian() * 0.0025D,2650102, 0);
                FXDispatcher.INSTANCE.drawNitorFlames(x + RNGHelper.getRNGGaussianR(.5, .5), y + RNGHelper.getRNGGaussianR(.5, .5), z + 1, RNGHelper.getRNGGaussian() * 0.0025D, RNGHelper.getRNGGaussian() * 0.0025D, RNGHelper.getRNGGaussian() * 0.0025D,2650102, 0);
                FXDispatcher.INSTANCE.drawNitorFlames(x + RNGHelper.getRNGGaussianR(.5, .5), y + 1, z + RNGHelper.getRNGGaussianR(.5, .5), RNGHelper.getRNGGaussian() * 0.0025D, RNGHelper.getRNGGaussian() * 0.0025D, RNGHelper.getRNGGaussian() * 0.0025D,2650102, 0);
                FXDispatcher.INSTANCE.drawNitorFlames(x + RNGHelper.getRNGGaussianR(.5, .5), y, z + RNGHelper.getRNGGaussianR(.5, .5), RNGHelper.getRNGGaussian() * 0.0025D, RNGHelper.getRNGGaussian() * 0.0025D, RNGHelper.getRNGGaussian() * 0.0025D,2650102, 0);
                FXDispatcher.INSTANCE.drawNitorFlames(x, y + RNGHelper.getRNGGaussianR(.5, .5), z + RNGHelper.getRNGGaussianR(.5, .5), RNGHelper.getRNGGaussian() * 0.0025D, RNGHelper.getRNGGaussian() * 0.0025D, RNGHelper.getRNGGaussian() * 0.0025D,2650102, 0);
                FXDispatcher.INSTANCE.drawNitorFlames(x + RNGHelper.getRNGGaussianR(.5, .5), y + RNGHelper.getRNGGaussianR(.5, .5), z, RNGHelper.getRNGGaussian() * 0.0025D, RNGHelper.getRNGGaussian() * 0.0025D, RNGHelper.getRNGGaussian() * 0.0025D,2650102, 0);
                i++;
            }
//        }
        return null;
    }
}
