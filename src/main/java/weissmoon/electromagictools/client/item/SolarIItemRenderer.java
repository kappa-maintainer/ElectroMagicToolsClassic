package weissmoon.electromagictools.client.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import weissmoon.core.api.client.item.IItemRenderer;
import weissmoon.core.client.render.WeissRenderItem;
import weissmoon.core.proxy.ClientProxy;
import weissmoon.electromagictools.lib.Reference;

/**
 * Created by Weissmoon on 9/15/20.
 */
public class SolarIItemRenderer implements IItemRenderer {
    @Override
    public boolean handleRenderType(ItemStack item, ItemCameraTransforms.TransformType cameraTransformType) {
        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemCameraTransforms.TransformType cameraTransformType, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemCameraTransforms.TransformType cameraTransformType, ItemStack item, Object... data) {
        //ClientProxy.weissRenderItem.renderModel(
                Minecraft.getMinecraft().getRenderItem().renderItem(item, Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(new ModelResourceLocation(
                        "welectromagic:blocksolarpanel#compressed=false,element=0"))
//                        -1,
//                new ItemStack(Blocks.MELON_BLOCK));
//                null
                );
    }
}
