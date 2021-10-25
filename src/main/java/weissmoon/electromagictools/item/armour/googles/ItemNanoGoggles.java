package weissmoon.electromagictools.item.armour.googles;

import ic2.api.classic.item.ICropAnalyzer;
import ic2.api.classic.item.IEUReader;
import ic2.api.classic.item.IThermometer;
import ic2.core.platform.registry.Ic2Lang;
import ic2.core.util.misc.StackUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weissmoon.core.utils.NBTHelper;
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.lib.Textures;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static weissmoon.electromagictools.util.ItemHelper.getChargedItem;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class ItemNanoGoggles extends ItemElectricGoggles implements IEUReader, IThermometer, ICropAnalyzer {

    public ItemNanoGoggles() {
        super(Strings.Items.NANO_GOGGLES_NAME, ArmorMaterial.IRON, 100000, 1600, 2, 800, 6);
    }

    public ItemNanoGoggles(String name, ArmorMaterial material, int maxCharge, int transferLimit, int tier, int energyPerDamage, int visDiscount){
        super(name, material, maxCharge, transferLimit, tier, energyPerDamage, visDiscount);
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

    @Override
    public net.minecraftforge.common.IRarity getForgeRarity(ItemStack stack){
        return EnumRarity.UNCOMMON;
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

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> list) {
        if (isInCreativeTab(tab)) {
            ItemStack stack = new ItemStack(this, 1, 0);
            list.add(stack);
            list.add(getChargedItem(this, 1));
            ItemStack full = getChargedItem(this, 1);
            NBTHelper.setBoolean(full, "CropUpgrade", true);
            NBTHelper.setBoolean(full, "EUReaderUpgrade", true);
            NBTHelper.setBoolean(full, "ThermometerUpgrade", true);
            list.add(full);
        }
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
