package weissmoon.electromagictools.client;

import baubles.api.render.IRenderBauble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import weissmoon.electromagictools.item.ModItems;

import javax.annotation.Nonnull;

/**
 * Created by Weissmoon on 9/16/20.
 */
public class WingsModelRenderer extends ModelBiped {

    private ItemStack feather;
    private int wing;

    public WingsModelRenderer(ItemStack stack, int wing){
        this.feather = stack;
        this.wing = wing;
    }

    public void render(@Nonnull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if(entity instanceof EntityLivingBase){
            renderL((EntityLivingBase)entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    public void renderB(ItemStack stack, EntityPlayer player, IRenderBauble.RenderType type, float partialTicks){
        GlStateManager.pushMatrix();
//        if(player.isSneaking()) {
//            GL11.glTranslatef(0F, 0.2F, 0F);
//            GL11.glRotatef(90F / (float) Math.PI, 1.0F, 0.0F, 0.0F);
//        }
        GlStateManager.enableLighting();
//        IRenderBauble.Helper.rotateIfSneaking(player);
        renderL(player, 0, 0, player.ticksExisted, 0, 0, 0);
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
    }

    public void renderL(@Nonnull EntityLivingBase entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale){
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);

        //ItemStack chestStack = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0.4, 0.4);
        if (entity.isSneaking())
        {
            GlStateManager.translate(0, 0, 0.25);
        }

        GlStateManager.rotate((float) (this.bipedBody.rotateAngleX * (180 / Math.PI)), 1, 0, 0);
        GlStateManager.rotate((float)-(this.bipedBody.rotateAngleY * (180 / Math.PI)), 0, 1, 0);
        GlStateManager.rotate((float)-(this.bipedBody.rotateAngleZ * (180 / Math.PI)), 0, 0, 1);
        GlStateManager.scale(-1, -1, -1);
        if(wing == 0) {
            GlStateManager.translate(-0.5, 0, 0);
        }else{
            GlStateManager.translate(-0.3, 0, 0.1);
        }
        GlStateManager.rotate(-25, 0, 1, 0);
        GlStateManager.rotate(15, 1, 0, 0);
        Minecraft.getMinecraft().getRenderItem().renderItem(feather, ItemCameraTransforms.TransformType.NONE);
        GlStateManager.rotate(-15, 1, 0, 0);
        GlStateManager.rotate(25, 0, 1, 0);
        if(wing == 0) {
            GlStateManager.translate(1, 0, 0);
        }else{
            GlStateManager.translate(0.6, 0, 0);
        }
        GlStateManager.rotate(25, 0, 1, 0);
        GlStateManager.rotate(15, 1, 0, 0);
        GlStateManager.scale(-1, 1, -1);
        Minecraft.getMinecraft().getRenderItem().renderItem(feather, ItemCameraTransforms.TransformType.NONE);
        GlStateManager.popMatrix();
    }
}
