package net.minecraft.pentahack.modules.player;

import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventPlayerDeath;
import net.minecraft.pentahack.events.listeners.EventUpdate;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.NumberSetting;
import net.minecraft.pentahack.util.Timer;
import org.lwjgl.input.Keyboard;

public class AutoRespawnModule extends Module {

    public NumberSetting delay = new NumberSetting("delay (s)", 3, 0, 10);
    /** put the module setting above this comment*/
    Timer timer = new Timer();
    boolean eventSent = false;

    public AutoRespawnModule() {
        super("AutoRespawn", Keyboard.KEY_NONE, Category.PLAYER, "Respawns you when you die");
        this.addSettings(delay);
    }

    /**
     * triggered when the module is being turned on
     *
     */
    @Override
    public void onEnable() {
        super.onEnable();
    }

    /**
     * triggered when the module is being turned off
     */
    @Override
    public void onDisable() {
        super.onDisable();
    }

    /**
     * executed every time a event is triggered
     */
    @Override
    public void onEvent(Event e) {
        if (e instanceof EventPlayerDeath){
            timer.reset();
        }

        if (e instanceof EventUpdate){

            if (eventSent && timer.hasTimeElapsed((long) delay.getValue() * 1000, true)){
                mc.player.respawnPlayer();
            }

            if (mc.player.getHealth() == 0 && !eventSent){
                eventSent = true;
                Client.onEvent(new EventPlayerDeath());
            }
            if (!(mc.player.getHealth() == 0)){
                eventSent = false;
            }
        }
    }
}
