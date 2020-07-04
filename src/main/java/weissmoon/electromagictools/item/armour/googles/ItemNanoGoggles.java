package weissmoon.electromagictools.item.armour.googles;

import ic2.api.classic.item.ICropAnalyzer;
import ic2.api.classic.item.IEUReader;
import ic2.api.classic.item.IThermometer;
import ic2.core.platform.registry.Ic2Lang;
import ic2.core.util.misc.StackUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.lib.Textures;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class ItemNanoGoggles extends ItemElectricGoggles implements IEUReader, IThermometer, ICropAnalyzer {

    public ItemNanoGoggles() {
        super(Strings.Items.NANO_GOGGLES_NAME, ArmorMaterial.IRON);
        this.maxCharge = 100000;
        this.transferLimit = 1600;
        this.tier = 2;
        this.energyPerDamage = 800;
        this.visDiscount = 6;
    }

    public ItemNanoGoggles(String name, ArmorMaterial material){
        super(name, material);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        NBTTagCompound nbt = StackUtil.getNbtData(stack);
        if (nbt.hasKey("EUReaderUpgrade") || nbt.hasKey("CropUpgrade") || nbt.hasKey("ThermometerUpgrade")){
            boolean shift = Ic2Lang.isShiftDown(tooltip);
            if (shift){
                if (nbt.hasKey("EUReaderUpgrade")) {
                    tooltip.add(Ic2Lang.upgradeEU.getLocalized());
                }

                if (nbt.hasKey("CropUpgrade")) {
                    tooltip.add(Ic2Lang.upgradeCrop.getLocalized());
                }

                if (nbt.hasKey("ThermometerUpgrade")) {
                    tooltip.add(Ic2Lang.upgradeThermo.getLocalized());
                }
            }
        }
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return Textures.Armour.NANO_ARMOUR_TEXTURE;
    }

    @Override
    public double getAbsorptionRatio(){
        return 0.9;
    }

    @Override
    public boolean isCropAnalyzer(ItemStack stack) {
        return StackUtil.getNbtData(stack).getBoolean("CropUpgrade");
    }

    @Override
    public boolean isEUReader(ItemStack stack) {
        return StackUtil.getNbtData(stack).getBoolean("EUReaderUpgrade");
    }

    @Override
    public boolean isThermometer(ItemStack stack) {
        return StackUtil.getNbtData(stack).getBoolean("ThermometerUpgrade");
    }
}
