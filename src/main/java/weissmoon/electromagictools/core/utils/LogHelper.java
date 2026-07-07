package weissmoon.electromagictools.core.utils;

import org.apache.logging.log4j.Level;
import net.minecraftforge.fml.common.FMLLog;

public class LogHelper{
    public static void log (String modID, Level level, Object object){

        FMLLog.log(modID, level, String.valueOf(object));
    }
}