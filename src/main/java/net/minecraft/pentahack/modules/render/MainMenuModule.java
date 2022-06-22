package net.minecraft.pentahack.modules.render;

import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventRender;
import net.minecraft.pentahack.modules.Module;
import org.lwjgl.input.Keyboard;

public class MainMenuModule extends Module {



    /** put the module setting above this comment*/

    public MainMenuModule() {
        super("MainMenu", Keyboard.KEY_NONE, Category.RENDER, "Changes the main menu");
    }

    /**
     * triggered when the module is being turned on
     */
    @Override
    public void onEnable() {
        Client.isCustomMenu = true;
        super.onEnable();
    }

    /**
     * triggered when the module is being turned off
     */
    @Override
    public void onDisable() {
        Client.isCustomMenu = false;
        super.onDisable();
    }

    /**
     * executed every time a event is triggered
     */
    @Override
    public void onEvent(Event e) {
        if (e instanceof EventRender){

        }
    }
}
