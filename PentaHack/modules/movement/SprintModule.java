package net.minecraft.PentaHack.modules.movement;

import net.minecraft.PentaHack.events.Event;
import net.minecraft.PentaHack.events.listeners.EventUpdate;
import net.minecraft.PentaHack.modules.Module;
import net.minecraft.PentaHack.settings.BooleanSetting;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class SprintModule extends Module {

    public BooleanSetting hidden = new BooleanSetting("Hidden", false);
    public SprintModule() {
        super("Sprint", Keyboard.KEY_N, Category.MOVEMENT);
        this.addSettings(hidden);
    }

    public void onEnable() {

    }

    public void onDisable() {
        mc.player.setSprinting(false);
    }

    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre()) {

                if (Keyboard.isKeyDown(17) && !mc.player.isSneaking() && !mc.player.isCollidedHorizontally) {
                    mc.player.setSprinting(true);

                }
            }
        }
    }

}
