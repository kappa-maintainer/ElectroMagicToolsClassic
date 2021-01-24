package weissmoon.electromagictools.block.tile;

import ic2.api.energy.prefab.BasicSink;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.items.ItemStackHandler;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.items.IRechargable;
import thaumcraft.api.items.RechargeHelper;
import thaumcraft.client.fx.FXDispatcher;
import weissmoon.electromagictools.network.PacketHandler;
import weissmoon.electromagictools.network.TileUpdatePacket;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Weissmoon on 11/27/20.
 */
public class TileIndustrialChargePedestal extends TileEntity implements ITickable, IEnergySink, IAspectContainer{

    private Limithadler itemStackHandler = new Limithadler();
    private BasicSink energySink;
    private int charging = 0;
    private boolean hasItem = false;
    private static final AspectList EMPTY_ASPECT_LIST = new AspectList();
    private final AspectList ASPECT_LIST = new AspectList();
    private BlockPos UP_POS;
    private IBlockState blockstate;
    private static final float R = 192f / 255f;
    private static final float G = 1;
    private static final float B = 1;

    public TileIndustrialChargePedestal(){
        super();
        energySink = new BasicSink(this, 10000000, 4);
    }

    @Override
    public double getDemandedEnergy() {
        return energySink.getDemandedEnergy();
    }

    @Override
    public int getSinkTier() {
        return Integer.MAX_VALUE;
    }

