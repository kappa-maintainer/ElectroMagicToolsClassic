package weissmoon.electromagictools.item.tool;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IShearable;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Strings;

/**
 * Created by Weissmoon on 9/23/19.
 */
public class ItemDiamondOmniTool extends ItemIronOmniTool {

    public ItemDiamondOmniTool() {
        super(ToolMaterial.DIAMOND, Strings.Items.DIAMOND_OMNITOOL_NAME);
        this.maxCharge = 100000;
        this.cost = 200;
        this.hitCost = 250;
        this.tier = 2;
        this.transferLimit = 400;
        this.efficiency = 16;
        this.attackDamage = 9;
        this.setCreativeTab(ElectroMagicTools.EMTtab);
    }

    public ItemDiamondOmniTool(ToolMaterial material, String name) {
        super(material, name);
    }

    @Override
    public boolean canHarvestBlock(IBlockState block, ItemStack stack) {
        return Items.DIAMOND_AXE.canHarvestBlock(block) ||
                Items.DIAMOND_SHOVEL.canHarvestBlock(block) ||
                Items.DIAMOND_PICKAXE.canHarvestBlock(block) ||
                Items.DIAMOND_SWORD.canHarvestBlock(block) ||
                block.getBlock() instanceof IShearable;
    }

    @Override
    public int getItemEnchantability() {
        return 4;
    }
}
