package weissmoon.electromagictools.item;

import ic2.api.classic.item.IDamagelessElectricItem;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.item.ItemStack;
import thaumcraft.api.items.IScribeTools;
import weissmoon.core.item.WeissItem;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Strings;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class ItemScribingTools extends WeissItem implements IScribeTools, IElectricItem, IDamagelessElectricItem{
    public ItemScribingTools() {
        super(Strings.Items.SCRIBING_TOOLS_NAME);
        setMaxDamage(400);
        setNoRepair();
        setMaxStackSize(1);
        setCreativeTab(ElectroMagicTools.EMTtab);
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return false;
    }

    @Override
    public void setDamage(ItemStack stack, int damage){
        //ElectricItem.manager.discharge(stack, 10, 0, true, false, false);
        ElectricItem.manager.use(stack, 10, null);
    }

    @Override
    public int getDamage(ItemStack stack){
        return 400 - (int)ElectricItem.manager.getCharge(stack);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        double durability = this.getDurabilityForDisplay(stack);
        return durability != 0D;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack){
        return this.getDamage(stack) / 400D;
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
}
