package net.minecraft.pentahack.modules.player;

import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventUpdate;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.ModeSetting;
import org.lwjgl.input.Keyboard;

public class NoFallModule extends Module {

    public ModeSetting mode = new ModeSetting("mode", "Vanilla", "FastFall", "Vanilla");

    public NoFallModule() {
        super("NoFall", Keyboard.KEY_NONE, Category.PLAYER);
        this.addSettings(mode);
    }

    @Override
    public void onEvent(Event e) {
        //todo : fix this module
        if (e instanceof EventUpdate && e.isPre()) {
            if (mc.player.fallDistance > 3.0f && !mc.player.onGround){

                if(mode.getMode().equalsIgnoreCase("Vanilla")){
                    mc.player.onGround = true;
                }

                else if(mode.getMode().equalsIgnoreCase("FastFall")){
                    if (!mc.player.onGround) {
                        mc.player.onGround = true;

                        mc.player.posY = Math.round(mc.player.posY - mc.player.fallDistance);

                        mc.player.fallDistance = 2.9f;

                        mc.player.onGround = true;
                    }
                }
            }

        }
    }
}
