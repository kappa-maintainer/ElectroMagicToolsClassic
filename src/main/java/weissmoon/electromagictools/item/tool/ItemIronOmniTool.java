package weissmoon.electromagictools.item.tool;

import com.google.common.collect.Multimap;
import ic2.api.classic.item.IMiningDrill;
import ic2.api.item.ElectricItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IShearable;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.item.ItemWeissElectricTool;
import weissmoon.electromagictools.lib.Strings;

/**
 * Created by Weissmoon on 9/23/19.
 */
public class ItemIronOmniTool extends ItemWeissElectricTool implements IMiningDrill {

    protected int hitCost;
    protected static final String SHEARMODE_NBT_TAG= "shearsMode";

    public ItemIronOmniTool() {
        this(7, ToolMaterial.IRON, Strings.Items.IRON_OMNITOOL_NAME, 20000, 200, 1);
        this.efficiency = 13;
    }

    public ItemIronOmniTool(int attackDamage, ToolMaterial material, String name, int maxCharge, int transferLimit, int tier) {
        super(attackDamage, -3.0F, material, name);
        this.maxCharge = maxCharge;
        this.hitCost = (int)(operationEnergyCost * 1.5F);
        this.tier = tier;
        this.transferLimit = transferLimit;
        this.setCreativeTab(ElectroMagicTools.EMTtab);
    }

    @Override
    public boolean canHarvestBlock(IBlockState block, ItemStack stack) {
        return Items.IRON_AXE.canHarvestBlock(block) ||
                Items.IRON_SHOVEL.canHarvestBlock(block) ||
                Items.IRON_PICKAXE.canHarvestBlock(block) ||
                Items.IRON_SWORD.canHarvestBlock(block) ||
                block.getBlock() instanceof IShearable;
    }

    public float getDestroySpeed(ItemStack stack, IBlockState block) {
        if (!ElectricItem.manager.canUse(stack, operationEnergyCost)) {
            return 0.5F;
        }

        if (Items.IRON_AXE.getDestroySpeed(stack, block) > 1.0F ||
                Items.IRON_SHOVEL.getDestroySpeed(stack, block) > 1.0F ||
                Items.IRON_PICKAXE.getDestroySpeed(stack, block) > 1.0F ||
                Items.IRON_SWORD.getDestroySpeed(stack, block) > 1.0F ||
                Items.SHEARS.getDestroySpeed(stack, block) > 1.0F){
            return efficiency;
        }else{
            return super.getDestroySpeed(stack, block);
        }
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
            double attackDamage = 1.5;
            if(ElectricItem.manager.canUse(stack, ((ItemIronOmniTool)stack.getItem()).hitCost)) {
                attackDamage = this.attackDamage;
            }
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)this.attackSpeed, 0));
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

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    @Override
    public double getMaxCharge(ItemStack stack) {
        return this.maxCharge;
    }

    @Override
    public int getTier(ItemStack stack) {
        return this.tier;
    }

    @Override
    public double getTransferLimit(ItemStack stack) {
        return this.transferLimit;
    }

    @Override
    public EnumEnchantmentType getType(ItemStack itemStack) {
        return EnumEnchantmentType.DIGGER;
    }

    @Override
    public boolean isBasicDrill(ItemStack stack) {
        return !stack.isItemEnchantable();
    }

    @Override
    public int getExtraSpeed(ItemStack stack) {
        int pointBoost = this.getPointBoost(stack);
        return pointBoost;
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
    public void useDrill(ItemStack stack) {
        ElectricItem.manager.use(stack, this.getEnergyCost(stack), null);
    }

    @Override
    public boolean canMine(ItemStack stack) {
        return ElectricItem.manager.canUse(stack, this.getEnergyCost(stack));
    }

    @Override
    public boolean canMineBlock(ItemStack itemStack, IBlockState iBlockState, IBlockAccess worldIn, BlockPos blockPos) {
        return ForgeHooks.canToolHarvestBlock(worldIn, blockPos, itemStack);
    }

    public int getEnergyChange(ItemStack stack) {
        int eff = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
        int unb = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
        int points = eff * eff + 1;
        points -= unb * (unb + unb);
        return points;
    }
}
