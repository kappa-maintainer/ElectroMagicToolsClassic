package weissmoon.electromagictools.event;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import thaumcraft.api.casters.ICasterTriggerManager;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.common.blocks.devices.BlockPedestal;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.tiles.crafting.TileInfusionMatrix;
import thaumcraft.common.tiles.crafting.TilePedestal;
import weissmoon.electromagictools.item.ModItems;

import java.util.ArrayList;

/**
 * Created by Weissmoon on 11/17/20.
 */
public class WWMTCastTriggerManager implements ICasterTriggerManager {

    @Override
    public boolean performTrigger(World world, ItemStack casterStack, EntityPlayer player, BlockPos pos, EnumFacing side, int event) {
        if(event == 1) {
            try {
                TileEntity tileEntity = world.getTileEntity(pos);
                if (tileEntity instanceof TileInfusionMatrix) {
                    TileInfusionMatrix matrix = (TileInfusionMatrix) tileEntity;
                    if(!matrix.active)
                        return false;

                    TileEntity ped = world.getTileEntity(pos.down(2));
                    if (ped instanceof TilePedestal) {
                        TilePedestal pedestal = (TilePedestal) ped;
                        ItemStack input = pedestal.getStackInSlot(0);
                        if (input.isEmpty()) {
                            return false;
                        }

                        ArrayList<BlockPos> pedestals = new ArrayList<>();

                        int x, y, z;
                        h:
                        for (int a = -8; a <= 8; a++) {
                            for (int b = -8; b <= 8; b++) {
                                for (int c = -3; c <= 7; c++) {
                                    x = -a;
                                    y = c;
                                    z = -b;
                                    if ((a == 0 && b == 0)) {
                                        continue;
                                    }
                                    BlockPos effectPos = new BlockPos(pos).add(x, y, z);
                                    Block block = world.getBlockState(effectPos).getBlock();
                                    if (block instanceof BlockPedestal) {
                                        pedestals.add(effectPos);
                                    }
//                                    EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(35), stack);
//                                    if (ElectricItem.manager.canUse(stack, cost)) {
//                                        IBlockState iblockstate = worldIn.getBlockState(effectPos);
//                                        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(worldIn, effectPos, iblockstate, (EntityPlayer) entityLiving);
//                                        MinecraftForge.EVENT_BUS.post(event);
//                                        if (!event.isCanceled()) {
//                                            if (breakBlock(worldIn, effectPos, (EntityPlayer) entityLiving, stack, event.getExpToDrop())) {
//                                                ElectricItem.manager.use(stack, cost, entityLiving);
//                                            }
//                                        }
//                                    }
                                }
                            }
                        }
                        ArrayList<ItemStack> components = new ArrayList<>();
                        for (BlockPos epos : pedestals) {
                            TileEntity pedC = world.getTileEntity(epos);
                            if (pedC instanceof TilePedestal) {
                                TilePedestal pedestalC = (TilePedestal) pedC;
                                if (!pedestalC.getStackInSlot(0).isEmpty()) {
                                    components.add(pedestalC.getStackInSlot(0).copy());
                                }
                            }
                        }
                        InfusionRecipe recipe = ThaumcraftCraftingManager.findMatchingInfusionRecipe(components, input.copy(), player);
                        if (!(recipe == null)) {
                            if (recipe.getRecipeOutput() instanceof ItemStack && !(((ItemStack) recipe.getRecipeOutput()).getItem() == ModItems.stormBreaker)) {
                                return false;
                            }
                        } else
                            return false;
                    }
                }
                world.getWorldInfo().setThundering(true);
                world.getWorldInfo().setRaining(true);
                MinecraftForge.EVENT_BUS.register(new StormCraftingTicker(world, pos));
            }catch(Error ignored){}
        }
        return false;
    }
}
