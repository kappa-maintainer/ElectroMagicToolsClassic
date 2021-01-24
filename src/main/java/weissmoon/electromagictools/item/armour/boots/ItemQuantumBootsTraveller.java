package weissmoon.electromagictools.item.armour.boots;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.lib.Textures;

import javax.annotation.Nullable;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class ItemQuantumBootsTraveller extends ItemNanoBootsTraveller {

    public ItemQuantumBootsTraveller(){
        super(Strings.Items.QUANTUM_BOOTS_NAME, ArmorMaterial.IRON);
        maxCharge = 1000000;
        transferLimit = 12000;
        jumpBonus = 0.67F;
        speedBonus = 0.067F;
        tier = 3;
        energyPerDamage = 20000;
        visDiscount = 5;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return Textures.Armour.QUANTUM_ARMOUR_TEXTURE;
    }

    @Override
    protected double getAbsorptionRatio(){
        return 1;
    }
}
