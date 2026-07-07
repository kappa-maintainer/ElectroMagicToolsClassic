package weissmoon.electromagictools.core.helper;

import java.util.Random;

/**
 * Various random generator results
 */
public class RNGHelper{

    public static final Random random = new Random();

    /**
     * @return random integer value
     */
    public static int getRNGInt (){
        return random.nextInt();
    }

    /**
     * @param top highest integer value to return (must be positive)
     * @return random integer value
     */
    public static int getRNGIntClamp (int top){
        return random.nextInt(top);
    }

    /**
     * Returns an integer in a certain range.
     * @param lower minimum integer value (must be positive)
     * @param upper maximum integer value
     * @return random integer value
     */
    public static int getRNGIntClamp (int lower, int upper){
        int i = getRNGIntClamp(upper);
        while(i < lower){
            i = getRNGIntClamp(upper);
        }
        return i;
    }

    /**
     * @return random boolean value
     */
    public static Boolean getRNGBoolean (){
        return random.nextBoolean();
    }

    /**
     * @return random float value from 0.0 to 1.0
     */
    public static Float getRNGFloat (){
        return random.nextFloat();
    }

    /**
     * @return random double float value
     */
    public static Double getRNGGaussian (){
        return random.nextGaussian();
    }

    /**
     * Return Double Gaussian.
     * @param shift sets the median.
     * @param range sets the size of the of the range for both sides from the middle.
     */
    public static Double getRNGGaussianR (double shift, double range){
        return (getRNGGaussian() * range / 2.0D + shift);

    }
}
