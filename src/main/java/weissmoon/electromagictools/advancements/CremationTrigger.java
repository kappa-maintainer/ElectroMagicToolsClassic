package weissmoon.electromagictools.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import weissmoon.electromagictools.lib.Reference;

import java.util.Map;
import java.util.Set;

/**
 * Created by Weissmoon on 2/3/21.
 */
public class CremationTrigger implements ICriterionTrigger<CremationTrigger.Instance>{

    private static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "brutalcremation");
    public static final CremationTrigger INSTANCE = new CremationTrigger();
    private final Map<PlayerAdvancements, CremationTrigger.Listeners> listeners = Maps.newHashMap();

    @Override
    public ResourceLocation getId(){
        return ID;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, Listener listener){
        CremationTrigger.Listeners CremationTrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (CremationTrigger$listeners == null){
            CremationTrigger$listeners = new CremationTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, CremationTrigger$listeners);
        }

        CremationTrigger$listeners.add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener listener) {
        CremationTrigger.Listeners usedendereyetrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (usedendereyetrigger$listeners != null){
            usedendereyetrigger$listeners.remove(listener);

            if (usedendereyetrigger$listeners.isEmpty()){
                this.listeners.remove(playerAdvancementsIn);
            }
        }
    }

    @Override
    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn){
        this.listeners.remove(playerAdvancementsIn);
    }

    @Override
    public CremationTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context){
        return new CremationTrigger.Instance();
    }

    public void trigger(EntityPlayerMP player){
        CremationTrigger.Listeners usedendereyetrigger$listeners = this.listeners.get(player.getAdvancements());

        if (usedendereyetrigger$listeners != null){
            usedendereyetrigger$listeners.trigger();
        }
    }


    public static class Instance extends AbstractCriterionInstance{
        public Instance(){
            super(CremationTrigger.ID);
        }
    }

    static class Listeners{
        private final PlayerAdvancements playerAdvancements;
        private final Set<Listener<CremationTrigger.Instance>> listeners = Sets.newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn){
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty(){
            return listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<CremationTrigger.Instance> listener){
            listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<CremationTrigger.Instance> listener){
            listeners.remove(listener);
        }

        public void trigger(){
            for (ICriterionTrigger.Listener<CremationTrigger.Instance> listener1 : Lists.newArrayList(listeners)){
                listener1.grantCriterion(playerAdvancements);
            }
        }
    }
}