package weissmoon.electromagictools.item.tool.chainsaw;

import com.google.common.collect.Multimap;
import ic2.api.classic.item.IDamagelessElectricItem;
import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.platform.registry.Ic2Lang;
import ic2.core.util.misc.StackUtil;
import ic2.core.util.obj.ToolTipType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftMaterials;
import weissmoon.core.api.item.IModesCore;
import weissmoon.core.item.tools.WeissItemAxe;
import weissmoon.core.utils.NBTHelper;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.item.WeissItemElectricTool;
import weissmoon.electromagictools.lib.LocaleComps;
import weissmoon.electromagictools.lib.Strings;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static weissmoon.electromagictools.util.ItemHelper.getChargedItem;
import static weissmoon.electromagictools.util.ItemHelper.getElectricDurability;

/**
 * Created by Weissmoon on 9/22/19.
 */
public class ItemDiamondChainsaw extends WeissItemElectricTool /* implements IModesCore */ {

    protected int hitCost;
    protected double transferLimit, attackDamage;
    private static final String SHEARMODE_NBT_TAG= "shearsMode";

    public ItemDiamondChainsaw() {
        this(ToolMaterial.DIAMOND, 15, 0.0F, Strings.Items.DIAMOND_CHAINSAW_NAME, 10000, 100, 1);
    }

    public ItemDiamondChainsaw(ToolMaterial material, float damage, float speed, String name, int maxCharge, int transferLimit, int tier) {
        super(damage - 1, speed, material, name);
        this.attackDamage = damage;
        this.maxCharge = maxCharge;
        this.operationEnergyCost = 50;
        this.hitCost = operationEnergyCost + 100;
        this.tier = tier;
        this.transferLimit = transferLimit;
        this.efficiency = 10;
        this.setHarvestLevel("axe", 3);
        this.setCreativeTab(ElectroMagicTools.EMTtab);
    }

    @Override
    public void onSortedItemToolTip(ItemStack stack, EntityPlayer player, boolean debugTooltip, List<String> tooltip, Map<ToolTipType, List<String>> sortedTooltip) {
        NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
        if (!tag.getBoolean(SHEARMODE_NBT_TAG)) {
            tooltip.add(LocaleComps.MESSAGE_DIAMOND_CHAINSAW_NORMAL.getLocalized());
        } else {
            tooltip.add(LocaleComps.MESSAGE_DIAMOND_CHAINSAW_NO_SHEAR.getLocalized());
        }
        List<String> ctrlTip = sortedTooltip.get(ToolTipType.Ctrl);
        ctrlTip.add(Ic2Lang.onItemRightClick.getLocalized());
        ctrlTip.add(Ic2Lang.pressTo.getLocalizedFormatted(IC2.keyboard.getKeyName(2), LocaleComps.DIAMOND_CHAINSAW_SHEAR_TOGGLE.getLocalized()));
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn){
        NBTHelper.setBoolean(stack, SHEARMODE_NBT_TAG, true);
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
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        ElectricItem.manager.use(stack, operationEnergyCost, entityLiving);
        return true;
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
        return Items.DIAMOND_AXE.canHarvestBlock(state, stack) ||
                Items.SHEARS.canHarvestBlock(state, stack);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState block) {
        if (!ElectricItem.manager.canUse(stack, operationEnergyCost)) {
            return 1.0F;
        }

        if (Items.DIAMOND_AXE.getDestroySpeed(stack, block) > 1.0F || Items.DIAMOND_SWORD.getDestroySpeed(stack, block) > 1.0F) {
            return efficiency;
        }else{
            return super.getDestroySpeed(stack, block);
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker){
        if(attacker instanceof EntityPlayer){
            if (ElectricItem.manager.use(stack, hitCost, attacker)){
                target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)attacker), 10.0F);
            } else {
                target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)attacker), 1.0F);
            }

            if (target.getHealth() <= 0.0F) {
                if (target instanceof EntityCreeper) {
                    IC2.achievements.issueStat((EntityPlayer)attacker, "killCreeperChainsaw");
                }
                IC2.achievements.issueStat((EntityPlayer)attacker, "chainsawKills");
            }

            return false;
        }
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn){
        ItemStack stack = player.getHeldItem(handIn);
        NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
        if (IC2.platform.isSimulating() && IC2.keyboard.isModeSwitchKeyDown(player)) {
            if (tag.getBoolean(SHEARMODE_NBT_TAG)) {
                tag.setBoolean(SHEARMODE_NBT_TAG, false);
                IC2.platform.messagePlayer(player, TextFormatting.GREEN, LocaleComps.MESSAGE_DIAMOND_CHAINSAW_NORMAL);
            } else {
                tag.setBoolean(SHEARMODE_NBT_TAG, true);
                IC2.platform.messagePlayer(player, TextFormatting.RED, LocaleComps.MESSAGE_DIAMOND_CHAINSAW_NO_SHEAR);
            }
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        } else {
            return super.onItemRightClick(world, player, handIn);
        }
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player){
        if(NBTHelper.getBoolean(itemstack, SHEARMODE_NBT_TAG) || player.world.isRemote)
            return false;

        IBlockState block = player.world.getBlockState(pos);
        if (block instanceof IShearable) {
            IShearable target = (IShearable) block;
            if (target.isShearable(itemstack, player.world, pos)) {
                List<ItemStack> drops = target.onSheared(itemstack, player.world, pos, EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("fortune"), itemstack));
                Random rand = new Random();

                for (ItemStack stack : drops) {
                    float f = 0.7F;
                    double xOffset = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                    double yOffset = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                    double zOffset = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                    EntityItem entityitem = new EntityItem(player.world, (double) pos.getX() + xOffset, (double) pos.getY() + yOffset, (double) pos.getZ() + zOffset, stack);
                    entityitem.setDefaultPickupDelay();
                    player.world.spawnEntity(entityitem);
                }
                player.addStat(StatList.getBlockStats(block.getBlock()));
            }
        }
        return false;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand){
        if(!NBTHelper.getBoolean(stack, SHEARMODE_NBT_TAG) || player.world.isRemote){
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
    public boolean isRepairable() {
        return false;
    }

    @Override
    public int getItemEnchantability() {
        return 4;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack){
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);

        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            double stackAttackDamage = 1.5;
            if(ElectricItem.manager.canUse(stack, ((ItemDiamondChainsaw)stack.getItem()).hitCost)) {
                stackAttackDamage = attackDamage;
            }
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", stackAttackDamage, 0));
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
    public EnumEnchantmentType getType(ItemStack itemStack) {
        return EnumEnchantmentType.DIGGER;
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        return com.google.common.collect.ImmutableSet.of("axe");
    }

//    @Override
//    public void changeToolMode(ItemStack stack){
//        NBTHelper.toggleBoolean(stack, SHEARMODE_NBT_TAG);
//    }
}
