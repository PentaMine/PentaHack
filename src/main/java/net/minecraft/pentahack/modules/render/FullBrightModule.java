package net.minecraft.pentahack.modules.render;

import net.minecraft.pentahack.modules.Module;
import org.lwjgl.input.Keyboard;

public class FullBrightModule extends Module {

    public FullBrightModule() {
        super("FullBright", Keyboard.KEY_NONE, Category.RENDER);
        mc.gameSettings.gammaSetting = 0;
    }

    @Override
    public void onEnable() {
        mc.gameSettings.gammaSetting = 100;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = 0;
    }

}
