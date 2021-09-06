package net.minecraft.pentahack.modules.player;

import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventUpdate;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.BooleanSetting;
import org.lwjgl.input.Keyboard;

public class NoFallModule extends Module {

    public BooleanSetting hidden = new BooleanSetting("Hidden", false);
    public NoFallModule() {
        super("NoFall", Keyboard.KEY_I, Category.PLAYER);
        this.addSettings(hidden);
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate && e.isPre()) {
            if (mc.player.fallDistance > 2) ;
            {

            }
        }
    }
}
