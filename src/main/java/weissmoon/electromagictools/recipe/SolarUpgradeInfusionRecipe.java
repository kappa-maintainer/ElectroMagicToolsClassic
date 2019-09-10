package weissmoon.electromagictools.recipe;

import ic2.api.item.IC2Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import weissmoon.core.utils.NBTHelper;
import weissmoon.electromagictools.item.armour.googles.ItemSolarHelmetRevealing;

import java.util.List;

/**
 * Created by Weissmoon on 9/7/19.
 */
public class SolarUpgradeInfusionRecipe extends InfusionRecipe {

    public SolarUpgradeInfusionRecipe(String research, Object outputResult, int inst, AspectList aspects2, Object centralItem, Object... recipe) {
        super(research, outputResult, inst, aspects2, centralItem, recipe);
    }

    public Object getRecipeOutput(EntityPlayer player, ItemStack input, List<ItemStack> comps ) {
        ItemStack helmetOuput = input.copy();
        for(ItemStack solar:comps){
            if(!ItemStack.areItemStacksEqual(solar, IC2Items.getItem("crafting", "advanced_circuit"))){
                NBTTagCompound solartag = new NBTTagCompound();
                solar.writeToNBT(solartag);
                NBTHelper.setTagCompound(helmetOuput, ItemSolarHelmetRevealing.NBT_INFUSED_SOLAR , solartag);
                break;
            }
        }
        return helmetOuput;
    }
}
