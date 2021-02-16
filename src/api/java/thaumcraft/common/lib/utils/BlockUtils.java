package thaumcraft.common.lib.utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * Created by Weissmoon on 9/23/19.
 */
public class BlockUtils {
    public static boolean breakFurthestBlock(World world, BlockPos pos, IBlockState block, EntityPlayer player){
        return false;
    }

    public static RayTraceResult getTargetBlock(World world, Entity entity, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, double range) {
        return new RayTraceResult(entity);
    }
}
