package weissmoon.electromagictools.item.tool.chainsaw;

import thaumcraft.api.ThaumcraftMaterials;
import weissmoon.electromagictools.lib.Strings;

/**
 * Created by Weissmoon on 9/23/19.
 */
public class ItemThaumiumChainsaw extends ItemDiamondChainsaw {

    public ItemThaumiumChainsaw() {
        super(ThaumcraftMaterials.TOOLMAT_THAUMIUM, 16, 0.0F, Strings.Items.THAUMIUM_CHAINSAW_NAME, 100000, 250, 600, 1);
        efficiency = 21F;
    }

    @Override
    public int getItemEnchantability() {
        return 8;
    }
}
