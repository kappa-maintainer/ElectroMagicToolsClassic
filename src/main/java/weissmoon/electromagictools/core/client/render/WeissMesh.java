package weissmoon.electromagictools.core.client.render;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

/**
 * Created by Weissmoon on 12/8/16.
 */
public interface WeissMesh extends ItemMeshDefinition {
    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack);
}
