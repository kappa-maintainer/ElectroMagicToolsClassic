package weissmoon.electromagictools.block.tile;

import ic2.api.energy.prefab.BasicSource;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.info.ILocatable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.common.lib.events.EssentiaHandler;

/**
 * Created by Weissmoon on 9/24/20.
 */
public class EssentiaGeneratorBase extends TileEntity implements ITickable, IEnergySource, ILocatable{

    private String BURN_TIME_NBT_KEY;
    int pulseTimer = 20;
    private int fade = 0;
    private BasicSource energySource;
    private Aspect aspect;

    public EssentiaGeneratorBase(int essentia){
        super();
        if(essentia == 1)
            aspect = Aspect.AIR;
        else if(essentia == 2)
            aspect = Aspect.AURA;
        else if(essentia == 3)
            aspect = Aspect.FIRE;
        else
            aspect = Aspect.ENERGY;
        energySource = new BasicSource((ILocatable)this, 100000000,3);
    }


    @Override
    public void update() {
        if(pulseTimer <= 0) {
            double energy = 0;
//            int i = 0;
//            while(i < 512) {
//                if (EssentiaHandler.drainEssentia(this, Aspect.ENERGY, null, 8, false, 512)) {
//                    energy += 66.66666666;
//                    i++;
//                }
//                else
//                    i = 512;
//            }

//            if(EssentiaHandler.findEssentia(this, aspect, null, 4, true)){
                int range = 4;
                int start = -4;

                aaer:
                for(int aa = -range; aa <= range; ++aa) {
                    for(int bb = -range; bb <= range; ++bb) {
                        for(int cc = start; cc < range; ++cc) {
                            if (aa != 0 || bb != 0 || cc != 0) {
                                if(energySource.getFreeCapacity() < 65)
                                    break aaer;
                                int xx = pos.getX() + aa;
                                int yy = pos.getY() + cc;
                                int zz = pos.getZ() + bb;
                                TileEntity te = null;
                                try {
                                    te = world.getTileEntity(new BlockPos(xx, yy, zz));
                                }catch (Error ignored){

                                }
                                if (te instanceof IAspectSource && ((IAspectSource)te).doesContainerContainAmount(aspect, 1) && !((IAspectSource)te).isBlocked()) {
                                    ((IAspectSource) te).takeFromContainer(aspect, 1);
                                    energy += 66.66666666;
                                    fade = 40;
//                                    if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
//                                        String key = System.currentTimeMillis() + ":" + pos.getX() + ":" + pos.getY() + ":" + pos.getZ() + ":" + xx + ":" + yy + ":" + zz + ":" + aspect.getColor();
//                                        EssentiaHandler.sourceFX.put(key, new EssentiaHandler.EssentiaSourceFX(pos, new BlockPos(xx, yy, zz), aspect.getColor(), 1));
//                                    }
                                }
                            }
                        }
                    }
                }
//            }
            energySource.addEnergy(energy);
            pulseTimer = 20;
        }else{
            pulseTimer--;
            if(fade > 0)
                fade--;
        }
    }

    public Aspect getAspect(){
        return aspect;
    }

    public int getFade(){
        return fade;
    }

    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        energySource.readFromNBT(compound);
        pulseTimer = compound.getByte(BURN_TIME_NBT_KEY);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        compound.setByte(BURN_TIME_NBT_KEY, (byte) pulseTimer);
        energySource.writeToNBT(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public void onLoad(){
        super.onLoad();
        energySource.onLoad();
    }

    @Override
    public void invalidate(){
        energySource.invalidate();
        super.invalidate();
    }

    @Override
    public void onChunkUnload(){
        energySource.onChunkUnload();
        super.onChunkUnload();
    }

    @Override
    public double getOfferedEnergy() {
        return Math.max(energySource.getOfferedEnergy(), 32768);
    }

    @Override
    public void drawEnergy(double amount) {
        energySource.drawEnergy(amount);
    }

    @Override
    public int getSourceTier() {
        return 3;
    }

    @Override
    public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing side) {
        return true;
    }

    @Override
    public BlockPos getPosition(){
        return pos;
    }

    @Override
    public World getWorldObj(){
        return world;
    }
}
