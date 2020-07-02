package weissmoon.electromagictools.item.tool;

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
public class ItemStreamChainsaw extends ItemDiamondChainsaw{

    public ItemStreamChainsaw() {
        super(ThaumcraftMaterials.TOOLMAT_THAUMIUM, 12, 0F, Strings.Items.STREAM_CHAINSAW_NAME, 100000, 900, 3);
        this.operationEnergyCost = 400;
        this.efficiency = 25F;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack){
        return EnumAction.BOW;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        if(!player.isSneaking() && Utils.isWoodLog(world, pos) && ElectricItem.manager.canUse(player.getHeldItemMainhand(), this.operationEnergyCost)){
            if(!world.isRemote){
                if(BlockUtils.breakFurthestBlock(world, pos, world.getBlockState(pos), player)) {
                    world.playSound(null, pos, new SoundEvent(new ResourceLocation("thaumcraft:bubble")), SoundCategory.PLAYERS, 0.15F, 1.0F);
                    ElectricItem.manager.use(player.getHeldItem(hand), operationEnergyCost, player);
                }
            }
            player.swingArm(hand);
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player){
        IBlockState state = player.world.getBlockState(pos);
        if(!player.isSneaking() && Utils.isWoodLog(player.world, pos) && ElectricItem.manager.canUse(player.getHeldItemMainhand(), this.operationEnergyCost)){
            if(!player.world.isRemote){
                if(BlockUtils.breakFurthestBlock(player.world, pos, state, player)){
                    player.world.playSound(null, pos, new SoundEvent(new ResourceLocation("thaumcraft:bubble")), SoundCategory.PLAYERS, 0.15F, 1.0F);
                    ElectricItem.manager.use(itemstack, this.operationEnergyCost, player);
                    return true;
                }
            }
        }
        return super.onBlockStartBreak(itemstack, pos, player);
    }
}
