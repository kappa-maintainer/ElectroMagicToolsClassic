package weissmoon.electromagictools.core.client.render.renderOverride;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import weissmoon.electromagictools.core.client.render.WeissMesh;

/**
 * Dummy mesh Override for items using IItemRenderer
 * Created by Weissmoon on 9/27/16.
 */
public class WeissOverrideMesh implements WeissMesh {

    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack) {
        return new ModelResourceLocation("welectromagic:renderOverride", "inventory");
    }
}
