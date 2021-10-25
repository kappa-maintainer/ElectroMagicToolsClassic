package weissmoon.electromagictools.api;

import ic2.api.item.IC2Items;
import ic2.core.platform.lang.storage.Ic2InfoLang;
import ic2.core.platform.player.PlayerHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn){}
    };

    public static final RegistryDefaultedLocked<String, ISolarRequirements> stringRequirements = new RegistryDefaultedLocked<String, ISolarRequirements>(defaultRequirements);
    public static final RegistryDefaultedLocked<ItemStack, ISolarRequirements> stackRequirements = new RegistryDefaultedLocked<ItemStack, ISolarRequirements>(defaultRequirements);

    static{
        ISolarRequirements solarPanel = new ISolarRequirements() {
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

            @SideOnly(Side.CLIENT)
            public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
                PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
                if (handler.hasEUReader()) {
                    tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(1));
                }
            }
        };
        stringRequirements.putObjectB("ic2:blockgenerator", solarPanel);
        stackRequirements.putObjectB(IC2Items.getItem("te","solar_generator"), solarPanel);
    }
}
