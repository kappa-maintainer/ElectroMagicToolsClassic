package weissmoon.electromagictools.item.armour.wings;

import ic2.api.item.IMetalArmor;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftMaterials;
import thaumcraft.api.items.IVisDiscountGear;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.client.WingsModelRenderer;
import weissmoon.electromagictools.item.ModItems;
import weissmoon.electromagictools.lib.Strings;

/**
 * Created by Weissmoon on 8/18/20.
 */
public class ItemThaumiumWings extends ItemFeatherWings implements IVisDiscountGear, IMetalArmor {

    protected int visDiscount;

    public ItemThaumiumWings(){
        super(Strings.Items.THAUMIUM_WINGS_GAME, ThaumcraftMaterials.ARMORMAT_THAUMIUM);
        setMaxDamage(250);
        fallReduction = 0.7F;
        fallDamage = 0.5F;
        propulsion = 0.15F;
        tier = 1;
        visDiscount = 4;
    }

    protected ItemThaumiumWings(String name, ArmorMaterial materialIn) {
        super(name, materialIn);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped original) {
        if(model == null)
            model = new WingsModelRenderer(new ItemStack(ModItems.materials, 1, 9), 1);
        return model;
    }

    @Override
    public int getVisDiscount(ItemStack stack, EntityPlayer player) {
        return visDiscount;
    }

    @Override
    public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player) {
        return true;
    }
}
