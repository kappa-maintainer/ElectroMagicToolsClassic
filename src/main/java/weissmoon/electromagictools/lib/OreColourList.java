package weissmoon.electromagictools.lib;

import com.google.common.collect.Maps;
import net.minecraftforge.fml.common.Loader;
import org.apache.logging.log4j.Level;
import weissmoon.core.utils.LogHelper;

import javax.annotation.Nonnull;
import java.util.IdentityHashMap;

/**
 * Created by Weissmoon on 2/8/21.
 */
public class OreColourList{

    private static final IdentityHashMap<String, Integer> oreColours = Maps.newIdentityHashMap();

    public static boolean addOreList(@Nonnull String oreName, @Nonnull int oreColour){
        return addOreList(oreName, oreColour, false);
    }

    public static boolean addOreList(@Nonnull String oreName, @Nonnull int oreColour, boolean override){
        if(oreColours.containsKey(oreName)){
            if(override){
                LogHelper.log(Reference.MOD_ID, Level.WARN, "Ore colour for " + oreName.toLowerCase() + " is being overridden by \"" + Loader.instance().activeModContainer().getModId() +"\"");
                LogHelper.log(Reference.MOD_ID, Level.WARN, "new value for " + oreName.toLowerCase() + " is " + oreColour);
            }else{
                return false;
            }
        }
        oreColours.put(oreName.toLowerCase(), oreColour);
        return true;
    }

    public static int getColour(String name){
        if(oreColours.containsKey(name.toLowerCase()))
            return oreColours.get(name.toLowerCase());
        return 12632256;
    }

    public static String[] getNames(){
        return oreColours.keySet().toArray(new String[0]);
    }

    static{
        addOreList("IRON", 14200723);
        addOreList("LAPIS", 1328572);
        addOreList("GOLD", 16576075);
        addOreList("DIAMOND", 6155509);
        addOreList("EMERALD", 1564002);
        addOreList("REDSTONE", 16711680);
        addOreList("COAL", 1052688);
        addOreList("silver", 0xa5d0e5);//14342653
        addOreList("TIN", 15724539);
        addOreList("COPPER", 16620629);
        addOreList("AMBER", 16626469);
        addOreList("CINNABAR", 10159368);
        addOreList("QUARTZ", 15064789);
        addOreList("uranium", 0x60b010);
    }
}
