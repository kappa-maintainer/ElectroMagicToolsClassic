package weissmoon.electromagictools.item.armour.wings;

import ic2.api.item.ElectricItem;
import ic2.api.util.Keys;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weissmoon.core.utils.NBTHelper;
import weissmoon.electromagictools.client.WingsModelRenderer;
import weissmoon.electromagictools.item.ModItems;
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.lib.Textures;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Weissmoon on 8/19/20.
 */
public class ItemQuantumWings extends ItemNanoWings{
    public ItemQuantumWings(){
        super(Strings.Items.QUANTUM_WINGS_GAME, ArmorMaterial.IRON);
        fallReduction = 0.2F;
        fallDamage = 0;
        propulsion = 0.33F;
        tier = 3;
        visDiscount = 6;
        maxCharge = 10000000;
        transferLimit = 12000;
        energyPerDamage = 20000;
        energyPerJump = 6;
    }

    protected double getAbsorptionRatio(){
        return 1;
    }

    @Override
    public void tickWings(@Nonnull EntityPlayer player, ItemStack wings, World world){
        byte flap = NBTHelper.getByte(wings, "flap");

        if(!Keys.instance.isJumpKeyDown(player) && flap > 0)
            ElectricItem.manager.use(wings, energyPerJump * flap, player);

        if (ElectricItem.manager.getCharge(wings) > 5) {
            super.tickWings(player, wings, world);
        }
    }

    @Override
    public net.minecraftforge.common.IRarity getForgeRarity(ItemStack stack){
        return EnumRarity.RARE;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type){
        return Textures.Armour.QUANTUM_ARMOUR_TEXTURE;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped original) {
        if(model == null){
            ItemStack s = new ItemStack(ModItems.materials, 1, 16);
            NBTHelper.setInteger(s, "icon", 2);
            model = new WingsModelRenderer(s, 2);
        }
        return model;
    }
}
