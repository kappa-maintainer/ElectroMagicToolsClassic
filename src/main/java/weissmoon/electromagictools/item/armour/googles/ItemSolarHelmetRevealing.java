package weissmoon.electromagictools.item.armour.googles;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import weissmoon.core.utils.NBTHelper;
import weissmoon.electromagictools.api.ISolarRequirements;
import weissmoon.electromagictools.api.SolarHelmetRegistry;
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.lib.Textures;

import javax.annotation.Nullable;

/**
 * Created by Weissmoon on 9/6/19.
 */
public class ItemSolarHelmetRevealing extends ItemQuantumGoggles {

    private int genDay, genNight;
    public static final String NBT_INFUSED_SOLAR = "solarPanel";

    public ItemSolarHelmetRevealing(){
        super(Strings.Items.SOLAR_GOGGLES_NAME, ArmorMaterial.IRON, 20000000, 18000, 4, 20000, 7);

        this.genDay = 65536;
        this.genNight = 4096;
        this.setCreativeTab(null);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return Textures.Armour.SOLAR_HELMET_TEXTURE;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack){
        super.onArmorTick(world, player, stack);

        ItemStack solarPanel = getSolar(stack);
        BlockPos playerPos = new BlockPos(player.posX, player.posY + 1, player.posZ);double enerj = 0;
        if(solarPanel.isEmpty()){
            //ISolarRequirements iSolarRequirements = SolarHelmetRegistry.stringRequirements.getObject(null);
            if(world.canSeeSky(playerPos)){
                enerj = (world.isDaytime() ? (double)genDay : (double) genNight) / 12000D;
            }
        }else{
            ISolarRequirements iSolarRequirements = SolarHelmetRegistry.stackRequirements.getObject(stack);
            if(iSolarRequirements.canGenerate(world, playerPos)){
                enerj = iSolarRequirements.getEnergyPerTick(world, new Vec3d(playerPos), solarPanel);
            }
        }

        if(enerj <= 0)
            return;

        for(ItemStack armor:player.getArmorInventoryList()){
            if(armor.getItem() instanceof IElectricItem){
                enerj -= ElectricItem.manager.charge(armor, enerj, 4, false, false);
                if(enerj <= 0)
                    return;
            }
        }

        if(enerj <= 0)
            return;

        for(ItemStack invStack: player.inventory.mainInventory){
            if(invStack.getItem() instanceof IElectricItem){
                enerj -= ElectricItem.manager.charge(invStack, enerj, 4, false, false);
                if(enerj <= 0)
                    return;
            }
        }
    }

    public ItemStack getSolar(ItemStack helmet){
        NBTTagCompound cmp = NBTHelper.getTagCompound(helmet, NBT_INFUSED_SOLAR);
        if(cmp == null)
            return ItemStack.EMPTY;
        return new ItemStack(cmp);
    }

    @Override
    public CreativeTabs[] getCreativeTabs(){
        return new CreativeTabs[]{};
    }

    @Override
    public boolean isCropAnalyzer(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isEUReader(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isThermometer(ItemStack stack) {
        return false;
    }
}
