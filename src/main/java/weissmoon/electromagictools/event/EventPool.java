package weissmoon.electromagictools.event;

import baubles.api.BaublesApi;
import com.google.common.collect.Lists;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.apache.logging.log4j.Level;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;
import weissmoon.core.utils.LogHelper;
import weissmoon.core.utils.NBTHelper;
import weissmoon.electromagictools.advancements.BaubleHitTrigger;
import weissmoon.electromagictools.advancements.WingDeathTrigger;
import weissmoon.electromagictools.item.ModItems;
import weissmoon.electromagictools.item.armour.boots.ItemNanoBootsTraveller;
import weissmoon.electromagictools.item.armour.boots.ItemQuantumBootsTraveller;
import weissmoon.electromagictools.item.armour.wings.ItemFeatherWings;
import weissmoon.electromagictools.item.armour.wings.ItemNanoWings;
import weissmoon.electromagictools.item.armour.wings.ItemQuantumWings;
import weissmoon.electromagictools.lib.Reference;
import weissmoon.electromagictools.network.OverrideScanPacket;
import weissmoon.electromagictools.network.PacketHandler;

import java.util.List;

import static weissmoon.electromagictools.ElectroMagicTools.ic2ceLoaded;
import static weissmoon.electromagictools.item.ItemOneRing.CORRUPTION_NBT_TAG;
import static weissmoon.electromagictools.item.ItemOneRing.playersWithRing;
import static weissmoon.electromagictools.item.armour.boots.ItemElectricBootsTraveller.playersWithStepUp;

/**
 * Created by Weissmoon on 9/19/19.
 */
public class EventPool {


