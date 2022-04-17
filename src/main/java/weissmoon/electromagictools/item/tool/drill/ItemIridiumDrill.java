package weissmoon.electromagictools.item.tool.drill;

import ic2.core.IC2;
import ic2.core.platform.lang.storage.Ic2InfoLang;
import ic2.core.util.misc.StackUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import weissmoon.core.utils.NBTHelper;
import weissmoon.electromagictools.lib.Strings;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Created by Weissmoon on 9/2/20.
 */
public class ItemIridiumDrill extends ItemThaumiumDrill{

    private static String FORTUNE_MODE_NBT_KEY = "fortuneMode";
    private static String SILK_MODE_NBT_KEY = "silkMode";
    private static String DIRTMODE_NBT_KEY = "dirtMode";

    public ItemIridiumDrill(){
        super(ToolMaterial.DIAMOND, Strings.Items.IRIDIUM_DRILL_NAME, 300000, 1000, 2);
        operationEnergyCost = 300;
        efficiency = 25F;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
        super.addInformation(stack, worldIn, tooltip, flagIn);

        boolean fortune = NBTHelper.getBoolean(stack, FORTUNE_MODE_NBT_KEY);
        boolean silk = NBTHelper.getBoolean(stack, SILK_MODE_NBT_KEY);
        if(fortune){
            tooltip.add(Enchantments.FORTUNE.getTranslatedName(3));
        }else if(silk){
            tooltip.add(Enchantments.SILK_TOUCH.getTranslatedName(1));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn){
        ItemStack stack = playerIn.getHeldItem(handIn);
        if(IC2.keyboard.isModeSwitchKeyDown(playerIn)){
            if(IC2.platform.isRendering()){
                return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
            }

            NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
            BreakModes mode = BreakModes.fromBool(tag.getBoolean(FORTUNE_MODE_NBT_KEY), tag.getBoolean(SILK_MODE_NBT_KEY), tag.getBoolean(DIRTMODE_NBT_KEY)).cycle(playerIn);
            tag.setBoolean(FORTUNE_MODE_NBT_KEY, mode.fortuneMode);
            tag.setBoolean(SILK_MODE_NBT_KEY, mode.silkMode);
            tag.setBoolean(DIRTMODE_NBT_KEY, mode.dirtMode);
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        }

        if(IC2.keyboard.isAltKeyDown(playerIn) && !this.getScanner(playerIn.inventory).isEmpty()){
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        }

        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }

    @Override
    public boolean hasEffect(ItemStack stack){
        NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
        BreakModes mode = BreakModes.fromBool(tag.getBoolean(FORTUNE_MODE_NBT_KEY), tag.getBoolean(SILK_MODE_NBT_KEY), tag.getBoolean(DIRTMODE_NBT_KEY));
        return mode.fortuneMode || mode.silkMode;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment){
        if(enchantment == Enchantments.FORTUNE || enchantment == Enchantments.SILK_TOUCH)
            return false;
        return enchantment.type.canEnchantItem(stack.getItem());
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book){
        NBTTagList nbttaglist = book.getEnchantmentTagList();
        for(int j = 0;j<nbttaglist.tagCount();++j){
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(j);
            int k = nbttagcompound.getShort("id");
            Enchantment enchantment = Enchantment.getEnchantmentByID(k);

            if(enchantment == Enchantments.FORTUNE || enchantment == Enchantments.SILK_TOUCH)
                return false;
        }
        return super.isBookEnchantable(stack, book);
    }

//    @Override
//    public void changeToolMode(ItemStack stack){
//        NBTHelper.toggleBoolean(stack, MODE_NBT_KEY);
//    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player){
        boolean fortune = NBTHelper.getBoolean(itemstack, FORTUNE_MODE_NBT_KEY);
        boolean silk = NBTHelper.getBoolean(itemstack, SILK_MODE_NBT_KEY);
        if(fortune){
            itemstack.addEnchantment(Enchantments.FORTUNE, 3);
            NBTHelper.setBoolean(itemstack, "fortuneAddition", true);
//            tooltip.add(Enchantments.FORTUNE.getTranslatedName(3));
        }else if(silk){
            itemstack.addEnchantment(Enchantments.SILK_TOUCH, 1);
            NBTHelper.setBoolean(itemstack, "silkAddition", true);
//            tooltip.add(Enchantments.SILK_TOUCH.getTranslatedName(1));
        }
        return false;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected){
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if(NBTHelper.getBoolean(stack, "silkAddition")){
            NBTHelper.setBoolean(stack, "silkAddition", false);
            Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(stack);
            enchants.remove(Enchantments.SILK_TOUCH);
            EnchantmentHelper.setEnchantments(enchants, stack);
        }
        if(NBTHelper.getBoolean(stack, "fortuneAddition")){
            NBTHelper.setBoolean(stack, "fortuneAddition", false);
            Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(stack);
            enchants.remove(Enchantments.FORTUNE);
            EnchantmentHelper.setEnchantments(enchants, stack);
        }
    }

