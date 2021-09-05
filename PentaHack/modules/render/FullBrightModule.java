package net.minecraft.PentaHack.modules.render;

import net.minecraft.PentaHack.modules.Module;
import net.minecraft.PentaHack.settings.BooleanSetting;
import org.lwjgl.input.Keyboard;

public class FullBrightModule extends Module {

    public BooleanSetting hidden = new BooleanSetting("Hidden", false);
    public FullBrightModule() {
        super("FullBright", Keyboard.KEY_O, Category.RENDER);
        this.addSettings(hidden);
        mc.gameSettings.gammaSetting = 0;
    }

    public void onEnable() {
        mc.gameSettings.gammaSetting = 100;
    }

    public void onDisable() {
        mc.gameSettings.gammaSetting = 0;
    }

}
