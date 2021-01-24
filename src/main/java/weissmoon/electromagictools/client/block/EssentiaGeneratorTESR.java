package weissmoon.electromagictools.client.block;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import weissmoon.electromagictools.block.tile.EssentiaGeneratorBase;

/**
 * Created by Weissmoon on 10/19/20.
 */
public class EssentiaGeneratorTESR extends TileEntitySpecialRenderer<EssentiaGeneratorBase> {

    private static final ResourceLocation GLOW_TEXTURE = new ResourceLocation("welectromagic:textures/blocks/essentiageneratorglow.png");

    public void render(EssentiaGeneratorBase te, double x, double y, double z, float partialTicks, int destroyStage, float alpha){

        if(te.getFade() <= 0)
            return;

        GlStateManager.pushMatrix();
        this.bindTexture(GLOW_TEXTURE);
        GlStateManager.translate(x + .5, y + .25, z);
        GlStateManager.enableRescaleNormal();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        GlStateManager.disableLighting();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680.0F, 0.0F);
        int colour = te.getAspect().getColor();
        float r, g, b;
        r = colour >> 16 & 255;
        g = colour >> 8 & 255;
        b = colour & 255;

        if(te.getFade() < 20){
            float fade = ((float)te.getFade() / 20);
            r = r * fade;
            g = g * fade;
            b = b * fade;
        }

        GlStateManager.color(r / 255, g / 255, b / 255,0.80F);

        GlStateManager.translate(0.5, 0, 0.5);
        int i = 0;
        while(i < 4) {
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(1.005F, 1.005F, 1.005F);

            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
            bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex(0, 1).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex(1, 1).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex(1, 0).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex(0, 0).normal(0.0F, 1.0F, 0.0F).endVertex();
            tessellator.draw();
            GlStateManager.scale(1 / 1.005F, 1 / 1.005F, 1 / 1.005F);
            GlStateManager.translate(0.5,  0, -0.5);
            i++;
        }

        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
//        super.render(entity, x, y, z, entityYaw, partialTicks);
    }


    protected void bindTexture(ResourceLocation location)
    {
        TextureManager texturemanager = this.rendererDispatcher.renderEngine;

        if (texturemanager != null)
        {
            texturemanager.bindTexture(location);
        }
    }
}
