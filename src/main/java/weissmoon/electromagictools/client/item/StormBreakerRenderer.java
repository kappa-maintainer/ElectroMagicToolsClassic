package weissmoon.electromagictools.client.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import weissmoon.core.api.client.item.IItemRenderer;
import weissmoon.core.utils.NBTHelper;

/**
 * Created by Weissmoon on 10/1/20.
 */
public class StormBreakerRenderer implements IItemRenderer {
    @Override
    public boolean handleRenderType(ItemStack item, ItemCameraTransforms.TransformType cameraTransformType) {
        if(NBTHelper.getBoolean(item, "throw"))
        return cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND ||
                cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND;
        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemCameraTransforms.TransformType cameraTransformType, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemCameraTransforms.TransformType cameraTransformType, ItemStack item, Object... data) {
        EnumHandSide p_187456_3_;
        float p_187456_2_ = Minecraft.getMinecraft().getRenderPartialTicks();
        ItemRenderer itemRenderer = Minecraft.getMinecraft().getItemRenderer();
        float p_187456_1_ = 1.0F;
        if(cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND)
            p_187456_3_ = EnumHandSide.LEFT;
        else
            p_187456_3_ = EnumHandSide.RIGHT;
        boolean flag = p_187456_3_ != EnumHandSide.LEFT;
        float f = flag ? 1.0F : -1.0F;
        float f1 = MathHelper.sqrt(p_187456_2_);
        float f2 = -0.3F * MathHelper.sin(f1 * (float)Math.PI);
        float f3 = 0.4F * MathHelper.sin(f1 * ((float)Math.PI * 2F));
        float f4 = -0.4F * MathHelper.sin(p_187456_2_ * (float)Math.PI);
//        GlStateManager.translate(f * (f2 + 0.64000005F), f3 + -0.6F + p_187456_1_ * -0.6F, f4 + -0.71999997F);
//        GlStateManager.rotate(f * 45.0F, 0.0F, 1.0F, 0.0F);
        float f5 = MathHelper.sin(p_187456_2_ * p_187456_2_ * (float)Math.PI);
        float f6 = MathHelper.sin(f1 * (float)Math.PI);
//        GlStateManager.rotate(f * f6 * 70.0F, 0.0F, 1.0F, 0.0F);
//        GlStateManager.rotate(f * f5 * -20.0F, 0.0F, 0.0F, 1.0F);
        AbstractClientPlayer abstractclientplayer = Minecraft.getMinecraft().player;
        Minecraft.getMinecraft().getTextureManager().bindTexture(abstractclientplayer.getLocationSkin());
//        GlStateManager.translate(f * -1.0F, 3.6F, 3.5F);
//        GlStateManager.rotate(f * 120.0F, 0.0F, 0.0F, 1.0F);
//        GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
//        GlStateManager.rotate(f * -135.0F, 0.0F, 1.0F, 0.0F);
//        GlStateManager.translate(f * 5.6F, 0.0F, 0.0F);
        RenderPlayer renderplayer = (RenderPlayer)Minecraft.getMinecraft().getRenderManager().<AbstractClientPlayer>getEntityRenderObject(abstractclientplayer);
        GlStateManager.disableCull();

        if (flag)
        {
            renderplayer.renderRightArm(abstractclientplayer);
        }
        else
        {
            renderplayer.renderLeftArm(abstractclientplayer);
        }

        GlStateManager.enableCull();
    }
}
