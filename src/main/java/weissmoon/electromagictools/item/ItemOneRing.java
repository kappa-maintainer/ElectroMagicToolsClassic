package weissmoon.electromagictools.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import weissmoon.core.client.render.IIconRegister;
import weissmoon.core.item.WeissItem;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Reference;

import java.util.Map;

import static weissmoon.core.helper.RNGHelper.random;

/**
 * Created by Weissmoon on 9/18/19.
 */
public class ItemOneRing extends WeissItem implements IBauble {

    public static final String CORRUPTION_NBT_TAG = "WEMTMindCorruption";

    public static final Map<String, Integer> playersWithRing = new java.util.HashMap<String, Integer>();

    public ItemOneRing() {
        super("itemOneRing");
        this.setUnlocalizedName(Reference.MOD_ID + ".itemOneRing");
        setMaxStackSize(1);
        setCreativeTab(ElectroMagicTools.EMTtab);
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIconWeiss = iconRegister.registerIcon(this, this.getRegistryName().toString());
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.RING;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        if (!player.isInvisible())
            player.setInvisible(true);

        int corruption = 0;
        NBTTagCompound entityData = player.getEntityData();

        if(entityData.hasKey(CORRUPTION_NBT_TAG)){
            corruption = entityData.getInteger(CORRUPTION_NBT_TAG);
        }else{
            entityData.setInteger(CORRUPTION_NBT_TAG, 0);
        }

        if(playersWithRing.containsKey(player.getName())){
            int cool = playersWithRing.get(player.getName());
            if(cool > 0){
                playersWithRing.put(player.getName(), cool - 1);
            }else{
                ((EntityPlayer) player).capabilities.disableDamage = true;
            }
        }else{
            playersWithRing.put(player.getName(), 0);
        }

        if(!player.world.isRemote){
            if(corruption < 6000 && random.nextInt(2000) == 0) {
                player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("blindness"), 500, 2, false, false));
                playersWithRing.put(player.getName(), 501);
            } else if (corruption >= 6000 && corruption < 24000 && random.nextInt(2000) == 0) {
                player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("nausea"), 500, 2, false, false));
                playersWithRing.put(player.getName(), 501);
            }else if (corruption >= 24000 && corruption < 72000 && random.nextInt(2000) == 0) {
                ((EntityPlayer) player).capabilities.disableDamage = false;
                player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("poison"), 500, 2, false, false));
                playersWithRing.put(player.getName(), 501);
            }else if (corruption >= 72000 && corruption < 120000 && random.nextInt(4000) == 0) {
                ((EntityPlayer) player).capabilities.disableDamage = false;
                player.motionY += 2d;
                playersWithRing.put(player.getName(), 101);
            }else if (corruption >= 120000 && random.nextInt(10000) == 0) {
                ((EntityPlayer) player).capabilities.disableDamage = false;
                player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("wither"), 6000, 4, false, false));
                playersWithRing.put(player.getName(), 5001);
            }
        }
        entityData.setInteger(CORRUPTION_NBT_TAG, corruption + 1);
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        NBTTagCompound entityData = player.getEntityData();
        entityData.setInteger(CORRUPTION_NBT_TAG, 0);
        if(!player.world.isRemote){
            Object r = TextFormatting.DARK_RED.toString();
            String equipt1 = r + new TextComponentTranslation("item.welectromagic.itemOneRing.equipt1", TextFormatting.DARK_PURPLE, r).getUnformattedText();
            player.sendMessage(new TextComponentString(equipt1));
            Object o = (TextFormatting.RED.toString() + TextFormatting.ITALIC.toString());
            Object p = (TextFormatting.DARK_PURPLE.toString() + TextFormatting.ITALIC.toString());
            String equipt2 = o + new TextComponentTranslation("item.welectromagic.itemOneRing.equipt2", p, o).getUnformattedText();
            player.sendMessage(new TextComponentString(equipt2));
        }
        playersWithRing.put(player.getName(), 0);
    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        player.setInvisible(false);
        player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("nausea"), 500, 2, false, false));
        playersWithRing.remove(player.getName());
        NBTTagCompound entityData = player.getEntityData();
        entityData.removeTag(CORRUPTION_NBT_TAG);
        if(!((EntityPlayer) player).capabilities.isCreativeMode)
            ((EntityPlayer) player).capabilities.disableDamage = false;
    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        int corruption;
        NBTTagCompound entityData = player.getEntityData();

        if(entityData.hasKey(CORRUPTION_NBT_TAG)){
            corruption = entityData.getInteger(CORRUPTION_NBT_TAG);
        }else{
            corruption = 601;
        }
        return corruption > 600;
    }
}
