package weissmoon.electromagictools.item.tool.omnitool;

import ic2.api.item.ElectricItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import weissmoon.electromagictools.lib.Strings;

import static weissmoon.electromagictools.util.BlockUtils.breakBlock;

/**
 * Created by Weissmoon on 9/23/19.
 */
public class ItemDiamondOmniTool extends ItemIronOmniTool {

    public ItemDiamondOmniTool() {
        super(ToolMaterial.DIAMOND, Strings.Items.DIAMOND_OMNITOOL_NAME);
        maxCharge = 100000;
        cost = 200;
        hitCost = 250;
        tier = 1;
        transferLimit = 400;
        efficiency = 16;
        attackDamage = 9;
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
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving){
        if(!entityLiving.isSneaking()) {
            for (int a = 1; a < 8; a++) {
                if (worldIn.getBlockState(pos.up(a)).getBlock() == Blocks.GRAVEL ||
                        worldIn.getBlockState(pos.up(a)).getBlock() == Blocks.SAND) {
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(35), stack);
                    if (ElectricItem.manager.canUse(stack, cost)) {
                        IBlockState iblockstate = worldIn.getBlockState(pos.up(a));
                        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(worldIn, pos.up(a), iblockstate, (EntityPlayer) entityLiving);
                        MinecraftForge.EVENT_BUS.post(event);
                        if (!event.isCanceled()){
                            if (breakBlock(worldIn, pos.up(a), (EntityPlayer) entityLiving, stack, event.getExpToDrop())) {
                                ElectricItem.manager.use(stack, cost, entityLiving);
                            }
                        }
                    }
                } else
                    break;
            }
        }
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

    @Override
    public int getItemEnchantability() {
        return 4;
    }

    @Override
    public int getExtraSpeed(ItemStack drill) {
        return 3;
    }

    @Override
    public int getExtraEnergyCost(ItemStack drill) {
        return 16;
    }

}
