package net.minecraft.PentaHack.modules.player;

import net.minecraft.PentaHack.events.Event;
import net.minecraft.PentaHack.events.listeners.EventUpdate;
import net.minecraft.PentaHack.modules.Module;
import net.minecraft.PentaHack.settings.BooleanSetting;
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
