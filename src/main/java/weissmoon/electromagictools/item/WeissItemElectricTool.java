package weissmoon.electromagictools.item;

import ic2.core.item.base.ItemElectricTool;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import weissmoon.core.client.render.IIcon;
import weissmoon.core.client.render.IIconRegister;
import weissmoon.core.helper.WeissItemRegistry;
import weissmoon.core.item.IItemWeiss;
import weissmoon.electromagictools.lib.Reference;

public abstract class WeissItemElectricTool extends ItemElectricTool implements IItemWeiss {
    private final String ModId;
    protected IIcon itemIconWeiss;
    protected final String regName;
    public WeissItemElectricTool(float attackDamageIn, float attackSpeedIn, ToolMaterial material, String name) {
        super(attackDamageIn, attackSpeedIn, material);
        this.ModId = Loader.instance().activeModContainer().getModId();
        this.regName = name;
        this.setUnlocalizedName(this.ModId.toLowerCase() + ":" + this.regName);
        this.setRegistryName(this.ModId.toLowerCase() + ":" + this.regName);
        WeissItemRegistry.weissItemRegistry.regItem(this);
    }

    @Override
    public String getModID() {
        return this.ModId;
    }

    @Override
    public String getWeissName() {
        return regName;
    }

    @Override
    public IIcon getIcon(ItemStack itemStack, int i) {
        return itemIconWeiss;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIconWeiss = iconRegister.registerIcon(this, this.getRegistryName().toString());
    }
}
