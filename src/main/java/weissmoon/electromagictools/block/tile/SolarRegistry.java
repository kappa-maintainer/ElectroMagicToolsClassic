package weissmoon.electromagictools.block.tile;

import ic2.core.platform.lang.storage.Ic2InfoLang;
import ic2.core.platform.player.PlayerHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weissmoon.electromagictools.api.ISolarRequirements;

import java.util.List;

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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(10.0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(100.0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(100.0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(10.0));
                tooltip.add(getAerTooltip(25.0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(100.0));
                tooltip.add(getAerTooltip(250.0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(1000.0));
                tooltip.add(getAerTooltip(2500.0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(10.0));
                tooltip.add(getTerraTooltip(25.0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(100.0));
                tooltip.add(getTerraTooltip(250.0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(1000.0));
                tooltip.add(getTerraTooltip(2500.0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(30.0));
            }
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


        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(300.0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(3000.0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(getPerditioTooltip(10.0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(getPerditioTooltip(100.0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(getPerditioTooltip(1000.0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(getAquaTooltip(10.0));
                tooltip.add(getTempestasTooltip(25.0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(getAquaTooltip(100.0));
                tooltip.add(getTempestasTooltip(250.0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(getAquaTooltip(1000.0));
                tooltip.add(getTempestasTooltip(2500.0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(10.0));
                tooltip.add(getIgnisTooltip(25.0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(100.0));
                tooltip.add(getIgnisTooltip(250.0));
            }
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

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
            if (handler.hasEUReader()) {
                tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(1000.0));
                tooltip.add(getIgnisTooltip(2500.0));
            }
        }
    };

    @SideOnly(Side.CLIENT)
    private static String getAerTooltip(Object generation){
        String returning = new TextComponentTranslation("tooltip.EMT.solar.aer", TextFormatting.GRAY).getFormattedText()+
                TextFormatting.GRAY.toString()+
                generation+
                new TextComponentTranslation("tooltip.EMT.solar.ending").getFormattedText();
        return returning;
    }

    @SideOnly(Side.CLIENT)
    private static String getTerraTooltip(Object generation){
        String returning = new TextComponentTranslation("tooltip.EMT.solar.terra", TextFormatting.GRAY, generation).getFormattedText()+
                TextFormatting.GRAY.toString()+
                generation+
                new TextComponentTranslation("tooltip.EMT.solar.ending").getFormattedText();
        return returning;
    }

    @SideOnly(Side.CLIENT)
    private static String getPerditioTooltip(Object generation){
        String returning =  new TextComponentTranslation("tooltip.EMT.solar.perditio", TextFormatting.GRAY, generation).getFormattedText()+
                TextFormatting.GRAY.toString()+
                generation+
                new TextComponentTranslation("tooltip.EMT.solar.ending").getFormattedText();
        return returning;
    }

    @SideOnly(Side.CLIENT)
    private static String getAquaTooltip(Object generation){
        String returning = new TextComponentTranslation("tooltip.EMT.solar.aqua", TextFormatting.GRAY, generation).getFormattedText()+
                TextFormatting.GRAY.toString()+
                generation+
                new TextComponentTranslation("tooltip.EMT.solar.ending").getFormattedText();
        return returning;
    }

    @SideOnly(Side.CLIENT)
    private static String getTempestasTooltip(Object generation){
        String returning = new TextComponentTranslation("tooltip.EMT.solar.tempestas", TextFormatting.GRAY, generation).getFormattedText()+
                TextFormatting.GRAY.toString()+
                generation+
                new TextComponentTranslation("tooltip.EMT.solar.ending").getFormattedText();
        return returning;
    }

    @SideOnly(Side.CLIENT)
    private static String getIgnisTooltip(Object generation){
        String returning = new TextComponentTranslation("tooltip.EMT.solar.ignis", TextFormatting.GRAY, generation).getFormattedText()+
                TextFormatting.GRAY.toString()+
                generation+
                new TextComponentTranslation("tooltip.EMT.solar.ending").getFormattedText();
        return returning;
    }
}
