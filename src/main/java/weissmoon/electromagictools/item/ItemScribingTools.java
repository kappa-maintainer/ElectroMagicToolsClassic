package weissmoon.electromagictools.item;

import ic2.api.classic.item.IDamagelessElectricItem;
import ic2.api.item.ElectricItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.items.IScribeTools;
import weissmoon.core.client.render.IIconRegister;
import weissmoon.core.item.WeissItem;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Strings;

import javax.annotation.Nonnull;

import static weissmoon.electromagictools.util.ItemHelper.getChargedItem;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class ItemScribingTools extends WeissItem implements IScribeTools, IDamagelessElectricItem{
    public ItemScribingTools() {
        super(Strings.Items.SCRIBING_TOOLS_NAME);
        this.setUnlocalizedName(getModID().toLowerCase() + "." + Strings.Items.SCRIBING_TOOLS_NAME);
        setMaxDamage(0);
        setNoRepair();
        setMaxStackSize(1);
        setCreativeTab(ElectroMagicTools.EMTtab);
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return false;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
<<<<<<< HEAD
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1.0D - ElectricItem.manager.getCharge(stack) / this.getMaxCharge(stack);
=======
    public double getDurabilityForDisplay(ItemStack stack){
        return getDamage(stack) / 400D;
>>>>>>> master
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> list) {
        if (isInCreativeTab(tab)){
            ItemStack stack = new ItemStack(this, 1, 0);
            list.add(stack);
            list.add(getChargedItem(this, 1));
        }
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    @Override
    public double getMaxCharge(ItemStack stack) {
        return 400;
    }

    @Override
    public int getTier(ItemStack stack) {
        return 1;
    }

    @Override
    public double getTransferLimit(ItemStack stack) {
        return 5;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIconWeiss = iconRegister.registerIcon(this, this.getRegistryName().toString());
    }
}
