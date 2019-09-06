package weissmoon.electromagictools.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Created by Weissmoon on 9/6/19.
 */
public interface ISolarRequirements {

    boolean canGenerate(World world, BlockPos blockPos);

    double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack);
    double getEnergyPerTick(World world, BlockPos blockPos);
}