    @SubscribeEvent
    public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event){
        if(ic2ceLoaded)
            if(!(event.player.world.isRemote)) {
                IPlayerKnowledge knowledge = ThaumcraftCapabilities.getKnowledge(event.player);
                if (!knowledge.isResearchKnown("p_ic2c_extras")) {
                    knowledge.addResearch("p_ic2c_extras");
                    knowledge.sync((EntityPlayerMP)event.player);
                }
            }
    }

    @SubscribeEvent
    public void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        String username = event.player.getName();
        playersWithStepUp.remove(username);
        if(playersWithRing.containsKey(username)) {
            playersWithRing.remove(username);
            if (!event.player.capabilities.isCreativeMode)
                (event.player).capabilities.disableDamage = false;
            event.player.setInvisible(false);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onDeath(LivingDeathEvent event){
        {
            if(event.getEntity() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.getEntityLiving();
                int slot = BaublesApi.isBaubleEquipped(player, ModItems.hitBauble);
                if (slot != -1) {
                    if (event.getSource().damageType.matches("infinity")) {
                        event.setCanceled(true);
                        return;//todo remove
                    }
                }
            }
        }
        if(event.getEntity() instanceof EntityPlayer){
            event.getEntityLiving().getEntityData().removeTag(CORRUPTION_NBT_TAG);
        }
        if(event.getSource() == DamageSource.FALL)
            if (event.getEntityLiving() instanceof EntityPlayerMP) {
                EntityPlayerMP player = (EntityPlayerMP) event.getEntityLiving();
                ItemStack stack = ItemStack.EMPTY;
                ItemStack chestStack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
                ItemStack bodyStack = BaublesApi.getBaublesHandler(player).getStackInSlot(5);
                wings:
                if (chestStack.getItem() instanceof ItemFeatherWings || bodyStack.getItem() instanceof ItemFeatherWings) {
                    if (chestStack == ItemStack.EMPTY || !(chestStack.getItem() instanceof ItemFeatherWings)) {
                        stack = bodyStack;
                        break wings;
                    } else if (bodyStack == ItemStack.EMPTY || !(bodyStack.getItem() instanceof ItemFeatherWings)) {
                        stack = chestStack;
                        break wings;
                    }
                    if (((ItemFeatherWings) chestStack.getItem()).getTier() > ((ItemFeatherWings) chestStack.getItem()).getTier())
                        stack = chestStack;
                    else
                        stack = bodyStack;
                }
                if (stack.getItem() instanceof ItemFeatherWings){
                    WingDeathTrigger.INSTANCE.trigger(player, stack);
                }
            }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onMobDrop(LivingDropsEvent event){
        EntityLivingBase entity = event.getEntityLiving();
        if(entity instanceof EntityCreeper)
            if(((EntityCreeper)entity).getPowered()) {
                EntityItem item = new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ);
                item.setItem(new ItemStack(ModItems.materials, 1, 1));
                event.getDrops().add(item);
            }
    }

    @SubscribeEvent
    public void onNanoBootsFall(LivingFallEvent event){
        PotionEffect potioneffect = event.getEntityLiving().getActivePotionEffect(MobEffects.JUMP_BOOST);
        float f = potioneffect == null ? 0.0F : (float)(potioneffect.getAmplifier() + 1);
        int i = MathHelper.ceil((event.getDistance() - 3.0F - f) * event.getDamageMultiplier());
        if (i > 0)
            return;
        if (event.getEntityLiving() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
            if(stack.getItem() instanceof ItemNanoBootsTraveller){
                if (ElectricItem.manager.use(stack, ((ItemNanoBootsTraveller) stack.getItem()).getEnergyPerDamage(), player))
                    event.setCanceled(true);
            }

            if(event.isCanceled()){
                if(stack.getItem() instanceof ItemQuantumBootsTraveller)
                    ((ItemQuantumBootsTraveller)stack.getItem()).damageAbsorbed(player, i);
                return;
            }

            ItemStack chestStack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
            ItemStack bodyStack = BaublesApi.getBaublesHandler(player).getStackInSlot(5);
            wings:
            if(chestStack.getItem() instanceof ItemFeatherWings || bodyStack.getItem() instanceof ItemFeatherWings){
                if (chestStack == ItemStack.EMPTY || !(chestStack.getItem() instanceof ItemFeatherWings)){
                    stack = bodyStack;
                    break wings;
                }else if(bodyStack == ItemStack.EMPTY || !(bodyStack.getItem() instanceof ItemFeatherWings)){
                    stack = chestStack;
                    break wings;
                }
                if(((ItemFeatherWings)chestStack.getItem()).getTier() > ((ItemFeatherWings)chestStack.getItem()).getTier())
                    stack = chestStack;
                else
                    stack = bodyStack;
            }
            if(stack.getItem() instanceof ItemFeatherWings){
                if(stack.getItem() instanceof IElectricItem){
                    if (ElectricItem.manager.use(stack, ((ItemNanoWings) stack.getItem()).getEnergyPerDamage(), player))
                        if(stack.getItem() instanceof ItemQuantumWings){
                            event.setCanceled(true);
                            return;
                        }
                    else
                        return;
                }
                float mult = event.getDamageMultiplier();
                mult /= ((ItemFeatherWings) stack.getItem()).getFallDamage();
                event.setDamageMultiplier(mult);
            }
        }
    }

    private SoundEvent sound = new SoundEvent(new ResourceLocation("welectromagic:hitcancel"));

    @SubscribeEvent
    public void onPlayerDamaged(LivingDamageEvent event){
        if (event.getEntityLiving() instanceof EntityPlayerMP) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            int slot = BaublesApi.isBaubleEquipped(player, ModItems.hitBauble);
            if(slot != -1) {
                ItemStack bauble = BaublesApi.getBaublesHandler(player).getStackInSlot(slot);
                if(NBTHelper.getInt(bauble, "charge") >= 200){
                    event.setCanceled(true);
                    NBTHelper.setInteger(bauble, "charge", 0);
                    if(player instanceof EntityPlayerMP){
                        ((EntityPlayerMP) player).connection.sendPacket(new SPacketSoundEffect(sound, SoundCategory.PLAYERS, player.posX, player.posY, player.posZ, 1, 1));
                        if(event.getAmount() >= 50)
                            BaubleHitTrigger.INSTANCE.trigger((EntityPlayerMP)player, bauble);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void playerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getWorld().isRemote && event.getEntityPlayer() != null) {
            event.getEntityPlayer().getActiveHand();
            ItemStack heldItem = event.getEntityPlayer().getHeldItem(event.getEntityPlayer().getActiveHand());
            if (!heldItem.isEmpty()) {
                List<EnumInfusionEnchantment> list = EnumInfusionEnchantment.getInfusionEnchantments(heldItem);
                if (list.contains(EnumInfusionEnchantment.SOUNDING) && event.getEntityPlayer().isSneaking()) {
                    heldItem.damageItem(5, event.getEntityPlayer());
                    event.getWorld().playSound(null, (double)event.getPos().getX() + 0.5D, (double)event.getPos().getY() + 0.5D, (double)event.getPos().getZ() + 0.5D, SoundsTC.wandfail, SoundCategory.BLOCKS, 0.2F, 0.2F + event.getWorld().rand.nextFloat() * 0.2F);
                    PacketHandler.INSTANCE.sendTo(new OverrideScanPacket(event.getPos(), EnumInfusionEnchantment.getInfusionEnchantmentLevel(heldItem, EnumInfusionEnchantment.SOUNDING)), (EntityPlayerMP)event.getEntityPlayer());
                }
            }
        }

    }

    @SubscribeEvent
    public void onLootTableLoad(LootTableLoadEvent event) {
        List<LootEntry> entries = Lists.newArrayList();
        List<LootCondition> conditions = Lists.newArrayList();
        List<LootFunction> functions = Lists.newArrayList();
        RandomValueRange rolls = new RandomValueRange(1.0F);
        RandomValueRange bonusRolls = new RandomValueRange(0.0F, 0.0F);
        if (event.getName().toString().matches("minecraft:chests/spawn_bonus_chest")){
            LootPool pool = new LootPool(entries.toArray(new LootEntry[0]),
                conditions.toArray(new LootCondition[0]),
                rolls,
                bonusRolls,
                "onering");
            pool.addEntry(new LootEntryItem(ModItems.onering, 1, 1, functions.toArray(new LootFunction[0]), conditions.toArray(new LootCondition[0]), "mjolnir"));
            event.getTable().addPool(pool);
            LogHelper.log(Reference.MOD_ID, Level.INFO, "Injecting Mjölnir into LootTable: \"" + event.getName().toString() +"\"");
        }
        if (event.getName().toString().matches("minecraft:chests/desert_pyramid")){
            LootPool pool = new LootPool(entries.toArray(new LootEntry[0]),
                    conditions.toArray(new LootCondition[0]),
                    new RandomValueRange(0.0F, 0.0F),
                    new RandomValueRange(0.0F, 0.25F),
                    "mjolnir");
            pool.addEntry(new LootEntryItem(ModItems.stormCaster, 1, 1, functions.toArray(new LootFunction[0]), conditions.toArray(new LootCondition[0]), "mjolnir"));
            event.getTable().addPool(pool);
            LogHelper.log(Reference.MOD_ID, Level.INFO, "Injecting Mjölnir into LootTable: \"" + event.getName().toString() +"\"");
        }
        if (event.getName().toString().matches("minecraft:chests/igloo_chest")){
            LootPool pool = new LootPool(entries.toArray(new LootEntry[0]),
                    conditions.toArray(new LootCondition[0]),
                    rolls,
                    bonusRolls,
                    "mjolnir");
            pool.addEntry(new LootEntryItem(ModItems.stormCaster, 1, 1, functions.toArray(new LootFunction[0]), conditions.toArray(new LootCondition[0]), "mjolnir"));
            event.getTable().addPool(pool);
            LogHelper.log(Reference.MOD_ID, Level.INFO, "Injecting Mjölnir into LootTable: \"" + event.getName().toString() +"\"");
        }
        if (event.getName().toString().matches("minecraft:chests/jungle_temple")){
            LootPool pool = new LootPool(entries.toArray(new LootEntry[0]),
                    conditions.toArray(new LootCondition[0]),
                    new RandomValueRange(0.0F, 0.0F),
                    new RandomValueRange(0.0F, 0.25F),
                    "mjolnir");
            pool.addEntry(new LootEntryItem(ModItems.stormCaster, 1, 1, functions.toArray(new LootFunction[0]), conditions.toArray(new LootCondition[0]), "mjolnir"));
            event.getTable().addPool(pool);
            LogHelper.log(Reference.MOD_ID, Level.INFO, "Injecting Mjölnir into LootTable: \"" + event.getName().toString() +"\"");
        }
        if (event.getName().toString().matches("minecraft:chests/simple_dungeon")){
            LootPool pool = new LootPool(entries.toArray(new LootEntry[0]),
                    conditions.toArray(new LootCondition[0]),
                    new RandomValueRange(0.0F, 0.0F),
                    new RandomValueRange(0.0F, 0.5F),
                    "mjolnir");
            pool.addEntry(new LootEntryItem(ModItems.stormCaster, 1, 1, functions.toArray(new LootFunction[0]), conditions.toArray(new LootCondition[0]), "mjolnir"));
            event.getTable().addPool(pool);
            LogHelper.log(Reference.MOD_ID, Level.INFO, "Injecting Mjölnir into LootTable: \"" + event.getName().toString() +"\"");
        }
        if (event.getName().toString().matches("minecraft:chests/stronghold_corridor")){
            LootPool pool = new LootPool(entries.toArray(new LootEntry[0]),
                    conditions.toArray(new LootCondition[0]),
                    new RandomValueRange(0.0F, 0.0F),
                    new RandomValueRange(0.0F, 0.15F),
                    "mjolnir");
            pool.addEntry(new LootEntryItem(ModItems.stormCaster, 1, 1, functions.toArray(new LootFunction[0]), conditions.toArray(new LootCondition[0]), "mjolnir"));
            event.getTable().addPool(pool);
            LogHelper.log(Reference.MOD_ID, Level.INFO, "Injecting Mjölnir into LootTable: \"" + event.getName().toString() +"\"");
        }
        if (event.getName().toString().matches("minecraft:chests/stronghold_crossing")){
            LootPool pool = new LootPool(entries.toArray(new LootEntry[0]),
                    conditions.toArray(new LootCondition[0]),
                    new RandomValueRange(0.0F, 0.0F),
                    new RandomValueRange(0.0F, 0.15F),
                    "mjolnir");
            pool.addEntry(new LootEntryItem(ModItems.stormCaster, 1, 1, functions.toArray(new LootFunction[0]), conditions.toArray(new LootCondition[0]), "mjolnir"));
            event.getTable().addPool(pool);
            LogHelper.log(Reference.MOD_ID, Level.INFO, "Injecting Mjölnir into LootTable: \"" + event.getName().toString() +"\"");
        }
        if (event.getName().toString().matches("minecraft:chests/end_city_treasure")){
            LootPool pool = new LootPool(entries.toArray(new LootEntry[0]),
                    conditions.toArray(new LootCondition[0]),
                    new RandomValueRange(0.0F, 0.0F),
                    new RandomValueRange(0.0F, 0.5F),
                    "mjolnir");
            pool.addEntry(new LootEntryItem(ModItems.mjölnir, 1, 1, functions.toArray(new LootFunction[0]), conditions.toArray(new LootCondition[0]), "mjolnir"));
            event.getTable().addPool(pool);
            LogHelper.log(Reference.MOD_ID, Level.INFO, "Injecting Mjölnir into LootTable: \"" + event.getName().toString() +"\"");
        }
    }
}
