package weissmoon.electromagictools.client.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;
import weissmoon.electromagictools.block.tile.TileIndustrialChargePedestal;
import weissmoon.electromagictools.client.ModelIndustrialVisCharger;
import weissmoon.electromagictools.lib.Reference;

/**
 * Created by Weissmoon on 12/21/20.
 */
public class IndustrialVisChargerTESR extends TileEntitySpecialRenderer<TileIndustrialChargePedestal> {

    private static ModelIndustrialVisCharger model = new ModelIndustrialVisCharger();
    ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, "textures/blocks/blockindustrialvischarger.png");
    double scale = 0.16;

    @Override
    public void render(TileIndustrialChargePedestal te, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
        GlStateManager.pushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        GlStateManager.translate(x + 0.5, y + 0.5, z + 0.5);
//        GlStateManager.scale(scale, scale, scale);
//        GL11.glRotated(22.5, 0, 0, 1);
//        GL11.glTranslated(0, 3, 0);
//        if(te.getCharging() == 0 || te.getCharging() == 30) {
//            model.setPosition(-((te.getCharging()) / 40D));
//        }else{
//            if(te.hasItem()){
//                model.setPosition(-((te.getCharging() - partialTicks) / 40D));
//            }else{
//                model.setPosition(-((te.getCharging() + partialTicks) / 40D));
//            }
//        }
//        GlStateManager.rotate(180, 1, 0, 0);
//        {
//            int i = Minecraft.getMinecraft().world.getCombinedLight(te.getUP_POS(), 0);
//            float f = (float) (i & 65535);
//            float f1 = (float) (i >> 16);
//            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
//        }
//        for(int a = 0; a < 4; a++) {
//            GlStateManager.rotate(90, 0, 1, 0);
//            GlStateManager.rotate(-45, 1, 0, 0);
            model.render(partialTicks);
//            GlStateManager.rotate(45, 1, 0, 0);
//        }
        GlStateManager.popMatrix();
        ItemStackHandler stackHandler = te.getItemStackHandler();
        ItemStack item = stackHandler.getStackInSlot(0);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        if(!item.isEmpty()) {
            GlStateManager.pushMatrix();
            GlStateManager.enableLighting();
            int i = Minecraft.getMinecraft().world.getCombinedLight(te.getPos(), 0);
            float f = (float)(i & 65535);
            float f1 = (float)(i >> 16);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
            GlStateManager.translate(0.5, 1.2, 0.5);
            GlStateManager.scale(1.5, 1.5, 1.5);
            float foating = ((te.getWorld().getWorldTime() + partialTicks) / 58) * (180F / (float)Math.PI);
            float angle = (partialTicks / 35) % 360;
            GlStateManager.rotate(foating, 0, 1, 0);
            Minecraft.getMinecraft().getRenderItem().renderItem(item, ItemCameraTransforms.TransformType.GROUND);
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();
    }

}
