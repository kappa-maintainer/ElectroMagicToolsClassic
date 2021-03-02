package weissmoon.electromagictools.jei;

import ic2.jeiIntigration.SubModul;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import net.minecraft.item.ItemStack;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.item.ModItems;

import javax.annotation.Nonnull;

public class JeiPlugin implements IModPlugin {

    @Override
    public void register(@Nonnull IModRegistry registry) {
        if (SubModul.load) {
            // Blacklist
            IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
            if (ElectroMagicTools.gtcxLoaded){
                blacklist.addIngredientToBlacklist(new ItemStack(ModItems.diamondChainsaw));
            }
        }
    }
}
