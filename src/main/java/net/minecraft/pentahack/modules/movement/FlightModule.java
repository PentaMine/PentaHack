package net.minecraft.pentahack.modules.movement;

import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventUpdate;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.BooleanSetting;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class FlightModule extends Module {
    public Minecraft mc = Minecraft.getMinecraft();

    public FlightModule() {
        super("Flight", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    @Override
    public void onDisable() {
        mc.player.capabilities.isFlying = false;
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre()) {
                mc.player.capabilities.isFlying = true;
            }
        }
    }
}


