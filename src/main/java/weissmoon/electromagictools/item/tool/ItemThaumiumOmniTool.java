package weissmoon.electromagictools.item.tool;

import thaumcraft.api.ThaumcraftMaterials;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Strings;

/**
 * Created by Weissmoon on 9/23/19.
 */
public class ItemThaumiumOmniTool extends ItemDiamondOmniTool{

    public ItemThaumiumOmniTool() {
        super(ThaumcraftMaterials.TOOLMAT_THAUMIUM, Strings.Items.THAUMIUM_OMNITOOL_NAME);
        this.maxCharge = 200000;
        this.cost = 300;
        this.hitCost = 650;
        this.tier = 2;
        this.transferLimit = 800;
        this.efficiency = 21;
        this.attackDamage = 11;
        this.setCreativeTab(ElectroMagicTools.EMTtab);
    }

    @Override
    public int getItemEnchantability() {
        return 8;
    }
}
