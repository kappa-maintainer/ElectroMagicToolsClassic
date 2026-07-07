package weissmoon.electromagictools.core.client.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Public RenderItem methods
 * TODO note differences
 * Created by Weissmoon on 10/10/16.
 */
public class WeissRenderItem {

    private static final ItemColors itemColors = Minecraft.getMinecraft().getItemColors();

    public void renderModel(IBakedModel model, int color, @Nullable ItemStack stack)
    {
        GlStateManager.pushMatrix();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);

        GlStateManager.color((color >> 16&0xFF) / 255.0F,
                (color >> 8&0xFF) / 255.0F,
                (color&0xFF) / 255.0F);
        //color = -1;
        for (EnumFacing enumfacing : EnumFacing.values())
        {
            this.renderQuads(vertexbuffer, model.getQuads((IBlockState)null, enumfacing, 0L), color, stack);
        }

        this.renderQuads(vertexbuffer, model.getQuads((IBlockState)null, (EnumFacing)null, 0L), color, stack);
        tessellator.draw();
        if (stack != null && stack.hasEffect())
        {
            //this.renderEffect(model);
        }
        GlStateManager.popMatrix();
    }

    private void renderQuads(BufferBuilder renderer, List<BakedQuad> quads, int color, @Nullable ItemStack stack)
    {
        boolean flag = color == -1 && stack != null;
        int i = 0;

        for (int j = quads.size(); i < j; ++i)
        {
            BakedQuad bakedquad = (BakedQuad)quads.get(i);
            int k = color;

            if (flag && bakedquad.hasTintIndex())
            {
                k = Minecraft.getMinecraft().getItemColors().colorMultiplier(stack, bakedquad.getTintIndex());

                if (EntityRenderer.anaglyphEnable)
                {
                    k = TextureUtil.anaglyphColor(k);
                }

                k = k | -16777216;
            }

            net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor(renderer, bakedquad, k);
            net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColorSlow(renderer, bakedquad, k);
        }
    }

}
