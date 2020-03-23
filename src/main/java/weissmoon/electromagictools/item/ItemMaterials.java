package weissmoon.electromagictools.item;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weissmoon.core.api.client.item.IItemRenderCustom;
import weissmoon.core.api.client.item.IItemRenderer;
import weissmoon.core.client.render.IIcon;
import weissmoon.core.client.render.IIconRegister;
import weissmoon.core.item.WeissItem;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Reference;
import weissmoon.electromagictools.lib.Strings;

import javax.annotation.Nonnull;

/**
 * Created by Weissmoon on 9/17/19.
 */
public class ItemMaterials extends WeissItem implements IItemRenderCustom{


    public ItemMaterials() {
        super(Strings.Items.MATERIALS_NAME);
        setHasSubtypes(true);
        setCreativeTab(ElectroMagicTools.EMTtab);
    }


    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        String sub = "Null";
        try{
            int i = itemstack.getMetadata();
            sub = Strings.Items.Materials[i];
        }catch (ArrayIndexOutOfBoundsException e){
            //sub = sub;
        }
        return "item." + Reference.MOD_ID + ":" + Strings.Items.MATERIALS_NAME + sub;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons (IIconRegister iconRegister){
        this.itemIconWeiss = iconRegister.registerIcon(this, getUnlocalizedName().substring(getUnlocalizedName().indexOf(".") + 1));
        this.itemIconArray = new IIcon[Strings.Items.Materials.length];
        int i = 0;
        for(String nem:Strings.Items.Materials) {
            this.itemIconArray[i] = iconRegister.registerIcon(this, Reference.MOD_ID + ":" + nem);
            i++;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon (ItemStack stack, int pass){
        int i = stack.getMetadata();
        try {
            if (itemIconArray[i] != null)
                return itemIconArray[i];
        }catch (ArrayIndexOutOfBoundsException e){
            //e.printStackTrace();
        }
        return this.itemIconWeiss;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(@Nonnull CreativeTabs creativeTabs, @Nonnull NonNullList<ItemStack> list) {
        if (!this.isInCreativeTab(creativeTabs))
            return;

        list.add(new ItemStack(this, 1, 0));
        list.add(new ItemStack(this, 1, 1));
        list.add(new ItemStack(this, 1, 3));
        list.add(new ItemStack(this, 1, 5));
        //return;

        if (ElectroMagicTools.ic2ceLoaded) {
            for (int i = 12; i <= Strings.Items.Materials.length; i++) {
                list.add(new ItemStack(this, 1, i - 1));
            }
        }
//        }else{
//            for (int i = 1; i <= Strings.Items.Materials.length-4; i++) {
//                list.add(new ItemStack(this, 1, i - 1));
//            }
//        }
    }

    @Override
    public IItemRenderer getIItemRender() {
        return new MaterialRender();
    }

    public class MaterialRender implements IItemRenderer{
        @Override
        public boolean handleRenderType(ItemStack item, ItemCameraTransforms.TransformType cameraTransformType) {
            return false;
        }

        @Override
        public boolean shouldUseRenderHelper(ItemCameraTransforms.TransformType cameraTransformType, ItemStack item, ItemRendererHelper helper) {
            return false;
        }

        @Override
        public void renderItem(ItemCameraTransforms.TransformType cameraTransformType, ItemStack item, Object... data) {

        }
    }
}
