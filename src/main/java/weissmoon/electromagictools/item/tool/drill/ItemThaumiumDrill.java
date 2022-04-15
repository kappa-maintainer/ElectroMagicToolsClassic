package weissmoon.electromagictools.item.tool.drill;

import com.google.common.collect.Multimap;
import ic2.api.classic.item.IMiningDrill;
import ic2.api.classic.item.IScannerItem;
import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.platform.lang.storage.Ic2InfoLang;
import ic2.core.platform.registry.Ic2Items;
import ic2.core.platform.registry.Ic2Lang;
import ic2.core.util.misc.StackUtil;
import ic2.core.util.obj.ToolTipType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftMaterials;
import weissmoon.core.item.tools.WeissItemsPickaxe;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.item.WeissItemElectricTool;
import weissmoon.electromagictools.lib.Strings;

import javax.annotation.Nonnull;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static net.minecraft.item.ItemBlock.setTileEntityNBT;
import static weissmoon.electromagictools.util.BlockUtils.breakBlock;
import static weissmoon.electromagictools.util.ItemHelper.getChargedItem;
import static weissmoon.electromagictools.util.ItemHelper.getElectricDurability;

/**
 * Created by Weissmoon on 9/6/19.
 */
public class ItemThaumiumDrill extends WeissItemElectricTool implements IMiningDrill {

    protected double transferLimit = 0;

    public ItemThaumiumDrill() {
        this(ThaumcraftMaterials.TOOLMAT_THAUMIUM, Strings.Items.THAUMIUM_DRILL_NAME, 100000, 100, 2);
        this.operationEnergyCost = 250;
        this.efficiency = 20F;
        this.setCreativeTab(ElectroMagicTools.EMTtab);
    }

    public ItemThaumiumDrill(ToolMaterial material, String name, int maxCharge, int transferLimit, int tier) {
        super(1 - material.getAttackDamage(), -3.0F, material, name);
        this.maxCharge = maxCharge;
        this.operationEnergyCost = 0;
        this.tier = tier;
        this.transferLimit = transferLimit;
        this.efficiency = 0;
        this.setCreativeTab(ElectroMagicTools.EMTtab);
    }

