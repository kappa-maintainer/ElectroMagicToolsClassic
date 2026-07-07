package weissmoon.electromagictools.core.item.tools;

import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import weissmoon.electromagictools.core.client.render.IIcon;
import weissmoon.electromagictools.core.client.render.IIconRegister;
import weissmoon.electromagictools.core.helper.WeissItemRegistry;
import weissmoon.electromagictools.core.item.IItemWeiss;

/**
 * Base ItemPickaxe class
 * automatically register: modID, registry name, unlocalized name, into mod ItemRegistry for model registration
 * Created by Weissmoon on 8/17/19.
 */
public class WeissItemsPickaxe extends ItemPickaxe implements IItemWeiss{

    protected IIcon itemIconWeiss;
    private final String ModId;
    protected final String RegName;

    public WeissItemsPickaxe(ToolMaterial material, String name) {
        super(material);
        this.ModId = "welectromagic";
        this.RegName = name;
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
