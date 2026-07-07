package weissmoon.electromagictools.core.client.render.renderOverride;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;
import weissmoon.electromagictools.core.api.client.item.IItemRenderer;
import weissmoon.electromagictools.core.item.IItemWeiss;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.Collections;
import java.util.List;

import static net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.*;

/**
 * Dummy model used to render IItemRenderers and pass real item model back to vanilla Renderer
 * Created by Weissmoon on 8/19/16.
 */
public class BridgeModel implements IBakedModel{//}, IPerspectiveAwareModel {

    private DummyOverrides dummyOverides;

    public BridgeModel(){
        this.dummyOverides = new DummyOverrides();
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return Collections.emptyList();
    }

    @Override
    public boolean isAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemCameraTransforms getItemCameraTransforms() {
        ItemStack stack = this.dummyOverides.stack;
        IItemWeiss item = (IItemWeiss) stack.getItem();
        return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(
                item.getIcon(stack, MinecraftForgeClient.getRenderPass())).getItemCameraTransforms();
        //return ItemCameraTransforms.DEFAULT;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return this.dummyOverides;
    }

    /**
     * IItemRender rendered here
     * @param cameraTransformType where the model is being rendered
     * @return empty model if IItemRenderer is used, real model with perspective if not used
     */
    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        ItemStack stack = this.dummyOverides.stack;
        IItemRenderer renderer = CustomRenderRegistry.getItemRenderer(stack, cameraTransformType);
        //Should IItemRender be used for this perspective
        if (renderer.handleRenderType(stack, cameraTransformType)) {
            GL11.glPushMatrix();
            try {
                if(cameraTransformType == THIRD_PERSON_LEFT_HAND || cameraTransformType == THIRD_PERSON_RIGHT_HAND) {
                    float f1 = 0;
                    float f13 = 0.8F;
                    GL11.glTranslatef(0.0F, 0.310F, -0.27F);
                    GL11.glRotatef(70.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(-45.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glScaled(0.38,0.38,0.38);
                //No longer needed
                //First person handled by event
//                }else if(cameraTransformType == FIRST_PERSON_RIGHT_HAND || cameraTransformType == FIRST_PERSON_LEFT_HAND){
//                    return Pair.of(this.dummyOverides.getModel(), null);
                }

                //Basic renderer
                    renderer.renderItem(cameraTransformType, stack);
            }finally{
                GL11.glPopMatrix();
            }
            return Pair.of(this.dummyOverides.getModel(), null);
        }else if(stack.getItem() instanceof IItemWeiss) {
            //return ItemModel with perspective transforms if IItemRender is not used
            IItemWeiss item = (IItemWeiss) stack.getItem();
            if ((Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(item.getIcon(stack, MinecraftForgeClient.getRenderPass()))) !=
                    (Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getMissingModel())){
                Pair<? extends IBakedModel,Matrix4f> returnPair = net.minecraftforge.client.ForgeHooksClient.handlePerspective(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(item.getIcon(stack, MinecraftForgeClient.getRenderPass())), cameraTransformType);
                //Forgehooks do not handle perspective properly
                returnPair = returnPair.getLeft().handlePerspective(cameraTransformType);
                return returnPair;
                //return Pair.of(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(item.getIcon(stack, MinecraftForgeClient.getRenderPass()))
                        //.getOverrides().handleItemState(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(item.getIcon(stack, MinecraftForgeClient.getRenderPass())), stack, this.dummyOverides.getWorld(), this.dummyOverides.livingBase), null);
            }
        }
        //return missing model if item is not IItemRender or IItemWeiss
        return Pair.of(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getMissingModel(), null);
    }

    //class used to identify item being rendered
    class DummyOverrides extends ItemOverrideList{

        private ItemStack stack;
        private IBakedModel model;
        private World world;
        private EntityLivingBase livingBase;
        public DummyOverrides() {
            super(ImmutableList.<ItemOverride>of());
        }

        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity)
        {
            this.stack = stack;
            this.model = originalModel;
            this.world = world;
            this.livingBase = entity;
            return originalModel;
        }
        public ItemStack getStack(){
            return stack;
        }
        public IBakedModel getModel(){
            return this.model;
        }
        public World getWorld(){
            return this.world;
        }
        public EntityLivingBase getEntity(){
            return this.livingBase;
        }
    }
}
