package weissmoon.electromagictools.item.tool.drill;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftMaterials;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXScanSource;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Strings;

public class ItemCoreDrill extends ItemThaumiumDrill {
    public ItemCoreDrill() {
        super(ThaumcraftMaterials.TOOLMAT_ELEMENTAL, Strings.Items.CORE_DRILL_NAME, 1000000, 1000, 3);
        this.operationEnergyCost = 350;
        this.efficiency = 25F;
        this.setCreativeTab(ElectroMagicTools.EMTtab);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!player.isSneaking() || IC2.keyboard.isAltKeyDown(player)){
            return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }
        ItemStack stack = player.getHeldItem(hand);
        if (ElectricItem.manager.use(stack, this.getEnergyCost(stack) * 5, player)){
            worldIn.playSound(null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundsTC.wandfail, SoundCategory.BLOCKS, 0.2F, 0.2F + worldIn.rand.nextFloat() * 0.2F);
            PacketHandler.INSTANCE.sendTo(new PacketFXScanSource(pos, 1), (EntityPlayerMP)player);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}
