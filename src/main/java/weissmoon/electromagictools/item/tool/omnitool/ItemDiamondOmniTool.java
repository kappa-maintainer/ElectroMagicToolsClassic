package weissmoon.electromagictools.item.tool.omnitool;

import ic2.api.classic.item.IScannerItem;
import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.platform.lang.storage.Ic2InfoLang;
import ic2.core.platform.registry.Ic2Items;
import ic2.core.platform.registry.Ic2Lang;
import ic2.core.util.misc.StackUtil;
import ic2.core.util.obj.ToolTipType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import weissmoon.core.utils.NBTHelper;
import weissmoon.electromagictools.lib.Strings.LocaleComps;
import weissmoon.electromagictools.lib.Strings;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static weissmoon.electromagictools.util.BlockUtils.breakBlock;

/**
 * Created by Weissmoon on 9/23/19.
 */
public class ItemDiamondOmniTool extends ItemIronOmniTool {
    protected static String NBT_DIRT_MODE = "dirtMode";
    public ItemDiamondOmniTool() {
        super(9, ToolMaterial.DIAMOND, Strings.Items.DIAMOND_OMNITOOL_NAME, 100000, 400, 1);
        this.operationEnergyCost = 200;
        this.efficiency = 16;
    }

    public ItemDiamondOmniTool(int attackDamage, ToolMaterial material, String name, int maxCharge, int transferLimit, int tier) {
        super(attackDamage, material, name, maxCharge, transferLimit, tier);
    }

    @Override
    public void onSortedItemToolTip(ItemStack stack, EntityPlayer player, boolean debugTooltip, List<String> tooltip, Map<ToolTipType, List<String>> sortedTooltip) {
        NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
        tooltip.add((!tag.getBoolean(SHEARMODE_NBT_TAG) ? LocaleComps.MESSAGE_DIAMOND_CHAINSAW_NORMAL : LocaleComps.MESSAGE_DIAMOND_CHAINSAW_NO_SHEAR).getLocalized());
        tooltip.add((tag.getBoolean(NBT_DIRT_MODE) ? Ic2InfoLang.enableDrillMode : Ic2InfoLang.disableDrillMode).getLocalized());
        List<String> ctrlTip = sortedTooltip.get(ToolTipType.Ctrl);
        ctrlTip.add(Ic2Lang.onItemRightClick.getLocalized());
        ctrlTip.add(Ic2Lang.pressTo.getLocalizedFormatted(IC2.keyboard.getKeyName(2), LocaleComps.DIAMOND_OMNITOOL_TOGGLE.getLocalized()));
        ctrlTip.add("");
        ctrlTip.add(Ic2Lang.onBlockClick.getLocalized());
        ctrlTip.add(Ic2Lang.pressTo.getLocalizedFormatted(IC2.keyboard.getKeyName(0), Ic2InfoLang.drillProbing.getLocalized()));
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
    public float getDestroySpeed(ItemStack stack, IBlockState block) {
        if (!ElectricItem.manager.canUse(stack, operationEnergyCost)) {
            return 1.0F;
        }
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        if (nbt.getBoolean(NBT_DIRT_MODE) && block.getMaterial() == Material.GROUND) {
            return efficiency / 8.0F;
        }
        return super.getDestroySpeed(stack, block);
    }

    @Override
    public boolean toolSpeed(ItemStack stack, IBlockState block){
        return Items.DIAMOND_AXE.getDestroySpeed(stack, block) > 1.0F ||
                Items.DIAMOND_SHOVEL.getDestroySpeed(stack, block) > 1.0F ||
                Items.DIAMOND_PICKAXE.getDestroySpeed(stack, block) > 1.0F ||
                Items.DIAMOND_SWORD.getDestroySpeed(stack, block) > 1.0F;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn){
        ItemStack stack = player.getHeldItem(handIn);
        NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);


        if (IC2.keyboard.isModeSwitchKeyDown(player)) {
            if (IC2.platform.isRendering()){
                return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
            }
            BreakModes mode = BreakModes.fromBool(tag.getBoolean(SHEARMODE_NBT_TAG), tag.getBoolean(NBT_DIRT_MODE)).cycle(player);
            tag.setBoolean(NBT_DIRT_MODE, mode.dirtMode);
            tag.setBoolean(SHEARMODE_NBT_TAG, mode.shearMode);
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        }

        if (IC2.keyboard.isAltKeyDown(player) && !this.getScanner(player.inventory).isEmpty()) {
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        }

        return super.onItemRightClick(world, player, handIn);
    }

