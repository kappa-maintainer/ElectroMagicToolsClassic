package weissmoon.electromagictools.recipe;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;

import java.util.List;

/**
 * Created by Weissmoon on 9/12/19.
 */
public class ElectricInfusionRecipe extends InfusionRecipe{
    public ElectricInfusionRecipe(String research, Object outputResult, int inst, AspectList aspects2, Object centralItem, Object... recipe) {
        super(research, outputResult, inst, aspects2, centralItem, recipe);
    }

    public Object getRecipeOutput(EntityPlayer player, ItemStack input, List<ItemStack> comps ) {
        ItemStack itemOuput = ((ItemStack)this.recipeOutput).copy();
        if(itemOuput.getItem() instanceof IElectricItem) {
            double totalCharge = 0;
            if(input.getItem() instanceof IElectricItem){
                totalCharge = ElectricItem.manager.getCharge(input);
            }
            for (ItemStack eItem : comps) {
                if (eItem.getItem() instanceof IElectricItem){
                    totalCharge += ElectricItem.manager.getCharge(eItem);
                }
            }
            totalCharge = (totalCharge / 10) * 9;
            ElectricItem.manager.charge(itemOuput, totalCharge, 4, true, false);
        }
        return itemOuput;
    }
}
