package net.minecraft.pentahack.modules.render;

import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventRender;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.NumberSetting;
import org.lwjgl.input.Keyboard;

public class ColorsModule extends Module {

    public ColorsModule() {
        super("Colors", Keyboard.KEY_NONE, Category.RENDER);
    }
    public NumberSetting red = new NumberSetting("red", 0, 0 , 255);
    public NumberSetting green = new NumberSetting("green", 0, 0 , 255);
    public NumberSetting blue = new NumberSetting("blue", 0, 0 , 255);
    public NumberSetting alpha = new NumberSetting("alpha", 0, 0 , 255);


    @Override
    public void onEvent(Event e) {
        if (e instanceof EventRender) {
            //Todo: link whit initiateGui method in Client.java aka the main client class
        }
        super.onEvent(e);
    }
}
