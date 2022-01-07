package net.minecraft.pentahack.modules.movement;

import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventUpdate;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.BooleanSetting;
import org.lwjgl.input.Keyboard;

public class SprintModule extends Module {

    public SprintModule() {
        super("Sprint", Keyboard.KEY_NONE, Category.MOVEMENT);
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
