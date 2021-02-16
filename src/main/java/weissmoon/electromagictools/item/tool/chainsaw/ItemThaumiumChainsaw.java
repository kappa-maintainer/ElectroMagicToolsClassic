package weissmoon.electromagictools.item.tool.chainsaw;

import thaumcraft.api.ThaumcraftMaterials;
import weissmoon.electromagictools.lib.Strings;

/**
 * Created by Weissmoon on 9/23/19.
 */
public class ItemThaumiumChainsaw extends ItemDiamondChainsaw {

    public ItemThaumiumChainsaw() {
        super(ThaumcraftMaterials.TOOLMAT_THAUMIUM, 12, -3.2F, Strings.Items.THAUMIUM_CHAINSAW_NAME);
        maxCharge = 100000;
        cost = 250;
        hitCost = 350;
        tier = 1;
        transferLimit = 600;
        efficiency = 21F;
    }

    @Override
    public int getItemEnchantability() {
        return 8;
    }
}
