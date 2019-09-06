package weissmoon.electromagictools.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Created by Weissmoon on 9/6/19.
 */
public class SolarHelmetRegistry {

    private static final ISolarRequirements defaultRequirements = new ISolarRequirements() {
        @Override
        public boolean canGenerate(World world, BlockPos blockPos) {
            return world.canSeeSky(blockPos) && world.isDaytime() && world.isRainingAt(blockPos);
        }

        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 0;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            return 0;
        }
    };

    public static final RegistryDefaultedLocked<String, ISolarRequirements> requirements = new RegistryDefaultedLocked<String, ISolarRequirements>(defaultRequirements);

    static{
        requirements.putObject("ic2:blockgenerator", new ISolarRequirements() {
            @Override
            public boolean canGenerate(World world, BlockPos blockPos) {
                return world.canSeeSky(blockPos) && world.isDaytime() && world.isRainingAt(blockPos);
            }

            @Override
            public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
                return 1;
            }

            @Override
            public double getEnergyPerTick(World world, BlockPos blockPos) {
                return 1;
            }
        });
    }
}
