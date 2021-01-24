package weissmoon.electromagictools.override;

import ic2.api.item.ElectricItem;
import ic2.api.item.IC2Items;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import weissmoon.core.client.render.renderOverride.WeissOverrideMesh;
import weissmoon.electromagictools.item.ModItems;

/**
 * Created by Weissmoon on 9/25/20.
 */
public class EnergyIconOverride {
    public static void init() {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(IC2Items.getItemAPI().getItem("re_battery"), new WeissOverrideMesh() {
                ModelResourceLocation location0 = new ModelResourceLocation("minecraft:ic2itembatre0#inventory");
                ModelResourceLocation location1 = new ModelResourceLocation("minecraft:ic2itembatre1#inventory");
                ModelResourceLocation location2 = new ModelResourceLocation("minecraft:ic2itembatre2#inventory");
                ModelResourceLocation location3 = new ModelResourceLocation("minecraft:ic2itembatre3#inventory");
                ModelResourceLocation location4 = new ModelResourceLocation("minecraft:ic2itembatre4#inventory");

                @Override
                public ModelResourceLocation getModelLocation(ItemStack stack) {
                    double d = (ElectricItem.manager.getMaxCharge(stack) - ElectricItem.manager.getCharge(stack)) / ElectricItem.manager.getMaxCharge(stack);
                    if (d == 0) {
                        return location0;
                    } else if (d == 1) {
                        return location4;
                    } else if (d < .75) {
                        if (d < .50)
                            return location1;
                        return location2;
                    } else {
                        return location3;
                    }
                }
            });
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(IC2Items.getItemAPI().getItem("lapotron_crystal"), new WeissOverrideMesh() {
                ModelResourceLocation location0 = new ModelResourceLocation("minecraft:ic2itembatlamacrystal0#inventory");
                ModelResourceLocation location1 = new ModelResourceLocation("minecraft:ic2itembatlamacrystal1#inventory");
                ModelResourceLocation location2 = new ModelResourceLocation("minecraft:ic2itembatlamacrystal2#inventory");
                ModelResourceLocation location3 = new ModelResourceLocation("minecraft:ic2itembatlamacrystal3#inventory");
                ModelResourceLocation location4 = new ModelResourceLocation("minecraft:ic2itembatlamacrystal4#inventory");

                @Override
                public ModelResourceLocation getModelLocation(ItemStack stack) {
                    double d = (ElectricItem.manager.getMaxCharge(stack) - ElectricItem.manager.getCharge(stack)) / ElectricItem.manager.getMaxCharge(stack);
                    if (d == 0) {
                        return location0;
                    } else if (d == 1) {
                        return location4;
                    } else if (d < .75) {
                        if (d < .50)
                            return location1;
                        return location2;
                    } else {
                        return location3;
                    }
                }
            });
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(IC2Items.getItemAPI().getItem("energy_crystal"), new WeissOverrideMesh() {
                ModelResourceLocation location0 = new ModelResourceLocation("minecraft:ic2itembatcrystal0#inventory");
                ModelResourceLocation location1 = new ModelResourceLocation("minecraft:ic2itembatcrystal1#inventory");
                ModelResourceLocation location2 = new ModelResourceLocation("minecraft:ic2itembatcrystal2#inventory");
                ModelResourceLocation location3 = new ModelResourceLocation("minecraft:ic2itembatcrystal3#inventory");
                ModelResourceLocation location4 = new ModelResourceLocation("minecraft:ic2itembatcrystal4#inventory");

                @Override
                public ModelResourceLocation getModelLocation(ItemStack stack) {
                    double d = (ElectricItem.manager.getMaxCharge(stack) - ElectricItem.manager.getCharge(stack)) / ElectricItem.manager.getMaxCharge(stack);
                    if (d == 0) {
                        return location0;
                    } else if (d == 1) {
                        return location4;
                    } else if (d < .75) {
                        if (d < .50)
                            return location1;
                        return location2;
                    } else {
                        return location3;
                    }
                }
            });
//            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(IC2Items.getItemAPI().getItem("batpack"), new WeissOverrideMesh() {
//                ModelResourceLocation location0 = new ModelResourceLocation("welectromagic:itemenergypack#inventory");
//
//                @Override
//                public ModelResourceLocation getModelLocation(ItemStack stack) {
//                    return location0;
//                }
//            });
//            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ModItems.gemPack, new WeissOverrideMesh() {
//                ModelResourceLocation location0 = new ModelResourceLocation("minecraft:ic2itemarmorbatpack#inventory");
//
//                @Override
//                public ModelResourceLocation getModelLocation(ItemStack stack) {
//                    return location0;
//                }
//            });
        }
    }
}
