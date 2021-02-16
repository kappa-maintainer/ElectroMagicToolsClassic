package weissmoon.electromagictools.event;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import thaumcraft.common.tiles.crafting.TilePedestal;
import weissmoon.core.helper.RNGHelper;
import weissmoon.electromagictools.item.ModItems;

/**
 * Created by Weissmoon on 11/17/20.
 */
public class StormCraftingTicker {

    private final World world;
    private final BlockPos pos;

    public StormCraftingTicker(World world, BlockPos pos){
        this.world = world;
        this.pos = pos;
    }


    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event){
        if(event.world == world){
            if(world.isBlockLoaded(pos)){
                TileEntity ped = world.getTileEntity(pos.down(2));
                if (ped != null && ped instanceof TilePedestal){
                    TilePedestal pedestal = (TilePedestal) ped;
                    if (pedestal.getStackInSlot(0).isEmpty()){
                        if(pedestal.getStackInSlot(0).getItem() == ModItems.stormBreaker){
                            world.spawnEntity(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), false));
                        }else{
                            world.spawnEntity(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), true));
                        }
                        unload();
                    }
                }else{
                    unload();
                }



                float chance = RNGHelper.getRNGFloat();
                if(chance > 0.95){
                    world.spawnEntity(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), true));
                }else if(chance < 0.55 && chance > 0.5){
                    world.spawnEntity(new EntityLightningBolt(world, pos.getX() + (RNGHelper.getRNGFloat() * 16), pos.getY() + (RNGHelper.getRNGFloat() * 16), pos.getZ() + (RNGHelper.getRNGFloat() * 16), false));
                }else if(chance < 0.){
                    world.spawnEntity(new EntityLightningBolt(world, pos.getX() + (RNGHelper.getRNGFloat() * 16), pos.getY() + (RNGHelper.getRNGFloat() * 16), pos.getZ() + (RNGHelper.getRNGFloat() * 16), true));
                }
            }else{
                unload();
            }
        }else{
            unload();
        }
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event){
        if(event.getWorld() == world){
            unload();
        }
    }

    private void unload(){
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}
