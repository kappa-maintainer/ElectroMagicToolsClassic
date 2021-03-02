package weissmoon.electromagictools.item.tool;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import weissmoon.core.item.WeissItem;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.network.FlameParticleMessage;
import weissmoon.electromagictools.network.PacketHandler;

/**
 * Created by Weissmoon on 10/20/20.
 */
public class ItemEntropyStaff extends WeissItem {

    ResourceLocation sound = new ResourceLocation("block.fire.extinguish");

    public ItemEntropyStaff() {
        super(Strings.Items.ENTROPY_STAFF_NAME);
        setCreativeTab(ElectroMagicTools.EMTtab);
        setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        IBlockState state = worldIn.getBlockState(pos);
        ItemStack toSmelt = state.getBlock().getPickBlock(state, new RayTraceResult(RayTraceResult.Type.BLOCK, new Vec3d(hitX, hitY, hitZ), facing, pos), worldIn, pos, player);
        ItemStack result = FurnaceRecipes.instance().getSmeltingResult(toSmelt);
        if(result.getItem() instanceof ItemBlock){
            IBlockState toPlace = ((ItemBlock) result.getItem()).getBlock().getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, result.getItemDamage(), player, hand);
            if(worldIn.setBlockState(pos,toPlace,3)){
                worldIn.playSound(player, pos, new SoundEvent(sound), SoundCategory.BLOCKS, 1, 1);
                if (!worldIn.isRemote){
                    PacketHandler.INSTANCE.sendToDimension(new FlameParticleMessage(pos), worldIn.provider.getDimension());
                }
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }
}
