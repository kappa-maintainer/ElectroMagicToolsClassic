package weissmoon.electromagictools.jei;

import com.buuz135.thaumicjei.category.InfusionCategory;
import ic2.api.item.IC2Items;
import ic2.api.item.IElectricItem;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.crafting.IThaumcraftRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import weissmoon.electromagictools.recipe.ElectricInfusionRecipe;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Weissmoon on 11/15/21.
 */
public class ElectricInfusionWrapper extends InfusionCategory.InfusionWrapper{
    private final InfusionRecipe recipe;
    private boolean electric;

    public ElectricInfusionWrapper(InfusionRecipe recipe){
        super(recipe);
        this.recipe = recipe;
    }

    public void getIngredients(IIngredients ingredients){
        super.getIngredients(ingredients);
        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
        List<ItemStack> output = outputs.get(0);
        ItemStack outputStack = output.get(0);
        electric = outputStack.getItem() instanceof IElectricItem;
    }

    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY){
        super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
        if(electric)
            minecraft.getRenderItem().renderItemIntoGUI(new ItemStack(IC2Items.getItemAPI().getItem("charging_re_battery"), 1, 27), 30, 10);
    }

    public List<String> getTooltipStrings(int mouseX, int mouseY){
        if(electric && mouseX>29 && mouseX<47 && mouseY>9 && mouseY<25){
            List<String> list = new ArrayList<>();
            list.add(TextFormatting.AQUA+new TextComponentTranslation("tooltip.jei.electricRecipe").getFormattedText());
            return list;
        }
        return super.getTooltipStrings(mouseX, mouseY);
    }

    static void initInfusionRecipes(@Nonnull IModRegistry registry){
        List<InfusionCategory.InfusionWrapper> infusionWrappers = new ArrayList<>();
        for(ResourceLocation string : ThaumcraftApi.getCraftingRecipes().keySet()){
            IThaumcraftRecipe recipe = ThaumcraftApi.getCraftingRecipes().get(string);
            if(recipe instanceof ElectricInfusionRecipe && ((InfusionRecipe)recipe).getRecipeInput() != null && ((InfusionRecipe)recipe).recipeOutput != null){
                infusionWrappers.add(new ElectricInfusionWrapper((InfusionRecipe)recipe));
            }
        }
        registry.addRecipes(infusionWrappers, "THAUMCRAFT_INFUSION");
    }
}