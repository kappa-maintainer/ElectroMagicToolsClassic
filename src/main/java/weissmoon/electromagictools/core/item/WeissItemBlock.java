package weissmoon.electromagictools.core.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import weissmoon.electromagictools.core.block.IBlockWeiss;
import weissmoon.electromagictools.core.client.render.IIcon;
import weissmoon.electromagictools.core.client.render.IIconRegister;
import weissmoon.electromagictools.core.helper.WeissItemRegistry;

/**
 * Base item block class
 * automatically register: modID, registry name, unlocalized name, into mod ItemRegistry for model registration
 * Created by Weissmoon on 3/15/19.
 */
public class WeissItemBlock extends ItemBlock implements IItemWeiss {

    private final String ModId;
    protected final String RegName;
    protected IIcon itemIconWeiss;

    public WeissItemBlock(Block block) {
        super(block);
        this.ModId = "welectromagic";
        this.RegName = ((IBlockWeiss)block).getWeissName();
        this.setTranslationKey(this.ModId.toLowerCase() + ":" + this.RegName);
        this.setRegistryName(this.ModId.toLowerCase() + ":" + this.RegName);
        WeissItemRegistry.weissItemRegistry.regItem(this);
    }

    @Override
    public final String getModID() {
        return this.ModId;
    }

    @Override
    public final String getWeissName(){
        return this.RegName;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return this.itemIconWeiss;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIconWeiss = iconRegister.registerIcon(this, getTranslationKey().substring(getTranslationKey().indexOf(".") + 1));
    }
}