    @Override
    public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
        return energySink.injectEnergy(directionFrom, amount, voltage);
    }

    @Override
    public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing side) {
        return side != EnumFacing.UP;
    }

    @Override
    public void update() {
        ItemStack stack = itemStackHandler.getStackInSlot(0);
        if(stack.getItem() instanceof IRechargable){
//            if(charging < 30){
//                charging++;
//                hasItem = true;
//                return;
//            }
            if(energySink.getEnergyStored() >= 10000){
                int vis = 1;
                b:
                if(energySink.getEnergyStored() >= 50000) {
                    vis = 5;
                    break b;
                }else if(energySink.getEnergyStored() >= 20000) {
                        vis++;
                        if (energySink.getEnergyStored() >= 30000) {
                            vis++;
                            if (energySink.getEnergyStored() >= 40000) {
                                vis++;
                            }
                        }
                    }
                IRechargable item = (IRechargable) stack.getItem();
//            int maxCharge = item.getMaxCharge(stack, null);
                float charge = RechargeHelper.rechargeItemBlindly(stack, null, vis);
                if(charge > 0){
                    energySink.useEnergy(10000 * charge);
                    itemStackHandler.updateInSlot(0, stack);
                    if(blockstate == null)
                        blockstate = world.getBlockState(pos);
                    world.setBlockState(pos, blockstate, 2);
                    markDirty();
                    if(!world.isRemote){
                        FXDispatcher.INSTANCE.drawWispyMotes(pos.getX() + .5, pos.getY() + .9, pos.getZ() + .1, 0, vis * 0.01, vis * 0.01, 9, R, G, B, 0);
                        FXDispatcher.INSTANCE.drawWispyMotes(pos.getX() + .5, pos.getY() + .9, pos.getZ() + .9, 0, vis * 0.01, vis * -.01, 9, R, G, B, 0);
                        FXDispatcher.INSTANCE.drawWispyMotes(pos.getX() + .1, pos.getY() + .9, pos.getZ() + .5, vis * 0.01, vis * 0.01, 0, 9, R, G, B, 0);
                        FXDispatcher.INSTANCE.drawWispyMotes(pos.getX() + .9, pos.getY() + .9, pos.getZ() + .5, vis * -.01, vis * 0.01, 0, 9, R, G, B, 0);
                    }
                }
            }
//        }else{
//            if(charging > 0){
//                hasItem = false;
//                charging--;
//            }
        }
    }

    public int getCharging(){
        return charging;
    }

    public boolean hasItem(){
        return hasItem;
    }

    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket(){
//        return new SPacketUpdateTileEntity(this.pos, -9, this.getUpdateTag());
        return null;
    }

    @Override
    public NBTTagCompound getUpdateTag(){
        NBTTagCompound compound = new NBTTagCompound();
        //super.writeToNBT(compound);
        compound.setInteger("x", pos.getX());
        compound.setInteger("y", pos.getY());
        compound.setInteger("z", pos.getZ());
        compound.setTag("items", itemStackHandler.serializeNBT());
        return compound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound compound){
        super.readFromNBT(compound);
        if(compound.hasKey("items")) {
            itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemStackHandler.serializeNBT());
        energySink.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if(compound.hasKey("items")) {
            itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        }
        energySink.readFromNBT(compound);
    }

    @Override
    public void onLoad(){
        super.onLoad();
        energySink.onLoad();
        this.UP_POS = pos.up();
    }

    @Override
    public void invalidate(){
        energySink.invalidate();
        super.invalidate();
    }

    @Override
    public void onChunkUnload(){
        energySink.onChunkUnload();
        super.onChunkUnload();
    }

    public ItemStackHandler getItemStackHandler() {
        return itemStackHandler;
    }

    @Override
    public AspectList getAspects() {
        if(itemStackHandler.getStackInSlot(0).getItem() instanceof IRechargable){
            for(Aspect aspect: ASPECT_LIST.getAspects()){
                ASPECT_LIST.remove(aspect);
            }
            int i = RechargeHelper.getCharge(itemStackHandler.getStackInSlot(0));
            ASPECT_LIST.add(Aspect.ENERGY, i);
            return ASPECT_LIST;
        }
        return EMPTY_ASPECT_LIST;
    }

    @Override
    public void setAspects(AspectList aspects) {}

    @Override
    public boolean doesContainerAccept(Aspect tag) {
        return false;
    }

    @Override
    public int addToContainer(Aspect tag, int amount) {
        return amount;
    }

    @Override
    public boolean takeFromContainer(Aspect tag, int amount) {
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList ot) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect tag, int amount) {
        return false;
    }

    @Override
    public boolean doesContainerContain(AspectList ot) {
        return false;
    }

    @Override
    public int containerContains(Aspect tag) {
        return 0;
    }

    public BlockPos getUP_POS() {
        return UP_POS;
    }


    private class Limithadler extends ItemStackHandler{
        public Limithadler() {
            super(1);
        }

        @Override
        protected int getStackLimit(int slot, @Nonnull ItemStack stack){
            return 1;
        }


        @Override
        public void setStackInSlot(int slot, @Nonnull ItemStack stack)
        {
            validateSlotIndex(slot);
            if(this.stacks.get(slot).getCount() < 1) {
                this.stacks.set(slot, stack);
                onContentsChanged(slot);
            }
        }

        public void updateInSlot(int slot, @Nonnull ItemStack stack)
        {
            validateSlotIndex(slot);
            this.stacks.set(slot, stack);
            onContentsChanged(slot);
        }

        @Override
        public void onContentsChanged(int slot){
            if(world instanceof WorldServer){
//                SPacketUpdateTileEntity packet = getUpdatePacket();
                IMessage packet = new TileUpdatePacket(pos, getUpdateTag());

                if(packet != null) {
//                    PlayerChunkMapEntry chunk = ((WorldServer) world).getPlayerChunkMap().getEntry(getPos().getX() >> 4, getPos().getZ() >> 4);
//                    if(chunk != null) {
//                        chunk.sendPacket(packet);
                        PacketHandler.INSTANCE.sendToAllTracking(packet, new NetworkRegistry.TargetPoint( world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 40));
//                    }
                }
            }
            markDirty();
        }

        @Override
        public boolean isItemValid(int index, ItemStack stack) {
            return stack.getItem() instanceof IRechargable;
        }
    }
}
