package weissmoon.electromagictools;

import net.minecraftforge.common.config.Config;
import weissmoon.electromagictools.lib.Reference;

@Config(modid = Reference.MOD_ID)
public class EMTConfig {
    @Config.Name("Obsidian Pillar Generation Rate")
    @Config.Comment("Divide by 1000")
    @Config.RequiresMcRestart
    public static int ObsidianPillarRate = 1;
}
