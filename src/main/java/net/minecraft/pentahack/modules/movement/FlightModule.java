package net.minecraft.pentahack.modules.movement;

import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventUpdate;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.BooleanSetting;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class FlightModule extends Module {
    public Minecraft mc = Minecraft.getMinecraft();


    public BooleanSetting hidden = new BooleanSetting("Hidden", false);


    public FlightModule() {
        super("Flight", Keyboard.KEY_G, Category.MOVEMENT);
        this.addSettings(hidden);
    }

    /*
    public void onEnable(){
        mc.player.capabilities.isFlying = true;
        mc.player.capabilities.allowFlying = true;
    }
    */
    public void onDisable() {
        mc.player.capabilities.isFlying = false;
        //mc.player.capabilities.allowFlying = false;
    }

    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre()) {
                mc.player.capabilities.isFlying = true;
            }
        }
    }
}


