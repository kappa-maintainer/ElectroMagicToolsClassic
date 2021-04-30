package weissmoon.electromagictools.recipe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.world.World;
import net.minecraftforge.common.util.RecipeMatcher;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

import java.util.List;

/**
 * Created by Weissmoon on 3/10/21.
 */
public class SpecialDrill extends ElectricInfusionRecipe{

    private boolean isSpecial;

    public SpecialDrill(String research, Object output, int inst, AspectList aspects, Object centralItem, boolean isSpecial, Object... recipe){
        super(research, output, inst, aspects, centralItem, recipe);
        this.isSpecial = isSpecial;
    }

//    @Override
//    public Object getRecipeOutput(EntityPlayer player, ItemStack input, List<ItemStack> comps ) {
//        Object output = super.getRecipeOutput(player, input, comps);
//        if(output instanceof ItemStack){
//            if(((ItemStack)output).getItem() == ModItems.rockBreakerDrill){
//                if(player.getName().toLowerCase().matches("trinsdar")){
//                    ItemStack outputStack = ((ItemStack)output).copy();
//                    ItemStack itemstack = new ItemStack(outputStack.getItem(), 1, outputStack.getItemDamage(), null);
//                    itemstack.setAnimationsToGo(outputStack.getAnimationsToGo());
//                    if (outputStack.getTagCompound() != null) {
//                        itemstack.setTagCompound(outputStack.getTagCompound().copy());
//                    }
//
//                    return itemstack;
//                }
//            }
//        }
//        return output;
//    }

    public boolean matches(List<ItemStack> input, ItemStack central, World world, EntityPlayer player) {
        if (this.getRecipeInput() == null) {
            return false;
        } else if (!ThaumcraftCapabilities.getKnowledge(player).isResearchKnown(this.research)) {
            return false;
        } else if ((this.getRecipeInput() == Ingredient.EMPTY || this.getRecipeInput().apply(central)) && RecipeMatcher.findMatches(input, this.getComponents()) != null){
                return ((player.getName().toLowerCase().matches("trinsdar")) == isSpecial);
        } else {
            return false;
        }
    }
}
