package weissmoon.electromagictools.block.tile;

import ic2.api.energy.prefab.BasicSource;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergySource;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import weissmoon.electromagictools.api.ISolarRequirements;
import weissmoon.electromagictools.block.solar.SolarBlockBase;

import static java.lang.System.out;

/**
 * Created by Weissmoon on 9/7/20.
 */
public class SolarTileEntity extends TileEntity implements ITickable, IEnergySource {

    private ISolarRequirements requirements;
    private BasicSource energySource;
    private boolean loaded;
    private BlockPos upPos;

    public SolarTileEntity(){
//        super(3000, 1);
        requirements = SolarRegistry.defaultRequirements;
        energySource = new BasicSource(this, 3000,1);
    }

    @Override
    public void update() {
        if(!loaded) {
            loadProperties();
            loaded = true;
            upPos = pos.up();
        }
        if(!world.isRemote && requirements.canGenerate(world, upPos)){
            energySource.setEnergyStored(requirements.getEnergyPerTick(world, pos));
        }
    }

    private void loadProperties(){
        IBlockState state = this.world.getBlockState(this.pos);
        if((!(state.getBlock() instanceof SolarBlockBase)))
            return;

        int tileID = state.getValue(SolarBlockBase.ELEMENT);
        if(state.getValue(SolarBlockBase.COMPRESSED))
            tileID += 8;
        if(!((SolarBlockBase)state.getBlock()).compressedPanel){
            switch (tileID){
                case 0:
                    requirements = SolarRegistry.compressed1;   break;
                case 1:
                    requirements = SolarRegistry.aqua1;         break;
                case 2:
                    requirements = SolarRegistry.perditio1;     break;
                case 3:
                    requirements = SolarRegistry.ordo1;         break;
                case 4:
                    requirements = SolarRegistry.ignis1;        break;
                case 5:
                    requirements = SolarRegistry.aer1;          break;
                case 6:
                    requirements = SolarRegistry.terra1;        break;
                case 8:
                    requirements = SolarRegistry.compressed2;   break;
                case 9:
                    requirements = SolarRegistry.aqua2;         break;
                case 10:
                    requirements = SolarRegistry.perditio2;     break;
                case 11:
                    requirements = SolarRegistry.ordo2;         break;
                case 12:
                    requirements = SolarRegistry.ignis2;        break;
                case 13:
                    requirements = SolarRegistry.aer2;          break;
                case 14:
                    requirements = SolarRegistry.terra2;        break;
                default:
                    requirements = SolarRegistry.defaultRequirements;
            }
        }else{
            switch (tileID){
                case 0:
                    requirements = SolarRegistry.compressed3;   break;
                case 1:
                    requirements = SolarRegistry.aqua3;         break;
                case 2:
                    requirements = SolarRegistry.perditio3;     break;
                case 3:
                    requirements = SolarRegistry.ordo3;         break;
                case 4:
                    requirements = SolarRegistry.ignis3;        break;
                case 5:
                    requirements = SolarRegistry.aer3;          break;
                case 6:
                    requirements = SolarRegistry.terra3;        break;
                default:
                    requirements = SolarRegistry.defaultRequirements;
                    out.println("Broken Solar Panel at:");
                    out.println(pos);
            }
        }
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
        return energySource.getOfferedEnergy();
    }

    @Override
    public void drawEnergy(double amount) {
       energySource.drawEnergy(amount);
    }

    @Override
    public int getSourceTier() {
        return 1;
    }

    @Override
    public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing side) {
        return side != EnumFacing.UP;
    }
}