    @Override
    public void onSortedItemToolTip(ItemStack stack, EntityPlayer player, boolean debugTooltip, List<String> tooltip, Map<ToolTipType, List<String>> sortedTooltip) {
        NBTTagCompound nbt = StackUtil.getNbtData(stack);
        tooltip.add((nbt.getBoolean("dirtMode") ? Ic2InfoLang.enableDrillMode : Ic2InfoLang.disableDrillMode).getLocalized());

        List<String> ctrlTip = sortedTooltip.get(ToolTipType.Ctrl);
        ctrlTip.add(Ic2Lang.onItemRightClick.getLocalized());
        ctrlTip.add(Ic2Lang.pressTo.getLocalizedFormatted(IC2.keyboard.getKeyName(2), Ic2InfoLang.drillModeSwitch.getLocalized()));
        ctrlTip.add("");
        ctrlTip.add(Ic2Lang.onBlockClick.getLocalized());
        ctrlTip.add(Ic2Lang.pressTo.getLocalizedFormatted(IC2.keyboard.getKeyName(0), Ic2InfoLang.drillProbing.getLocalized()));
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
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState block, BlockPos pos, EntityLivingBase entityLiving) {
        if(entityLiving instanceof EntityPlayer) {
            if(!entityLiving.isSneaking()) {
                for (int a = 1; a < 8; a++) {
                    if (world.getBlockState(pos.up(a)).getBlock() == Blocks.GRAVEL ||
                            world.getBlockState(pos.up(a)).getBlock() == Blocks.SAND) {
                        EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(35), stack);
                        if (ElectricItem.manager.canUse(stack, getEnergyCost(stack))) {
                            IBlockState iblockstate = world.getBlockState(pos.up(a));
                            BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos.up(a), iblockstate, (EntityPlayer) entityLiving);
                            MinecraftForge.EVENT_BUS.post(event);
                            if (!event.isCanceled()) {
                                if (breakBlock(world, pos.up(a), (EntityPlayer) entityLiving, stack, event.getExpToDrop())) {
                                    ElectricItem.manager.use(stack, getEnergyCost(stack), entityLiving);
                                    IC2.achievements.issueStat((EntityPlayer)entityLiving, "blocksDrilled");
                                }
                            }
                        }
                    } else
                        break;
                }
            }
            ElectricItem.manager.use(stack, getEnergyCost(stack), entityLiving);
            IC2.achievements.issueStat((EntityPlayer)entityLiving, "blocksDrilled");
        }
        return true;
    }

    @Override
    public boolean canHarvestBlock(IBlockState block, ItemStack stack) {
        return Items.DIAMOND_PICKAXE.canHarvestBlock(block, stack) ||
                Items.DIAMOND_SHOVEL.canHarvestBlock(block, stack);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState block) {
        if (!ElectricItem.manager.canUse(stack, getEnergyCost(stack))) {
            return 1.0F;
        }
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        if (nbt.getBoolean("dirtMode") && block.getMaterial() == Material.GROUND) {
            return efficiency / 8.0F;
        }
        if (Items.DIAMOND_PICKAXE.getDestroySpeed(stack, block) > 1.0F || Items.DIAMOND_SHOVEL.getDestroySpeed(stack, block) > 1.0F) {
            return efficiency;
        }else{
            return super.getDestroySpeed(stack, block);
        }
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase player){
        ElectricItem.manager.use(itemstack, operationEnergyCost, player);
        return true;
    }

    @Override
    public boolean isRepairable()
    {
        return false;
    }

    @Override
    public int getItemEnchantability() {
        return 8;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack){
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);

        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            double attackDamage = 1.5;
            if(ElectricItem.manager.canUse(stack, ((ItemThaumiumDrill)stack.getItem()).operationEnergyCost)) {
                attackDamage = 5.5;
            }
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", attackSpeed, 0));
        }

        return multimap;
        //return this.getItemAttributeModifiers(slot);
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
    public Set<String> getToolClasses(ItemStack stack)
    {
        return com.google.common.collect.ImmutableSet.of("pickaxe", "shovel");
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
        return tier;
    }

    @Override
    public double getTransferLimit(ItemStack stack) {
        return transferLimit;
    }

    @Override
    public boolean isBasicDrill(ItemStack drill) {
        return false;
    }

    @Override
    public int getExtraSpeed(ItemStack drill){
        int pointBoost = this.getPointBoost(drill);
        return pointBoost + 5;
    }

    private int getPointBoost(ItemStack drill) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, drill);
        return lvl <= 0 ? 0 : lvl * lvl + 1;
    }

    @Override
    public int getExtraEnergyCost(ItemStack drill) {
        int points = this.getEnergyChange(drill) + 21;
        return Math.max(points, 0);
    }

    public int getEnergyChange(ItemStack drill) {
        int eff = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, drill);
        int unb = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, drill);
        int points = eff * eff + 1;
        points -= unb * (unb + unb);
        return points;
    }


    @Override
    public void useDrill(ItemStack drill) {
        ElectricItem.manager.use(drill, operationEnergyCost * 0.95, null);
    }

    @Override
    public boolean canMine(ItemStack drill) {
        return ElectricItem.manager.getCharge(drill) >= operationEnergyCost * 0.95;
    }

    @Override
    public boolean canMineBlock(ItemStack drill, IBlockState state, IBlockAccess access, BlockPos pos) {
        return ForgeHooks.canToolHarvestBlock(access, pos, drill);
    }

    @Override
    public EnumEnchantmentType getType(ItemStack itemStack) {
        return EnumEnchantmentType.DIGGER;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (IC2.keyboard.isModeSwitchKeyDown(playerIn)) {
            if (IC2.platform.isRendering()) {
                return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
            }

            NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
            boolean result = !nbt.getBoolean("dirtMode");
            nbt.setBoolean("dirtMode", result);
            IC2.platform.messagePlayer(playerIn, result ? Ic2InfoLang.enableDrillMode : Ic2InfoLang.disableDrillMode);
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        }

        if (IC2.keyboard.isAltKeyDown(playerIn) && !this.getScanner(playerIn.inventory).isEmpty()) {
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        }

        return ActionResult.newResult(EnumActionResult.PASS, stack);
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

    // torch placement when gtcx is loaded
    final ItemStack torch = new ItemStack(Blocks.TORCH);
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (IC2.keyboard.isAltKeyDown(player)) {
            return Ic2Items.diamondDrill.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }
        if (!ElectroMagicTools.gtcxLoaded){
            return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }
        IBlockState state = worldIn.getBlockState(pos);
        Block block = state.getBlock();
        ItemStack stack = ItemStack.EMPTY;
        if (!block.isReplaceable(worldIn, pos)) {
            pos = pos.offset(facing);
        }
        for (ItemStack stack1 : player.inventory.mainInventory){
            if (stack1.getItem() == torch.getItem()){
                stack = stack1;
                break;
            }
        }
        if (!stack.isEmpty() && player.canPlayerEdit(pos, facing, stack) && worldIn.mayPlace(Blocks.TORCH, pos, false, facing, player)) {
            IBlockState state1 = Blocks.TORCH.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, 0, player, hand);
            if (placeBlockAt(stack, player, worldIn, pos, facing, hitX, hitY, hitZ, state1)) {
                state1 = worldIn.getBlockState(pos);
                SoundType soundtype = state1.getBlock().getSoundType(state1, worldIn, pos, player);
                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
            }
            return EnumActionResult.SUCCESS;
        } else {
            return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }

    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        if (!world.setBlockState(pos, newState, 11)) return false;

        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == Blocks.TORCH) {
            setTileEntityNBT(world, player, pos, stack);
            Blocks.TORCH.onBlockPlacedBy(world, pos, state, player, stack);

            if (player instanceof EntityPlayerMP) {
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, stack);
            }
        }

        return true;
    }
}
