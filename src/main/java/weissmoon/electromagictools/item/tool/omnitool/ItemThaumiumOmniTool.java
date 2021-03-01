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
        super(11, ThaumcraftMaterials.TOOLMAT_THAUMIUM, Strings.Items.THAUMIUM_OMNITOOL_NAME, 200000, 800, 2);
        this.operationEnergyCost = 300;
        this.efficiency = 21;
    }

    @Override
    public int getItemEnchantability() {
        return 8;
    }

    @Override
    public int getExtraSpeed(ItemStack drill) {
        return 2 + super.getExtraSpeed(drill);
    }

    @Override
    public int getExtraEnergyCost(ItemStack drill) {
        return 7 + super.getExtraEnergyCost(drill);
    }

}
