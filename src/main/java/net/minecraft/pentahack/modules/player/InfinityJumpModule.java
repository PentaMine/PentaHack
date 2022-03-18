package net.minecraft.pentahack.modules.player;

import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventUpdate;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.NumberSetting;
import org.lwjgl.input.Keyboard;

public class InfinityJumpModule extends Module {

    public NumberSetting heightRequirement = new NumberSetting("height requirement", 1, 0, 5);

    public InfinityJumpModule() {
        super("InfinityJump(TM)", Keyboard.KEY_NONE, Category.PLAYER, "makes you not need block to jump from");
        this.addSettings(heightRequirement);
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate && e.isPre()) {
            if (mc.player.fallDistance > heightRequirement.value && !mc.player.onGround){
                mc.player.onGround = true;
            }
        }
    }
}