    @Override
    public int getEnergyCost(ItemStack stack) {
        NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
        BreakModes mode = BreakModes.fromBool(tag.getBoolean(FORTUNE_MODE_NBT_KEY), tag.getBoolean(SILK_MODE_NBT_KEY), tag.getBoolean(DIRTMODE_NBT_KEY));
        if(mode.fortuneMode || mode.silkMode)
            return this.operationEnergyCost * 3;
        return this.operationEnergyCost;
    }

    private enum BreakModes{
        FORTUNE_AND_DIRTMODE(true, false, true),
        FORTUNE_ONLY(true, false, false),
        SILK_AND_DIRTMODE(false, true, true),
        SILK_ONLY(false, true, false),
        DIRTMODE_ONLY(false, false, true),
        NEITHER(false, false, false);
        boolean fortuneMode;
        boolean silkMode;
        boolean dirtMode;
        BreakModes(boolean fortuneMode, boolean silkMode, boolean dirtMode){
            this.fortuneMode = fortuneMode;
            this.silkMode = silkMode;
            this.dirtMode = dirtMode;
        }

        static BreakModes fromBool(boolean fortune, boolean silk, boolean dirt){
            if(fortune){
                if(dirt)
                    return FORTUNE_AND_DIRTMODE;
                else
                    return FORTUNE_ONLY;
            }else if(silk){
                if(dirt)
                    return SILK_AND_DIRTMODE;
                else
                    return SILK_ONLY;
            }else if(dirt){
                return DIRTMODE_ONLY;
            }
            return NEITHER;
        }

        BreakModes cycle(EntityPlayer player){
            if(this == FORTUNE_AND_DIRTMODE){
                IC2.platform.messagePlayer(player, TextFormatting.RED, Ic2InfoLang.disableDrillMode);
                return FORTUNE_ONLY;
            }else if(this == FORTUNE_ONLY){
                IC2.platform.messagePlayer(player, TextFormatting.RED, Strings.LocaleComps.MESSAGE_IRIDIUM_DRILL_NO_FORTUNE);
                IC2.platform.messagePlayer(player, TextFormatting.GREEN, Strings.LocaleComps.MESSAGE_IRIDIUM_DRILL_SILK);
                IC2.platform.messagePlayer(player, TextFormatting.GREEN, Ic2InfoLang.enableDrillMode);
                return SILK_AND_DIRTMODE;
            }else if(this == SILK_AND_DIRTMODE){
                IC2.platform.messagePlayer(player, TextFormatting.RED, Ic2InfoLang.disableDrillMode);
                return SILK_ONLY;
            }else if(this == SILK_ONLY){
                IC2.platform.messagePlayer(player, TextFormatting.RED, Strings.LocaleComps.MESSAGE_IRIDIUM_DRILL_NO_SILK);
                IC2.platform.messagePlayer(player, TextFormatting.GREEN, Ic2InfoLang.enableDrillMode);
                return DIRTMODE_ONLY;
            }else if(this == DIRTMODE_ONLY){
                IC2.platform.messagePlayer(player, TextFormatting.RED, Ic2InfoLang.disableDrillMode);
                return NEITHER;
            }else{
                IC2.platform.messagePlayer(player, TextFormatting.GREEN, Strings.LocaleComps.MESSAGE_IRIDIUM_DRILL_FORTUNE);
                IC2.platform.messagePlayer(player, TextFormatting.GREEN, Ic2InfoLang.enableDrillMode);
                return FORTUNE_AND_DIRTMODE;
            }
        }
    }
}
