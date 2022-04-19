package weissmoon.electromagictools.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import thaumcraft.common.entities.projectile.EntityAlumentum;
import weissmoon.core.item.tools.WeissItemSword;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Reference;
import weissmoon.electromagictools.lib.Strings;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import weissmoon.core.api.client.item.IItemRenderCustom;
import weissmoon.core.api.client.item.IItemRenderer;
import weissmoon.electromagictools.client.item.StormCasterRenderer;
import weissmoon.electromagictools.network.JukeboxNonRecordEventMessage;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Weissmoon on 2/15/20.
 * Tainted
 */
public class ItemStormCaster extends WeissItemSword implements IItemRenderCustom, IMusicProxyItem {

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

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        return this.handleMusic(player, worldIn, pos, hand);
    }

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

    @Override
    public JukeboxNonRecordEventMessage getPacket(BlockPos pos){
        return new JukeboxNonRecordEventMessage(Item.getIdFromItem(this),  pos);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public SoundEvent getSound(){
        return record.getSound();
    }

    private static class ItemRecord extends net.minecraft.item.ItemRecord{

        protected ItemRecord() {
            super("magus", new SoundEvent(new ResourceLocation(Reference.MOD_ID, "magus")));
        }
    }
}
