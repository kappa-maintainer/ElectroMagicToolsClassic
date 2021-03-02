package weissmoon.electromagictools.item.tool;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import weissmoon.core.item.WeissItem;
import weissmoon.core.utils.NBTHelper;
import weissmoon.electromagictools.lib.Strings;

/**
 * Created by Weissmoon on 11/17/20.
 */
public class ItemHitBauble extends WeissItem implements IBauble {
    public ItemHitBauble() {
        super(Strings.Items.HIT_BAUBLE);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.AMULET;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        int charge = NBTHelper.getInt(itemstack, "charge");
        if(charge < 200){
            NBTHelper.setInteger(itemstack, "charge", charge + 1);
        }
    }
}
