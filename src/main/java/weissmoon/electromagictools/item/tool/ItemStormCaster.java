package weissmoon.electromagictools.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import weissmoon.core.item.tools.WeissItemSword;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Strings;
import thaumcraft.common.entities.projectile.EntityAlumentum;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Weissmoon on 2/15/20.
 * Tainted
 */
public class ItemStormCaster extends WeissItemSword {

    public ItemStormCaster() {
        super(ToolMaterial.DIAMOND, Strings.Items.STORMBRINGER_NAME);
        setMaxDamage(1000);
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
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (slot == EntityEquipmentSlot.MAINHAND){
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 2, 0));
        }

        return multimap;
    }
}
