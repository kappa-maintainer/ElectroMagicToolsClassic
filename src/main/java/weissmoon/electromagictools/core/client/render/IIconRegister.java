package weissmoon.electromagictools.core.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import weissmoon.electromagictools.core.block.IBlockWeiss;
import weissmoon.electromagictools.core.client.render.renderOverride.CustomRenderRegistry;
import weissmoon.electromagictools.core.helper.WeissBlockRegistry;
import weissmoon.electromagictools.core.helper.WeissItemRegistry;
import weissmoon.electromagictools.core.api.client.item.IItemRenderCustom;
import weissmoon.electromagictools.core.item.IItemWeiss;

import java.util.List;

/**
 * Registry class for automatic model registry
 * Created by Weissmoon on 7/28/16.
 */
//@SideOnly(Side.CLIENT)
public class IIconRegister {

    public static final IIconRegister INSTANCE = new IIconRegister();

    private boolean item, block = false;
    //private ItemModelMesher modelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

    public void exnste(){
        this.registerWeissItemIcons();
        this.registerWeissBlockModels();
    }

    private void registerWeissItemIcons() {
        List<Item> list = WeissItemRegistry.weissItemRegistry.getItemList();
        IItemWeiss item ;
        for (Item aList : list) {
            item = (IItemWeiss) aList;
            if (item instanceof IItemRenderCustom) {
                CustomRenderRegistry.registerItemRenderer((Item) item, ((IItemRenderCustom) item).getIItemRender());
            }
            item.registerIcons(INSTANCE);
        }
        this.item = true;
    }

    public IIcon registerIcon(Item item, String resouceLocation){
        if(!(item instanceof IItemWeiss))return null;
        if(resouceLocation.contains(":")){
            resouceLocation = resouceLocation.substring(resouceLocation.indexOf(":") + 1);
        }
        IIcon icon = new IIcon(((IItemWeiss)item).getModID().toLowerCase() + ":" + resouceLocation, "inventory");
        //setCustomResourceLocation overrides IItemRender
        if (!(item instanceof IItemRenderCustom))
            ModelLoader.setCustomModelResourceLocation(item,0, icon);
        ModelLoader.registerItemVariants(item, icon);
        return icon;
    }

    private void registerWeissBlockModels() {
        List<Block> list = WeissBlockRegistry.weissBlockRegistry.getBlockList();
        IBlockWeiss block ;
        for (Block aList : list) {
            block = (IBlockWeiss) aList;
            block.registerBlockIcons(INSTANCE);
        }
        this.block = true;
    }

    public void registerIcon(Block block){
        if(!(block instanceof IBlockWeiss)) return;
        //if(((IBlockWeiss) block).hasItemBlock()) return;
        if (((IBlockWeiss) block).getStateMapper() == null) {
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(((IBlockWeiss) block).getIgnoredProperties()).build());
        }else{
            ModelLoader.setCustomStateMapper(block, ((IBlockWeiss)block).getStateMapper());
        }
    }

    public final boolean initialized(){
        return item && block;
    }
}
