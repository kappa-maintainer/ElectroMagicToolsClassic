package weissmoon.electromagictools.item.tool;

import thaumcraft.api.ThaumcraftMaterials;
import weissmoon.electromagictools.lib.Strings;

/**
 * Created by Weissmoon on 9/23/19.
 */
public class ItemThaumiumOmniTool extends ItemDiamondOmniTool{

    public ItemThaumiumOmniTool() {
        super(11, ThaumcraftMaterials.TOOLMAT_THAUMIUM, Strings.Items.THAUMIUM_OMNITOOL_NAME, 200000, 800, 2);
        this.operationEnergyCost = 200;
        this.efficiency = 21;
    }

    @Override
    public int getItemEnchantability() {
        return 8;
    }
}
