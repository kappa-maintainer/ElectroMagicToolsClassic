package weissmoon.electromagictools.core.api.item;

import net.minecraft.item.ItemStack;

/**
 * Interface for items with multiple modes
 * Created by Weissmoon on 11/2/16.
 */
public interface IModesCore {

    /**
     * Method called when mode key is pressed for held item in main hand
     */
    void changeToolMode(ItemStack stack);
}
