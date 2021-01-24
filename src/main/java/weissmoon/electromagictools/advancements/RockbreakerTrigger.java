package weissmoon.electromagictools.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import weissmoon.electromagictools.lib.Reference;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Weissmoon on 9/15/20.
 */
public class RockbreakerTrigger implements ICriterionTrigger<RockbreakerTrigger.Instance> {

    private static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "rockbreaker");
    public static final RockbreakerTrigger INSTANCE = new RockbreakerTrigger();
    private final Map<PlayerAdvancements, RockbreakerTrigger.Listeners> listeners = Maps.newHashMap();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener listener) {
        RockbreakerTrigger.Listeners usedendereyetrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (usedendereyetrigger$listeners == null)
        {
            usedendereyetrigger$listeners = new RockbreakerTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, usedendereyetrigger$listeners);
        }

        usedendereyetrigger$listeners.add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener listener) {
        RockbreakerTrigger.Listeners usedendereyetrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (usedendereyetrigger$listeners != null)
        {
            usedendereyetrigger$listeners.remove(listener);

            if (usedendereyetrigger$listeners.isEmpty())
            {
                this.listeners.remove(playerAdvancementsIn);
            }
        }
    }

    @Override
    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
        this.listeners.remove(playerAdvancementsIn);
    }

    @Override
    public RockbreakerTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        ItemPredicate itempredicate = ItemPredicate.deserialize(json.get("item"));
        return new RockbreakerTrigger.Instance(itempredicate);
    }

    public void trigger(EntityPlayerMP player, ItemStack item)
    {
        RockbreakerTrigger.Listeners usedendereyetrigger$listeners = this.listeners.get(player.getAdvancements());

        if (usedendereyetrigger$listeners != null)
        {
            usedendereyetrigger$listeners.trigger(item);
        }
    }

    public static class Instance extends AbstractCriterionInstance
    {
        private final ItemPredicate item;

        public Instance(ItemPredicate item)
        {
            super(RockbreakerTrigger.ID);
            this.item = item;
        }

        public boolean test(ItemStack item)
        {
            return this.item.test(item);
        }
    }

    static class Listeners
    {
        private final PlayerAdvancements playerAdvancements;
        private final Set<ICriterionTrigger.Listener<RockbreakerTrigger.Instance>> listeners = Sets.newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn)
        {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty()
        {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<RockbreakerTrigger.Instance> listener)
        {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<RockbreakerTrigger.Instance> listener)
        {
            this.listeners.remove(listener);
        }

        public void trigger(ItemStack item)
        {
            List<ICriterionTrigger.Listener<RockbreakerTrigger.Instance>> list = null;

            for (ICriterionTrigger.Listener<RockbreakerTrigger.Instance> listener : this.listeners)
            {
                if (((RockbreakerTrigger.Instance)listener.getCriterionInstance()).test(item))
                {
                    if (list == null)
                    {
                        list = Lists.<ICriterionTrigger.Listener<RockbreakerTrigger.Instance>>newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null)
            {
                for (ICriterionTrigger.Listener<RockbreakerTrigger.Instance> listener1 : list)
                {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}
