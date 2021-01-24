package weissmoon.electromagictools.item.tool.drill;

import com.google.common.collect.Multimap;
import ic2.api.classic.item.IDamagelessElectricItem;
import ic2.api.classic.item.IMiningDrill;
import ic2.api.item.ElectricItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
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
import weissmoon.electromagictools.lib.Strings;

import javax.annotation.Nonnull;

import java.util.Set;

import static weissmoon.electromagictools.util.BlockUtils.breakBlock;
import static weissmoon.electromagictools.util.ItemHelper.getChargedItem;
import static weissmoon.electromagictools.util.ItemHelper.getElectricDurability;

/**
 * Created by Weissmoon on 9/6/19.
 */
public class ItemThaumiumDrill extends WeissItemsPickaxe implements IDamagelessElectricItem, IMiningDrill {

    protected int maxCharge, cost, tier = 0;
    protected double transferLimit = 0;

    public ItemThaumiumDrill() {
        this(ThaumcraftMaterials.TOOLMAT_THAUMIUM, Strings.Items.THAUMIUM_DRILL_NAME);
        maxCharge = 100000;
        cost = 250;
        tier = 1;
        transferLimit = 100;
        efficiency = 20F;
    }

    public ItemThaumiumDrill(ToolMaterial material, String name) {
        super(material, name);
//        maxCharge = 0;
//        cost = 0;
//        tier = 0;
//        transferLimit = 0;
//        efficiency = 0;
        setNoRepair();
        setCreativeTab(ElectroMagicTools.EMTtab);
        setMaxDamage(0);
    }

    @Override
    public int getMaxDamage(ItemStack stack){
        return 0;
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
        if(!entityLiving.isSneaking()) {
            for (int a = 1; a < 8; a++) {
                if (world.getBlockState(pos.up(a)).getBlock() == Blocks.GRAVEL ||
                        world.getBlockState(pos.up(a)).getBlock() == Blocks.SAND) {
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(35), stack);
                    if (ElectricItem.manager.canUse(stack, cost)) {
                        IBlockState iblockstate = world.getBlockState(pos.up(a));
                        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos.up(a), iblockstate, (EntityPlayer) entityLiving);
                        MinecraftForge.EVENT_BUS.post(event);
                        if (!event.isCanceled()) {
                            if (breakBlock(world, pos.up(a), (EntityPlayer) entityLiving, stack, event.getExpToDrop())) {
                                ElectricItem.manager.use(stack, cost, entityLiving);
                            }
                        }
                    }
                } else
                    break;
            }
        }
        ElectricItem.manager.use(stack, cost, entityLiving);
        return true;
    }

    @Override
    public boolean canHarvestBlock(IBlockState block, ItemStack stack) {
        return Items.DIAMOND_PICKAXE.canHarvestBlock(block, stack) ||
                Items.DIAMOND_SHOVEL.canHarvestBlock(block, stack);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState block) {
        if (!ElectricItem.manager.canUse(stack, cost)) {
            return 1.0F;
        }
        if (Items.DIAMOND_PICKAXE.getDestroySpeed(stack, block) > 1.0F || Items.DIAMOND_SHOVEL.getDestroySpeed(stack, block) > 1.0F) {
            return efficiency;
        }else{
            return super.getDestroySpeed(stack, block);
        }
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase player){
        ElectricItem.manager.use(itemstack, ((ItemThaumiumDrill)itemstack.getItem()).cost, player);
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
            if(ElectricItem.manager.canUse(stack, ((ItemThaumiumDrill)stack.getItem()).cost)) {
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
    public int getExtraSpeed(ItemStack drill) {
        return 5;
    }

    @Override
    public int getExtraEnergyCost(ItemStack drill) {
        return 21;
    }

    @Override
    public void useDrill(ItemStack drill) {
        ElectricItem.manager.use(drill, cost * 0.95, null);
    }

    @Override
    public boolean canMine(ItemStack drill) {
        return ElectricItem.manager.getCharge(drill) >= cost * 0.95;
    }

    @Override
    public boolean canMineBlock(ItemStack drill, IBlockState state, IBlockAccess access, BlockPos pos) {
        return ForgeHooks.canToolHarvestBlock(access, pos, drill);
    }
}
