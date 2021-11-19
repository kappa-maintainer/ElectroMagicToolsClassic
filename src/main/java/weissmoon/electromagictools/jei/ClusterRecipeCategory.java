package weissmoon.electromagictools.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.items.ItemsTC;

/**
 * Created by Weissmoon on 11/17/21.
 */
public class ClusterRecipeCategory implements IRecipeCategory<ClusterRecipeCategory.ClusterWrapper>{

    public static final String CLUSTER_CATEGORY_UID = "thaumcraft.clusters";
    public IGuiHelper helper;
    private IDrawable background;
    private  IDrawable icon;

    public ClusterRecipeCategory(IGuiHelper iGuiHelper){
        this.helper = iGuiHelper;
        background = helper.drawableBuilder(new ResourceLocation("welectromagic", "textures/gui/cluster_jei.png"), 0, 0, 76, 26).addPadding(0, 0, 0, 30).build();
        icon = helper.createDrawableIngredient(new ItemStack(ItemsTC.clusters));
    }

    @Override
    public String getUid(){
        return CLUSTER_CATEGORY_UID;
    }

    @Override
    public String getTitle(){
        return "Clusters";
    }

    @Override
    public String getModName(){
        return "Thaumcraft";
    }

    @Override
    public  IDrawable getIcon() {
        return icon;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {

    }

    @Override
    public IDrawable getBackground(){
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, ClusterWrapper iRecipeWrapper, IIngredients iIngredients){
        IGuiItemStackGroup guiItemStacks = iRecipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 20, 4);
        guiItemStacks.init(1, false, 68, 4);
        guiItemStacks.set(0, iRecipeWrapper.input);
        guiItemStacks.set(1, iRecipeWrapper.output);
    }

    public static class ClusterWrapper implements IRecipeWrapper{

        private ItemStack input, output;

        public ClusterWrapper(ItemStack input, ItemStack output){
            this.input = input;
            this.output = output;
        }

        @Override
        public void getIngredients(IIngredients iIngredients){
            iIngredients.setInput(VanillaTypes.ITEM, input);
            iIngredients.setOutput(VanillaTypes.ITEM, output);
        }
    }
}
