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
public class ItemNanoBootsTraveller extends ItemElectricBootsTraveller {

    public ItemNanoBootsTraveller(){
        super(Strings.Items.NANO_BOOTS_NAME, ArmorMaterial.IRON);
        this.maxCharge = 100000;
        this.transferLimit = 1600;
        this.jumpBonus = 0.4;
        this.speedBonus = 0.04F;
        this.tier = 3;
        this.energyPerDamage = 5000;
        this.visDiscount = 4;
    }

    ItemNanoBootsTraveller(String name, ArmorMaterial materialIn) {
        super(name, materialIn);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return Textures.Armour.NANO_ARMOUR_TEXTURE;
    }

    @Override
    protected double getAbsorptionRatio() {
        return 0.9;
    }
}
