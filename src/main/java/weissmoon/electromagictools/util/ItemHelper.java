package weissmoon.electromagictools.util;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Weissmoon on 9/7/19.
 */
public class ItemHelper {

    public static ItemStack getChargedItem(IElectricItem item, int method){
        try {
            ItemStack itemStack = new ItemStack((Item) item);
            ElectricItem.manager.charge(itemStack, item.getMaxCharge(itemStack), 4, true, false);
            return itemStack;
        }catch(Error error){
            error.printStackTrace();
            return ItemStack.EMPTY;
        }
    }

    public static double getElectricDurability(ItemStack stack ) {
        if(!(stack.getItem() instanceof IElectricItem))
            return 0;
        double maxCharge = ((IElectricItem)stack.getItem()).getMaxCharge(stack);
        return (maxCharge - ElectricItem.manager.getCharge(stack)) / (float)maxCharge;
    }
}
