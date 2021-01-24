package weissmoon.electromagictools.item.armour.googles;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.lib.Textures;

import javax.annotation.Nullable;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class ItemNanoGoggles extends ItemElectricGoggles {

    public ItemNanoGoggles() {
        super(Strings.Items.NANO_GOGGLES_NAME, ArmorMaterial.IRON);
        maxCharge = 1000000;
        transferLimit = 1600;
        tier = 2;
        energyPerDamage = 5000;
        visDiscount = 6;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return Textures.Armour.NANO_ARMOUR_TEXTURE;
    }

    @Override
    public double getAbsorptionRatio(){
        return 0.9;
    }
}
