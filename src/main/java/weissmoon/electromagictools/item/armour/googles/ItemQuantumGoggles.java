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
public class ItemQuantumGoggles extends ItemElectricGoggles {

    public ItemQuantumGoggles() {
        super(Strings.Items.QUANTUM_GOGGLES_NAME, ArmorMaterial.IRON);
        this.maxCharge = 10000000;
        this.transferLimit = 12000;
        this.tier = 4;
        this.energyPerDamage = 20000;
        this.visDiscount = 8;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return Textures.Armour.QUANTUM_GOOGLES_TEXTURE;
    }

    @Override
    public double getAbsorptionRatio(){
        return 1;
    }
}
