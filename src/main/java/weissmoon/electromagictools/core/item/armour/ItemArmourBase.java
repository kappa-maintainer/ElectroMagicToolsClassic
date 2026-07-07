package weissmoon.electromagictools.core.item.armour;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weissmoon.electromagictools.core.client.render.IIcon;
import weissmoon.electromagictools.core.client.render.IIconRegister;
import weissmoon.electromagictools.core.helper.WeissItemRegistry;
import weissmoon.electromagictools.core.item.IItemWeiss;

/**
 * Base Armour class
 * automatically register: modID, registry name, unlocalized name, into mod ItemRegistry for model registration
 * Created by Weissmoon on 4/5/19.
 */
public class ItemArmourBase extends ItemArmor implements IItemWeiss {

    protected IIcon itemIconWeiss;
    private final String ModId;
    protected final String RegName;

    public ItemArmourBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
        this.ModId = "welectromagic";
        this.RegName = name;
        this.setTranslationKey(this.ModId.toLowerCase() + ":" + this.RegName);
        this.setRegistryName(this.ModId.toLowerCase() + ":" + this.RegName);
        WeissItemRegistry.weissItemRegistry.regItem(this);
    }

    @SideOnly(Side.CLIENT)
    @Override
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
    public IIcon getIcon (ItemStack stack, int pass){
        return this.itemIconWeiss;
    }

}