    public ItemStack getScanner(InventoryPlayer player) {
        ItemStack result = ItemStack.EMPTY;

        for(int i = 0; i < 9; ++i) {
            ItemStack stack = player.getStackInSlot(i);
            if (stack.getItem() instanceof IScannerItem) {
                result = stack;
                break;
            }
        }

        if (result.isEmpty()) {
            ItemStack stack = player.player.getHeldItemOffhand();
            if (stack.getItem() instanceof IScannerItem) {
                result = stack;
            }
        }

        return result;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (IC2.keyboard.isAltKeyDown(player)) {
            return Ic2Items.diamondDrill.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player){
        if(NBTHelper.getBoolean(itemstack, SHEARMODE_NBT_TAG) || player.world.isRemote)
            return false;

        return Ic2Items.chainSaw.getItem().onBlockStartBreak(itemstack, pos, player);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving){
        if(!entityLiving.isSneaking()) {
            for (int a = 1; a < 8; a++) {
                if (worldIn.getBlockState(pos.up(a)).getBlock() == Blocks.GRAVEL ||
                        worldIn.getBlockState(pos.up(a)).getBlock() == Blocks.SAND) {
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(35), stack);
                    if (ElectricItem.manager.canUse(stack, operationEnergyCost)) {
                        IBlockState iblockstate = worldIn.getBlockState(pos.up(a));
                        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(worldIn, pos.up(a), iblockstate, (EntityPlayer) entityLiving);
                        MinecraftForge.EVENT_BUS.post(event);
                        if (!event.isCanceled()){
                            if (breakBlock(worldIn, pos.up(a), (EntityPlayer) entityLiving, stack, event.getExpToDrop())) {
                                ElectricItem.manager.use(stack, operationEnergyCost, entityLiving);
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
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand){
        if(NBTHelper.getBoolean(stack, SHEARMODE_NBT_TAG) || player.world.isRemote){
            return false;
        }

        if (target instanceof IShearable) {
            BlockPos tatgetPos = new BlockPos((int) target.posX, (int) target.posY, (int) target.posZ);
            if ((((IShearable)target).isShearable(stack, target.world, tatgetPos))) {
                List<ItemStack> drops = ((IShearable)target).onSheared(stack, target.world, tatgetPos, EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("fortune"), stack));

                Random rand = new Random();
                for (ItemStack istack : drops) {
                    EntityItem ent = target.entityDropItem(istack, 1.0F);
                    ent.motionY += rand.nextFloat() * 0.05F;
                    ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                    ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int getItemEnchantability() {
        return 4;
    }

    @Override
    public int getExtraSpeed(ItemStack drill) {
        return 3 + super.getExtraSpeed(drill);
    }

    @Override
    public int getExtraEnergyCost(ItemStack drill) {
        return 14 + super.getExtraEnergyCost(drill);
    }

    enum BreakModes{
        SHEARMODE_AND_DIRTMODE(false, true),
        SHEARMODE_ONLY(false, false),
        DIRTMODE_ONLY(true, true),
        NEITHER(true, false);
        boolean shearMode;
        boolean dirtMode;
        BreakModes(boolean shearMode, boolean dirtMode){
            this.shearMode = shearMode;
            this.dirtMode = dirtMode;
        }

        static BreakModes fromBool(boolean shear, boolean dirt){
            if (!shear && dirt){
                return SHEARMODE_AND_DIRTMODE;
            } else if (!shear){
                return SHEARMODE_ONLY;
            } else if (dirt){
                return DIRTMODE_ONLY;
            } else {
                return NEITHER;
            }
        }

        BreakModes cycle(EntityPlayer player){
            if (this == SHEARMODE_AND_DIRTMODE){
                IC2.platform.messagePlayer(player, TextFormatting.RED, Ic2InfoLang.disableDrillMode);
                return SHEARMODE_ONLY;
            } else if (this == SHEARMODE_ONLY){
                IC2.platform.messagePlayer(player, TextFormatting.RED, LocaleComps.MESSAGE_DIAMOND_CHAINSAW_NO_SHEAR);
                IC2.platform.messagePlayer(player, TextFormatting.GREEN, Ic2InfoLang.enableDrillMode);
                return DIRTMODE_ONLY;
            } else if (this == DIRTMODE_ONLY){
                IC2.platform.messagePlayer(player, TextFormatting.RED, Ic2InfoLang.disableDrillMode);
                return NEITHER;
            } else {
                IC2.platform.messagePlayer(player, TextFormatting.GREEN, LocaleComps.MESSAGE_DIAMOND_CHAINSAW_NORMAL);
                IC2.platform.messagePlayer(player, TextFormatting.GREEN, Ic2InfoLang.enableDrillMode);
                return SHEARMODE_AND_DIRTMODE;
            }
        }
    }
}
