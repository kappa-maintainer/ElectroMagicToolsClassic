package weissmoon.electromagictools.recipe;

import net.minecraft.item.ItemStack;

import java.util.Set;

/**
 * Created by Weissmoon on 9/7/19.
 */
public class Ingredient extends net.minecraft.item.crafting.Ingredient {

    public Ingredient(ItemStack... p_i47503_1_){
        super(p_i47503_1_);
    }

    public Ingredient(Set<ItemStack> keys) {
        super(keys.toArray(new ItemStack[]{}));
    }
}
