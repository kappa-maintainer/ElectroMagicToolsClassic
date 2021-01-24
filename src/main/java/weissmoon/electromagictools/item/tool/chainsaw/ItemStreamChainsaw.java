package weissmoon.electromagictools.item.tool.chainsaw;

import ic2.api.item.ElectricItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftMaterials;
import thaumcraft.common.lib.utils.BlockUtils;
import thaumcraft.common.lib.utils.Utils;
import weissmoon.electromagictools.lib.Strings;

/**
 * Created by Weissmoon on 9/23/19.
 */
public class ItemStreamChainsaw extends ItemDiamondChainsaw {

    public ItemStreamChainsaw() {
        super(ThaumcraftMaterials.TOOLMAT_THAUMIUM, 12, -3.2F, Strings.Items.STREAM_CHAINSAW_NAME);
        maxCharge = 1000000;
        cost = 400;
        hitCost = 500;
        tier = 2;
        transferLimit = 900;
        efficiency = 25F;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack){
        return EnumAction.BOW;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        if(!player.isSneaking() && Utils.isWoodLog(world, pos) && ElectricItem.manager.canUse(player.getHeldItemMainhand(), cost)){
            if(!world.isRemote){
                if(BlockUtils.breakFurthestBlock(world, pos, world.getBlockState(pos), player)) {
                    world.playSound(null, pos, new SoundEvent(new ResourceLocation("thaumcraft:bubble")), SoundCategory.PLAYERS, 0.15F, 1.0F);
                    ElectricItem.manager.use(player.getHeldItemMainhand(), cost, player);
                }
            }
            player.swingArm(EnumHand.MAIN_HAND);
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player){
        IBlockState state = player.world.getBlockState(pos);
        if(!player.isSneaking() && Utils.isWoodLog(player.world, pos) && ElectricItem.manager.canUse(player.getHeldItemMainhand(), cost)){
            if(!player.world.isRemote){
                if(BlockUtils.breakFurthestBlock(player.world, pos, state, player)){
                    player.world.playSound(null, pos, new SoundEvent(new ResourceLocation("thaumcraft:bubble")), SoundCategory.PLAYERS, 0.15F, 1.0F);
                    ElectricItem.manager.use(itemstack, cost, player);
                    return true;
                }
            }
        }
        return super.onBlockStartBreak(itemstack, pos, player);
    }
}
