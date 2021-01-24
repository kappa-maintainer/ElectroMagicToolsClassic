package weissmoon.electromagictools.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import weissmoon.core.api.client.item.IItemRenderCustom;
import weissmoon.core.api.client.item.IItemRenderer;
import weissmoon.core.item.tools.WeissItemSword;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.client.item.StormCasterRenderer;
import weissmoon.electromagictools.lib.Reference;
import weissmoon.electromagictools.lib.Strings;
import thaumcraft.common.entities.projectile.EntityAlumentum;
import weissmoon.electromagictools.network.JukeboxNonRecordEventMessage;
import weissmoon.electromagictools.network.PacketHandler;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.block.BlockJukebox.HAS_RECORD;

/**
 * Created by Weissmoon on 2/15/20.
 * Tainted
 */
public class ItemStormCaster extends WeissItemSword implements IItemRenderCustom {

    private ItemRecord record = new ItemRecord();
    private List<EntityPlayer> players = Lists.newArrayList();

    public ItemStormCaster() {
        super(ToolMaterial.DIAMOND, Strings.Items.STORMBRINGER_NAME);
        setMaxDamage(1000);
        setNoRepair();
        setCreativeTab(ElectroMagicTools.EMTtab);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand){
        ItemStack itemstack = player.getHeldItem(hand);

        world.spawnEntity(new EntityAlumentum(world, player.posX + 8, player.posY, player.posZ - 8));
        world.spawnEntity(new EntityAlumentum(world, player.posX - 8, player.posY, player.posZ + 8));
        world.spawnEntity(new EntityAlumentum(world, player.posX - 8, player.posY, player.posZ - 8));
        world.spawnEntity(new EntityAlumentum(world, player.posX + 8, player.posY, player.posZ + 8));
        world.spawnEntity(new EntityAlumentum(world, player.posX, player.posY + 4, player.posZ));
        world.spawnEntity(new EntityAlumentum(world, player.posX, player.posY + 8, player.posZ));

        player.swingArm(hand);

        if(!player.isCreative())
            itemstack.damageItem(20, player);

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TextComponentTranslation("tooltip.EMT.hammer.broken.thor").getUnformattedComponentText());
        tooltip.add(new TextComponentTranslation("tooltip.EMT.hammer.broken.danger").getUnformattedComponentText());

        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            tooltip.add(new TextComponentTranslation("tooltip.EMT.hammer.broken.plsNoRightClick").getUnformattedComponentText());
        else
            tooltip.add(new TextComponentTranslation("tooltip.EMT.hammer.broken.plsRightClick").getUnformattedComponentText());
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();

        if (slot == EntityEquipmentSlot.MAINHAND){
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 2, 0));
        }

        return multimap;
    }

//    @Override
//    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
//        IBlockState iblockstate = worldIn.getBlockState(pos);
//
//        if (iblockstate.getBlock() == Blocks.JUKEBOX && !(iblockstate.getValue(HAS_RECORD)))
//        {
//            ItemStack itemstack = player.getHeldItem(hand);
//            iblockstate = iblockstate.withProperty(HAS_RECORD, true);
//            worldIn.setBlockState(pos, iblockstate, 2);
//            ((BlockJukebox)Blocks.JUKEBOX).insertRecord(worldIn, pos, iblockstate, itemstack);
//            worldIn.playEvent(null, 1010, pos, Item.getIdFromItem(this));
//            if (!worldIn.isRemote){
//                PacketHandler.INSTANCE.sendToDimension(new JukeboxNonRecordEventMessage((byte) 0, pos), worldIn.provider.getDimension());
//            }
//            itemstack.shrink(1);
//            player.addStat(StatList.RECORD_PLAYED);
//
//            return EnumActionResult.SUCCESS;
//        }
//        else
//        {
//            return EnumActionResult.PASS;
//        }
//    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected){
        if(!(worldIn.isRemote) && entityIn instanceof EntityPlayerMP) {
            if(!players.contains(entityIn)) {
                IPlayerKnowledge knowledge = ThaumcraftCapabilities.getKnowledge((EntityPlayer)entityIn);
                if (!knowledge.isResearchKnown("p_stormcaster")) {
                    knowledge.addResearch("p_stormcaster");
                    knowledge.sync((EntityPlayerMP)entityIn);
                    players.add((EntityPlayer)entityIn);
                }
            }
        }
    }

    @Override
    public IItemRenderer getIItemRender() {
        return new StormCasterRenderer();
    }

    @SideOnly(Side.CLIENT)
    public SoundEvent getSound()
    {
        return record.getSound();
    }

    private static class ItemRecord extends net.minecraft.item.ItemRecord{

        protected ItemRecord() {
            super("magus", new SoundEvent(new ResourceLocation(Reference.MOD_ID, "magus")));
        }
    }
}
