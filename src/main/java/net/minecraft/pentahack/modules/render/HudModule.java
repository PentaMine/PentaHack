package net.minecraft.pentahack.modules.render;

import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventRender;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.BooleanSetting;
import net.minecraft.pentahack.ui.HUD;
import org.lwjgl.input.Keyboard;

public class HudModule extends Module {

    public BooleanSetting ttf = new BooleanSetting("ttf", false);
    public BooleanSetting hidden = new BooleanSetting("Hidden", true);

    public static boolean dd;

    public HudModule() {
        super("HUD", Keyboard.KEY_O, Category.RENDER);
        this.addSettings(ttf, hidden);
        toggled = true;
        onEnable();
    }



    public void onEnable() {
        HUD.enabled = false;

    }

    public void onDisable() {
        HUD.enabled = true;
    }


    public void onEvent(Event e) {
        if (e instanceof EventRender){
            if (ttf.enabled){
                HUD.ttf = true;
            }
            else {
                HUD.ttf = false;
            }
        }
    }
}
