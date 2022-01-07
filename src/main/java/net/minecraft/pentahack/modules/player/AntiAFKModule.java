package net.minecraft.pentahack.modules.player;

import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventUpdate;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.BooleanSetting;

import net.minecraft.pentahack.util.Timer;
import org.lwjgl.input.Keyboard;


public class AntiAFKModule extends Module {

    public BooleanSetting random = new BooleanSetting("random rotations", false);
    /** put the module setting above this comment*/

    Timer timer = new Timer();

    public AntiAFKModule() {
        super("AntiAFK", Keyboard.KEY_NONE, Category.PLAYER, "Prevents the player from getting AFK kicked");

        this.addSettings(random);
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
        if (e instanceof EventUpdate){
            if (timer.hasTimeElapsed(300, true)) {

                mc.player.rotationYaw = mc.player.rotationYaw + 10;
                if (random.isEnabled()){
                    mc.player.rotationYaw = mc.player.rotationYaw + (float) Math.random() * 100;
                }
            }
        }
    }
}
