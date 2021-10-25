package weissmoon.electromagictools.api;

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
public interface ISolarRequirements {

    boolean canGenerate(World world, BlockPos blockPos);

    double getEnergyPerTick(World world, Vec3d vec3d, ItemStack stack);
    double getEnergyPerTick(World world, BlockPos blockPos);


    @SideOnly(Side.CLIENT)
    void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn);
//    {
//        PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
//        if (handler.hasEUReader()) {
//            tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(new Object[]{this.getProduction(stack.getMetadata())}));
//        }
//    }
}
