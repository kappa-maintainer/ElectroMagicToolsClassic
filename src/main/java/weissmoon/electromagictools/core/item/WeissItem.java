package weissmoon.electromagictools.core.item;

import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.*;
import weissmoon.electromagictools.core.client.render.IIcon;
import weissmoon.electromagictools.core.client.render.IIconRegister;
import weissmoon.electromagictools.core.helper.WeissItemRegistry;

/**
 * Base Item class
 * automatically register: modID, registry name, unlocalized name, into mod ItemRegistry for model registration
 */
public abstract class WeissItem extends Item implements IItemWeiss{
    @SideOnly(Side.CLIENT)
    protected IIcon[] itemIconArray;
    @SideOnly(Side.CLIENT)
    protected IIcon itemIconWeiss;
    private final String ModId;
    protected final String RegName;

    public WeissItem(String name){
        super();
        this.ModId = "welectromagic";
        this.RegName = name;
        this.setTranslationKey(this.ModId.toLowerCase() + ":" + this.RegName);
        this.setRegistryName(this.ModId.toLowerCase() + ":" + this.RegName);
        WeissItemRegistry.weissItemRegistry.regItem(this);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons (IIconRegister iconRegister){
        this.itemIconWeiss = iconRegister.registerIcon(this, getTranslationKey().substring(getTranslationKey().indexOf(".") + 1));
    }

    @Override
    public final String getModID() {
        return this.ModId;
    }

    @Override
    public final String getWeissName(){
        return this.RegName;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect (ItemStack stack){
        return false;
    }

//    /**
//     * Use for icon arrays(Invetory item)
//     */
//    @SideOnly(Side.CLIENT)
//    public IIcon getIconIndex (ItemStack stack){
//        return this.getIcon(stack, 0);
//    }

    /**
     * Use for icon arrays(Held item)
     */
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon (ItemStack stack, int pass){
        return this.itemIconWeiss;
    }

//    /**
//     * Use for singular icon
//     */
//    @SideOnly(Side.CLIENT)
//    public IIcon getIconFromDamage (int p_77617_1_){
//        return this.itemIconWeiss;
//    }
}
