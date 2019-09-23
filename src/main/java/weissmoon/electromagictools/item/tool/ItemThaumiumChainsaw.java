package weissmoon.electromagictools.item.tool;

import thaumcraft.api.ThaumcraftMaterials;
import weissmoon.electromagictools.lib.Strings;

/**
 * Created by Weissmoon on 9/23/19.
 */
public class ItemThaumiumChainsaw extends ItemDiamondChainsaw {

    public ItemThaumiumChainsaw() {
        super(ThaumcraftMaterials.TOOLMAT_THAUMIUM, 12, -3.2F, Strings.Items.THAUMIUM_CHAINSAW_NAME);
        this.maxCharge = 100000;
        this.cost = 250;
        this.hitCost = 350;
        this.tier = 2;
        this.transferLimit = 600;
        this.efficiency = 21F;
    }

    @Override
    public int getItemEnchantability() {
        return 8;
    }
}