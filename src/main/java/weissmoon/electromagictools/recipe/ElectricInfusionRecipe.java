package weissmoon.electromagictools.recipe;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.item.armor.electric.ItemArmorNanoSuit;
import ic2.core.item.armor.electric.ItemArmorQuantumSuit;
import ic2.core.util.misc.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import weissmoon.electromagictools.item.ModItems;
import weissmoon.electromagictools.item.armour.googles.ItemNanoGoggles;

import java.util.List;

/**
 * Created by Weissmoon on 9/12/19.
 */
public class ElectricInfusionRecipe extends InfusionRecipe{
    public ElectricInfusionRecipe(String research, Object output, int inst, AspectList aspects, Object centralItem, Object... recipe) {
        super(research, output, inst, aspects, centralItem, recipe);
    }

    @Override
    public Object getRecipeOutput(EntityPlayer player, ItemStack input, List<ItemStack> comps ) {
        ItemStack itemOuput = ((ItemStack)this.recipeOutput).copy();
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(itemOuput);
        if(itemOuput.getItem() instanceof IElectricItem) {
            double totalCharge = 0;
            if(input.getItem() instanceof IElectricItem){
                totalCharge = ElectricItem.manager.getCharge(input);
            }
            for (ItemStack eItem : comps) {
                if (eItem.getItem() instanceof IElectricItem){
                    totalCharge += ElectricItem.manager.getCharge(eItem);
                }
            }
            totalCharge = (totalCharge / 10) * 9;
            ElectricItem.manager.charge(itemOuput, totalCharge, 4, true, false);
        }
        if (input.getItem() == ModItems.electricGoggles){
            for (ItemStack eItem : comps) {
                if (eItem.getItem() instanceof ItemArmorNanoSuit && ((ItemArmorNanoSuit)eItem.getItem()).armorType == EntityEquipmentSlot.HEAD){
                    ItemArmorNanoSuit nanoSuit = (ItemArmorNanoSuit) eItem.getItem();
                    if (nanoSuit.isEUReader(eItem)){
                        nbt.setBoolean("EUReaderUpgrade", true);
                    }
                    if (nanoSuit.isCropAnalyzer(eItem)){
                        nbt.setBoolean("CropUpgrade", true);
                    }
                    if (nanoSuit.isThermometer(eItem)){
                        nbt.setBoolean("ThermometerUpgrade", true);
                    }
                    break;
                }
            }
        }
        if (input.getItem() == ModItems.nanoGoggles){
            for (ItemStack eItem : comps) {
                if (eItem.getItem() instanceof ItemArmorQuantumSuit && ((ItemArmorQuantumSuit)eItem.getItem()).armorType == EntityEquipmentSlot.HEAD){
                    ItemArmorQuantumSuit nanoSuit = (ItemArmorQuantumSuit) eItem.getItem();
                    if (nanoSuit.isEUReader(eItem)){
                        nbt.setBoolean("EUReaderUpgrade", true);
                    }
                    if (nanoSuit.isCropAnalyzer(eItem)){
                        nbt.setBoolean("CropUpgrade", true);
                    }
                    if (nanoSuit.isThermometer(eItem)){
                        nbt.setBoolean("ThermometerUpgrade", true);
                    }
                    break;
                }
            }
            ItemNanoGoggles nanoSuit = (ItemNanoGoggles) input.getItem();
            if (nanoSuit.isEUReader(input)){
                nbt.setBoolean("EUReaderUpgrade", true);
            }
            if (nanoSuit.isCropAnalyzer(input)){
                nbt.setBoolean("CropUpgrade", true);
            }
            if (nanoSuit.isThermometer(input)){
                nbt.setBoolean("ThermometerUpgrade", true);
            }
        }
        return itemOuput;
    }
}
