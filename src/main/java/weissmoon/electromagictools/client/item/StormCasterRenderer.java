package weissmoon.electromagictools.client.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import weissmoon.core.api.client.item.IItemRenderer;
import weissmoon.core.helper.RNGHelper;
import weissmoon.electromagictools.item.ModItems;

/**
 * Created by Weissmoon on 9/16/20.
 */
public class StormCasterRenderer implements IItemRenderer {
    ItemStack mjölnir = new ItemStack(ModItems.mjölnir);
    @Override
    public boolean handleRenderType(ItemStack item, ItemCameraTransforms.TransformType cameraTransformType) {
        if(cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND ||
                cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND ||
                cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND ||
                cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND)
            return RNGHelper.getRNGFloat() > 0.95;
        else
            return RNGHelper.getRNGFloat() > 0.15;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemCameraTransforms.TransformType cameraTransformType, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemCameraTransforms.TransformType cameraTransformType, ItemStack item, Object... data) {
        if (cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND ||
                cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND ||
                cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND ||
                cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND)
            return;
        Minecraft.getMinecraft().getRenderItem().renderItem(mjölnir, cameraTransformType);
        GlStateManager.enableBlend();
    }
}
