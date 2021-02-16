package weissmoon.electromagictools.item.tool.omnitool;

import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftMaterials;
import weissmoon.electromagictools.item.tool.omnitool.ItemDiamondOmniTool;
import weissmoon.electromagictools.lib.Strings;

/**
 * Created by Weissmoon on 9/23/19.
 */
public class ItemThaumiumOmniTool extends ItemDiamondOmniTool {

    public ItemThaumiumOmniTool() {
        super(ThaumcraftMaterials.TOOLMAT_THAUMIUM, Strings.Items.THAUMIUM_OMNITOOL_NAME);
        maxCharge = 200000;
        cost = 300;
        hitCost = 650;
        tier = 2;
        transferLimit = 800;
        efficiency = 21;
        attackDamage = 11;
    }

    @Override
    public int getItemEnchantability() {
        return 8;
    }

    @Override
    public int getExtraSpeed(ItemStack drill) {
        return 5;
    }

    @Override
    public int getExtraEnergyCost(ItemStack drill) {
        return 23;
    }

}
