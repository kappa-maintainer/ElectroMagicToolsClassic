package weissmoon.electromagictools.item.tool.omnitool;

import com.google.common.collect.Multimap;
import ic2.api.classic.item.IDamagelessElectricItem;
import ic2.api.classic.item.IMiningDrill;
import ic2.api.item.ElectricItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weissmoon.core.item.tools.WeissItemsPickaxe;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.item.WeissItemElectricTool;
import weissmoon.electromagictools.lib.Strings;

import javax.annotation.Nonnull;

import java.util.Set;

import static weissmoon.electromagictools.util.ItemHelper.getChargedItem;

/**
 * Created by Weissmoon on 9/23/19.
 */
public class ItemIronOmniTool extends WeissItemElectricTool implements IMiningDrill {

    protected int hitCost;
    protected static final String SHEARMODE_NBT_TAG= "shearsMode";

    public ItemIronOmniTool() {
        this(7, ToolMaterial.IRON, Strings.Items.IRON_OMNITOOL_NAME, 50000, 200, 1);
        this.efficiency = 13;
        this.operationEnergyCost = 100;
    }

    public ItemIronOmniTool(int attackDamage, ToolMaterial material, String name, int maxCharge, int transferLimit, int tier) {
        super(attackDamage, -3.0F, material, name);
        this.maxCharge = maxCharge;
        this.hitCost = (int)(operationEnergyCost * 1.25F);
        this.tier = tier;
        this.transferLimit = transferLimit;
        this.setCreativeTab(ElectroMagicTools.EMTtab);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack){
        return (maxCharge - ElectricItem.manager.getCharge(stack)) / (float)maxCharge;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        ElectricItem.manager.use(stack, operationEnergyCost, entityLiving);
        return true;
    }

    @Override
    public boolean canHarvestBlock(IBlockState block, ItemStack stack) {
        return Items.IRON_AXE.canHarvestBlock(block) ||
                Items.IRON_SHOVEL.canHarvestBlock(block) ||
                Items.IRON_PICKAXE.canHarvestBlock(block) ||
                Items.IRON_SWORD.canHarvestBlock(block) ||
                block.getBlock() instanceof IShearable;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState block) {
        if (!ElectricItem.manager.canUse(stack, operationEnergyCost)) {
            return 1.0F;
        }

        if (toolSpeed(stack, block) ||
                Items.SHEARS.getDestroySpeed(stack, block) > 1.0F){
            return efficiency;
        }else{
            return super.getDestroySpeed(stack, block);
        }
    }

    public boolean toolSpeed(ItemStack stack, IBlockState block){
        return Items.IRON_AXE.getDestroySpeed(stack, block) > 1.0F ||
                Items.IRON_SHOVEL.getDestroySpeed(stack, block) > 1.0F ||
                Items.IRON_PICKAXE.getDestroySpeed(stack, block) > 1.0F ||
                Items.IRON_SWORD.getDestroySpeed(stack, block) > 1.0F;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker){
        ElectricItem.manager.use(stack, hitCost, attacker);
        return true;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack){
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);

        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            double stackAttackDamage = 1.5;
            if(ElectricItem.manager.canUse(stack, hitCost)) {
                stackAttackDamage = attackDamage;
            }
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", stackAttackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", attackSpeed, 0));
        }

        return multimap;
        //return this.getItemAttributeModifiers(slot);
    }

    @Override
    public int getItemEnchantability() {
        return 2;
    }

    @Override
    public boolean isRepairable() {
        return false;
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
        return com.google.common.collect.ImmutableSet.of("pickaxe", "axe", "shovel");
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
    public int getExtraSpeed(ItemStack stack) {
        return this.getPointBoost(stack);
    }

    private int getPointBoost(ItemStack stack) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
        return lvl <= 0 ? 0 : lvl * lvl + 1;
    }

    @Override
    public int getExtraEnergyCost(ItemStack stack) {
        int points = this.getEnergyChange(stack);
        return Math.max(points, 0);
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

    public int getEnergyChange(ItemStack stack) {
        int eff = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
        int unb = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
        int points = eff * eff + 1;
        points -= unb * (unb + unb);
        return points;
    }
}
