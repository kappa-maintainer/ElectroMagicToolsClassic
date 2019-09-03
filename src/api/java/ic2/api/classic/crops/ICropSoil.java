package ic2.api.classic.crops;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 
 * @author Speiger
 * 
 * Custom interface to add your custom Soil
 * You have to register it in the Crops API
 * 
 */
public interface ICropSoil
{
	/**
	 * Function to apply custom Nutrient for a Block
	 * @param world World the Block is in
	 * @param pos Position of the Block
	 * @return the Nutrient effect. (Vanilla dirt applies 1)
	 * @Note: Supports negative Values
	 */
	public int getNutrientEffect(World world, BlockPos pos);
	
	public int getNutrientEffect(IBlockState state);
	
	/**
	 * Function to apply custom hydration to the crops
	 * @param world World the Block is in
	 * @param pos Position of the Block
	 * @return the hydration of the soil.
	 * @Note: Supports negative values
	 */
	public int getHumidityEffect(World world, BlockPos pos);
	
	public int getHumidityEffect(IBlockState state);
}
