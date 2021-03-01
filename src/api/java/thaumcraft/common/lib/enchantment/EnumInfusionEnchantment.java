package thaumcraft.common.lib.enchantment;

import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Weissmoon on 9/11/20.
 */
public enum EnumInfusionEnchantment {

    COLLECTOR(),
    BURROWING(),
    REFINING(),
    SOUNDING();
    private EnumInfusionEnchantment(){

    }

    public static void addInfusionEnchantment(ItemStack stack, EnumInfusionEnchantment ie, int level) {

    }

    public static List<EnumInfusionEnchantment> getInfusionEnchantments(ItemStack heldItem){
        return null;
    }

    public static int getInfusionEnchantmentLevel(ItemStack heldItem, EnumInfusionEnchantment sounding){
        return 0;
    }
}
