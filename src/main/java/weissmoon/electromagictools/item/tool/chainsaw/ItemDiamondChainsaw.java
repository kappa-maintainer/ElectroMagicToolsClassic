package weissmoon.electromagictools.item.tool.chainsaw;

import com.google.common.collect.Multimap;
import ic2.api.classic.item.IDamagelessElectricItem;
import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
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
import weissmoon.electromagictools.lib.Strings;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static weissmoon.electromagictools.util.ItemHelper.getChargedItem;
import static weissmoon.electromagictools.util.ItemHelper.getElectricDurability;

/**
 * Created by Weissmoon on 9/22/19.
 */
public class ItemDiamondChainsaw extends WeissItemAxe implements IDamagelessElectricItem {
    
    protected int maxCharge, cost, hitCost, tier, transferLimit;
    private static final String SHEARMODE_NBT_TAG= "shearsMode";

    public ItemDiamondChainsaw() {
        this(ThaumcraftMaterials.TOOLMAT_THAUMIUM, 0, -3.2F, Strings.Items.DIAMOND_CHAINSAW_NAME);
        maxCharge = 100000;
        cost = 250;
        hitCost = 300;
        tier = 1;
        transferLimit = 100;
        efficiency = 16F;
    }

    public ItemDiamondChainsaw(ToolMaterial material, float damage, float speed, String name) {
        super(material, damage, speed, name);
//        maxCharge = 0;
//        cost = 0;
//        hitCost = 0;
//        tier = 0;
//        transferLimit = 0;
//        efficiency = 0;
        setNoRepair();
        setCreativeTab(ElectroMagicTools.EMTtab);
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
        ElectricItem.manager.use(stack, cost, entityLiving);
        return true;
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
        return Items.DIAMOND_AXE.canHarvestBlock(state, stack) ||
                Items.DIAMOND_AXE.canHarvestBlock(state, stack) ||
                Items.SHEARS.canHarvestBlock(state, stack);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState block) {
        if (!ElectricItem.manager.canUse(stack, cost)) {
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
            if (ElectricItem.manager.use(stack, cost, attacker)){
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

            return true;
        }
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn){
        //Tool mode handled by changeToolMode
        return super.onItemRightClick(world, player, handIn);
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
    public Set<String> getToolClasses(ItemStack stack)
    {
        return com.google.common.collect.ImmutableSet.of("axe");
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

//    @Override
//    public void changeToolMode(ItemStack stack){
//        NBTHelper.toggleBoolean(stack, SHEARMODE_NBT_TAG);
//    }
}