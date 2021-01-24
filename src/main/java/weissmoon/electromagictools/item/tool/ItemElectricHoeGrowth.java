package weissmoon.electromagictools.item.tool;

import ic2.api.classic.item.IDamagelessElectricItem;
import ic2.api.item.ElectricItem;
import net.minecraft.block.IGrowable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftMaterials;
import thaumcraft.api.blocks.BlocksTC;
import weissmoon.core.item.tools.WeissItemHoe;
import weissmoon.core.utils.NBTHelper;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Strings;

import javax.annotation.Nonnull;
import java.util.Random;

import static weissmoon.electromagictools.util.ItemHelper.getChargedItem;
import static weissmoon.electromagictools.util.ItemHelper.getElectricDurability;

/**
 * Created by Weissmoon on 9/6/19.
 */
public class ItemElectricHoeGrowth extends WeissItemHoe implements IDamagelessElectricItem {

    private Random all0 = new Random(){
        public int nextInt(int na){
            return 0;
        }
    };
    private final int maxCharge = 200000;

    public ItemElectricHoeGrowth() {
        super(ThaumcraftMaterials.TOOLMAT_THAUMIUM, Strings.Items.ELECTRIC_HOE_NAME);
        setNoRepair();
        setCreativeTab(ElectroMagicTools.EMTtab);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos blockPos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack hoeStack = player.getHeldItem(hand);
        NBTHelper.setBoolean(hoeStack, "Unbreakable", true);
        boolean used = false;
        for(int x = -1; x <= 1; x++){
            for(int z = -1; z <= 1; z++){
                BlockPos hoePos = new BlockPos(blockPos).add(x, 0, z);
                if(ElectricItem.manager.canUse(hoeStack, 25) && super.onItemUse(player, world, hoePos, hand, facing, hitX, hitY, hitZ).equals(EnumActionResult.SUCCESS)){
                    ElectricItem.manager.use(hoeStack, 25, player);
                    world.playEvent(2005, hoePos, 0);
                    used = true;
                }
            }
        }
        if(used){
            NBTHelper.setBoolean(player.getHeldItem(hand), "Unbreakable", false);
            return EnumActionResult.SUCCESS;
        }
        if(ElectricItem.manager.canUse(hoeStack, 250)){
            used = ItemDye.applyBonemeal(new ItemStack(Items.DYE, 1, 15), world, blockPos);
            if (!used){
                if (world.getBlockState(blockPos).getBlock() == BlocksTC.saplingGreatwood){
                    if(!world.isRemote)
                        ((IGrowable)BlocksTC.saplingGreatwood).grow(world, all0, blockPos, world.getBlockState(blockPos));
                    world.playEvent(2005, blockPos, 0);
                    used = true;
                }
                if(world.getBlockState(blockPos).getBlock() == BlocksTC.saplingSilverwood){
                    if(!world.isRemote)
                        ((IGrowable)BlocksTC.saplingSilverwood).grow(world, all0, blockPos, world.getBlockState(blockPos));
                    world.playEvent(2005, blockPos, 0);
                    used = true;
                }
            }else{
                ElectricItem.manager.use(hoeStack, 250, player);
                world.playEvent(2005, blockPos, 0);
            }
            if(used){
                world.playSound(player, blockPos, new SoundEvent(new ResourceLocation("thaumcraft:wand")), SoundCategory.BLOCKS, 0.75F, 0.9F);
            }
        }
        NBTHelper.setBoolean(player.getHeldItem(hand), "Unbreakable", false);
        if(used){
            return EnumActionResult.SUCCESS;
        }else{
            return EnumActionResult.PASS;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> list) {
        if (isInCreativeTab(tab)){
            ItemStack stack = new ItemStack(this, 1, 0);
            list.add(stack);
            list.add(getChargedItem(this, 1));
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack){
        return getElectricDurability(stack);
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    @Override
    public double getMaxCharge(ItemStack stack) {
        return maxCharge;
    }

    @Override
    public int getTier(ItemStack stack) {
        return 2;
    }

    @Override
    public double getTransferLimit(ItemStack stack) {
        return 1000;
    }
}
