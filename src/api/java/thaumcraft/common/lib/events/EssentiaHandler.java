package thaumcraft.common.lib.events;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import thaumcraft.api.aspects.Aspect;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Weissmoon on 10/4/20.
 */
public class EssentiaHandler {

    public static ConcurrentHashMap<String, EssentiaHandler.EssentiaSourceFX> sourceFX = new ConcurrentHashMap();

    public static boolean drainEssentia(TileEntity tile, Aspect aspect, EnumFacing direction, int range, boolean ignoreMirror, int ext) {
        return false;
    }

    public static boolean findEssentia(TileEntity tile, Aspect aspect, EnumFacing direction, int range, boolean ignoreMirror) {
        return false;
    }

    public static class EssentiaSourceFX{
        public EssentiaSourceFX(BlockPos a,BlockPos b, int c, int d){
        }
    }
}
