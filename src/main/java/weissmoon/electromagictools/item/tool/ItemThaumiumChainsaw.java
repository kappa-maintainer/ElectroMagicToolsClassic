package weissmoon.electromagictools.item.tool;

import thaumcraft.api.ThaumcraftMaterials;
import weissmoon.electromagictools.lib.Strings;

/**
 * Created by Weissmoon on 9/23/19.
 */
public class ItemThaumiumChainsaw extends ItemDiamondChainsaw {

    public ItemThaumiumChainsaw() {
        super(ThaumcraftMaterials.TOOLMAT_THAUMIUM, 17, 0F, Strings.Items.THAUMIUM_CHAINSAW_NAME, 100000, 600, 2);
        this.operationEnergyCost = 250;
        this.efficiency = 21F;
    }

    @Override
    public int getItemEnchantability() {
        return 8;
    }
}
