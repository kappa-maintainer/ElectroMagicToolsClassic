package weissmoon.electromagictools.block.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import weissmoon.electromagictools.api.ISolarRequirements;

import static net.minecraftforge.common.BiomeDictionary.Type.NETHER;

/**
 * Created by Weissmoon on 9/7/20.
 */
public class SolarRegistry {
    public static final ISolarRequirements defaultRequirements = new ISolarRequirements() {
        @Override
        public boolean canGenerate(World world, BlockPos blockPos) {
            return false;
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

    public interface IBasicSolar extends ISolarRequirements{
        @Override
        default boolean canGenerate(World world, BlockPos blockPos) {
            return world.canSeeSky(blockPos) && world.isDaytime() && !world.isRainingAt(blockPos);
        }
    }

    public static final ISolarRequirements compressed1 = new IBasicSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 10;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            return 10;
        }
    };
    public static final ISolarRequirements compressed2 = new IBasicSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 100;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            return 100;
        }
    };
    public static final ISolarRequirements compressed3 = new IBasicSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 1000;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            return 1000;
        }
    };


    public static final ISolarRequirements aer1 = new IBasicSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 10;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            return blockPos.getY() > 160 ? 25 : 10;
        }
    };
    public static final ISolarRequirements aer2 = new IBasicSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 100;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            return blockPos.getY() > 160 ? 250 : 100;
        }
    };
    public static final ISolarRequirements aer3 = new IBasicSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 1000;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            return blockPos.getY() > 160 ? 2500 : 1000;
        }
    };

    public static final ISolarRequirements terra1 = new IBasicSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 10;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            return blockPos.getY() < 11 ? 25 : 10;
        }
    };
    public static final ISolarRequirements terra2 = new IBasicSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 100;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            return blockPos.getY() < 11 ? 250 : 100;
        }
    };
    public static final ISolarRequirements terra3 = new IBasicSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 1000;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            return blockPos.getY() < 11 ? 2500 : 1000;
        }
    };

    public static final ISolarRequirements ordo1 = new IBasicSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 10;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            return 30;
        }
    };
    public static final ISolarRequirements ordo2 = new IBasicSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 100;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            return 300;
        }
    };
    public static final ISolarRequirements ordo3 = new IBasicSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 1000;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            return 3000;
        }
    };

    public interface IDarkSolar extends ISolarRequirements{
        @Override
        default boolean canGenerate(World world, BlockPos blockPos) {
            return world.canSeeSky(blockPos) && !world.isDaytime()  && !world.isRainingAt(blockPos);
        }
    }

    public static final ISolarRequirements perditio1 = new IDarkSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 10;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            return 10;
        }
    };
    public static final ISolarRequirements perditio2 = new IDarkSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 100;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            return 100;
        }
    };
    public static final ISolarRequirements perditio3 = new IDarkSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 1000;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            return 1000;
        }
    };

    public interface IWaterSolar extends ISolarRequirements{
        @Override
        default boolean canGenerate(World world, BlockPos blockPos) {
            return world.canSeeSky(blockPos) && world.isRainingAt(blockPos);
        }
    }

    public static final IWaterSolar aqua1 = new IWaterSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 10;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            if(world.isThundering())
                return 25;
            return 10;
        }
    };
    public static final IWaterSolar aqua2 = new IWaterSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 100;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            if(world.isThundering())
                return 250;
            return 100;
        }
    };
    public static final IWaterSolar aqua3 = new IWaterSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 1000;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            if(world.isThundering())
                return 2500;
            return 1000;
        }
    };

    public interface IFireSolar extends ISolarRequirements{
        @Override
        default boolean canGenerate(World world, BlockPos blockPos) {
            if (BiomeDictionary.hasType(world.getBiome(blockPos), NETHER))
                return true;
            return world.canSeeSky(blockPos) && world.isDaytime()  && !world.isRainingAt(blockPos);
        }
    }

    public static final ISolarRequirements ignis1 = new IFireSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 10;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            if (BiomeDictionary.hasType(world.getBiome(blockPos), NETHER))
                return 25;
            return 10;
        }
    };
    public static final ISolarRequirements ignis2 = new IFireSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 100;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            return ignis1.getEnergyPerTick(world, blockPos) * 10;
        }
    };
    public static final ISolarRequirements ignis3 = new IFireSolar() {
        @Override
        public double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack) {
            return 1000;
        }

        @Override
        public double getEnergyPerTick(World world, BlockPos blockPos) {
            return ignis1.getEnergyPerTick(world, blockPos) * 100;
        }
    };

}
