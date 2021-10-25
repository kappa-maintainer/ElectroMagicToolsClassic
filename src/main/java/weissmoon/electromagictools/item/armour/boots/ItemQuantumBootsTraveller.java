package weissmoon.electromagictools.item.armour.boots;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.lib.Textures;

import javax.annotation.Nullable;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class ItemQuantumBootsTraveller extends ItemNanoBootsTraveller {

    public ItemQuantumBootsTraveller(){
        super(Strings.Items.QUANTUM_BOOTS_NAME, ArmorMaterial.IRON, 1000000, 1200, 0.67F, 0.067F, 3, 1000, 5);
        MinecraftForge.EVENT_BUS.register(this);
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

    @Override
    protected double getAbsorptionRatio(){
        return 1;
    }

    public void damageAbsorbed(EntityPlayer player, int damage){
        IC2.achievements.issueStat(player, "quantumArmorDamageTaken", damage);
    }
}
