package net.minecraft.pentahack.modules.render;

import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventRender;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.ui.GuiHandler;
import net.minecraft.pentahack.ui.HUD;
import net.minecraft.client.gui.*;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.List;


public class ClickGuiModule extends Module {

    public int currentTab, moduleIndex;
    public boolean expanded;
    public List<Category> categories = (Arrays.asList(Category.values()));


    public ClickGuiModule() {
        super("ClickGUI", Keyboard.KEY_RSHIFT, Category.RENDER);
    }

    public void onEnable() {
        mc.displayGuiScreen(new GuiHandler());
        HUD.enabled = false;


    }

    public void onDisable() {
        mc.displayGuiScreen(new GuiScreen());
        mc.setIngameFocus();
        HUD.enabled = true;
    }

    public void onEvent(Event e) {
        if (e instanceof EventRender) {
            HUD.enabled = false;
            if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                toggle();
            }
        }
    }


}


