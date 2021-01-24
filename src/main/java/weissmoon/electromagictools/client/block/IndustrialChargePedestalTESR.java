package weissmoon.electromagictools.client.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.opengl.GL11;
import weissmoon.electromagictools.block.tile.TileIndustrialChargePedestal;

/**
 * Created by Weissmoon on 12/3/20.
 */
public class IndustrialChargePedestalTESR extends TileEntitySpecialRenderer<TileIndustrialChargePedestal> {

    @Override
    public void render(TileIndustrialChargePedestal te, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
        ItemStackHandler stackHandler = te.getItemStackHandler();
        ItemStack item = stackHandler.getStackInSlot(0);
        GL11.glPushMatrix();
        GlStateManager.translate(x, y, z);
        if(!item.isEmpty()) {
            GL11.glPushMatrix();
            GlStateManager.enableLighting();
            int i = Minecraft.getMinecraft().world.getCombinedLight(te.getUP_POS(), 0);
            float f = (float)(i & 65535);
            float f1 = (float)(i >> 16);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
            GlStateManager.translate(0.5, 1.2, 0.5);
            GlStateManager.scale(1.5, 1.5, 1.5);
            float foating = ((te.getWorld().getWorldTime() + partialTicks) / 58) * (180F / (float)Math.PI);
            float angle = (partialTicks / 35) % 360;
            GL11.glRotated(foating, 0, 1, 0);
            Minecraft.getMinecraft().getRenderItem().renderItem(item, ItemCameraTransforms.TransformType.GROUND);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }
}
