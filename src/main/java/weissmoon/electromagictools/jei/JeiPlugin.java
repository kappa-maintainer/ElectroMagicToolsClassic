package weissmoon.electromagictools.jei;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.IThaumcraftRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.common.container.ContainerArcaneWorkbench;
import thaumcraft.common.lib.utils.Utils;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.item.ModItems;
import weissmoon.electromagictools.recipe.ElectricInfusionRecipe;

import javax.annotation.Nonnull;
import java.util.*;

@JEIPlugin
public class JeiPlugin implements IModPlugin {

    @Override
    public void register(@Nonnull IModRegistry registry) {
        // Blacklist
        if (ElectroMagicTools.gtcxLoaded)
            registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModItems.diamondChainsaw));

        //thaumcraft jei integration
        registry.addRecipeCatalyst(new ItemStack(BlocksTC.arcaneWorkbench), VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipeCatalyst(new ItemStack(BlocksTC.infernalFurnace), VanillaRecipeCategoryUid.SMELTING);
        registry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerArcaneWorkbench.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 16, 36);

        //Cluster drops
        List<ClusterRecipeCategory.ClusterWrapper> clusterWrappers = new ArrayList<>();
        Utils.specialMiningResult.forEach((key, value) -> {
            ItemStack inputStack = new ItemStack((Item)key.get(0), 1, (int)key.get(1));
            clusterWrappers.add(new ClusterRecipeCategory.ClusterWrapper(inputStack, value));
        });
        registry.addRecipes(clusterWrappers, ClusterRecipeCategory.CLUSTER_CATEGORY_UID);


        //Thaumic JEI integration
        if(ElectroMagicTools.thaumicJEILoaded){
            registry.addRecipeCatalyst(new ItemStack(BlocksTC.thaumatorium), "THAUMCRAFT_CRUCIBLE");
            registry.handleRecipes(ElectricInfusionRecipe.class, ElectricInfusionWrapper::new, "THAUMCRAFT_INFUSION");
            ElectricInfusionWrapper.initInfusionRecipes(registry);
        }
    }

    //Cluster drops
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new ClusterRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    //Hide not Electric infusion wrapper electric infusion recipes
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        if(ElectroMagicTools.thaumicJEILoaded)
            for(ResourceLocation string : ThaumcraftApi.getCraftingRecipes().keySet()){
                IThaumcraftRecipe recipe = ThaumcraftApi.getCraftingRecipes().get(string);
                if(recipe instanceof ElectricInfusionRecipe && ((InfusionRecipe)recipe).getRecipeInput() != null && ((InfusionRecipe)recipe).recipeOutput != null){
                    List wrappers = jeiRuntime.getRecipeRegistry().getRecipeWrappers(jeiRuntime.getRecipeRegistry().getRecipeCategory("THAUMCRAFT_INFUSION"), jeiRuntime.getRecipeRegistry().createFocus(IFocus.Mode.OUTPUT, ((ElectricInfusionRecipe)recipe).recipeOutput));
                    for(Object wrapper: wrappers)
                        if(!(wrapper instanceof ElectricInfusionWrapper))
                            jeiRuntime.getRecipeRegistry().hideRecipe((IRecipeWrapper)wrapper, "THAUMCRAFT_INFUSION");
                }
            }
    }

}
