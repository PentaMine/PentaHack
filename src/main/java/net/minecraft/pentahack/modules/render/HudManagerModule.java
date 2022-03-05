package net.minecraft.pentahack.modules.render;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventRender;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.ui.GuiHudManager;
import org.lwjgl.input.Keyboard;

public class HudManagerModule extends Module {

    public HudManagerModule() {
        super("HudManager", Keyboard.KEY_NONE, Category.RENDER);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(new GuiHudManager());
        //HUD.enabled = false;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.displayGuiScreen(new GuiScreen());
        mc.setIngameFocus();
        //HUD.enabled = true;
        super.onDisable();
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventRender) {
            //HUD.enabled = false;
            if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                toggle();
            }
        }
        super.onEvent(e);
    }
}
