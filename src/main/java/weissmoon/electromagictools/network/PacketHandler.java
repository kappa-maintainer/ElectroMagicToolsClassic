package weissmoon.electromagictools.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import weissmoon.electromagictools.lib.Reference;

/**
 * Created by Weissmoon on 9/18/20.
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

    public static void init () {
        INSTANCE.registerMessage(JukeboxNonRecordEventMessage.class, JukeboxNonRecordEventMessage.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(FlameParticleMessage.class, FlameParticleMessage.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(TileUpdatePacket.class, TileUpdatePacket.class, 2, Side.CLIENT);
    }
}
