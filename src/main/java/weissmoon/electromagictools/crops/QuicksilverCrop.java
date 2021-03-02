package weissmoon.electromagictools.crops;

import ic2.api.crops.CropCard;
import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.items.ItemsTC;
import weissmoon.core.helper.RNGHelper;
import weissmoon.electromagictools.lib.Reference;

import java.util.List;

/**
 * Created by Weissmoon on 2/5/21.
 */
public class QuicksilverCrop extends CropCard{
    @Override
    public String getId(){
        return "quickislver";
    }

    @Override
    public String getOwner(){
        return Reference.MOD_ID;
    }

    @Override
    public CropProperties getProperties(){
        return new CropProperties(7, 5, 0, 8, 2, 3);
    }

    @Override
    public int getMaxSize(){
        return 5;
    }

    @Override
    public ItemStack getGain(ICropTile crop){
        int gain = crop.getStatGain();
        float chance = RNGHelper.getRNGFloat()*10;
        if(chance > gain + 1){
            return new ItemStack(ItemsTC.quicksilver);
        }
        int resistance = crop.getStatResistance();
        if(chance < gain){
            float amount =((((float)resistance) / 10) + RNGHelper.getRNGFloat()) * 4F;
            return new ItemStack(BlocksTC.shimmerleaf, (int)amount);
        }

        return ItemStack.EMPTY;
    }

    @Override
    public int getSizeAfterHarvest(ICropTile cropTile) {
        return 2;
    }

    @Override
    public int getEmittedLight(ICropTile crop) {
        if(crop.getCurrentSize() == 5)
            return 8;
        else if(crop.getCurrentSize() < 2)
            return 5;
        return 0;
    }

    @Override
    public List<ResourceLocation> getTexturesLocation(){
        return null;
    }

    @Override
    public String getDiscoveredBy(){
        return "Azanor";
    }

    @Override
    public boolean onRightClick(ICropTile cropTile, EntityPlayer player){
        Packet packet = new SPacketSoundEffect(new SoundEvent(new ResourceLocation("thaumcraft:whispers")), SoundCategory.AMBIENT, player.posX, player.posY, player.posZ, 1, 1);
        ((EntityPlayerMP)player).connection.sendPacket(packet);
        return cropTile.performManualHarvest();
    }
}
