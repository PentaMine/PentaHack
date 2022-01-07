package net.minecraft.pentahack.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventUpdate;
import net.minecraft.pentahack.settings.BooleanSetting;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Keyboard;

public class BlankModule extends Module {



    /** put the module setting above this comment*/

    public BlankModule() {
        super("Name", Keyboard.KEY_NONE, Category.PLAYER, "Module description");
    }

    /**
     * triggered when the module is being turned on
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

        }
    }
}
