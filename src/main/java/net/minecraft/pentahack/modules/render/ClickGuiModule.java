package net.minecraft.pentahack.modules.render;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventRender;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.KeyBindSetting;
import net.minecraft.pentahack.settings.Setting;
import net.minecraft.pentahack.ui.ClickGuiHandler;
import net.minecraft.pentahack.ui.HUD;
import org.lwjgl.input.Keyboard;

public class ClickGuiModule extends Module {

    public ClickGuiModule() {
        super("ClickGui", Keyboard.KEY_RSHIFT, Category.RENDER);
    }

    @Override
    public void onEnable() {
        mc.gameSettings.showDebugInfo = false;
        mc.displayGuiScreen(new ClickGuiHandler());
                //HUD.enabled = false;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.displayGuiScreen(new GuiScreen());
        mc.setIngameFocus();

        for (Module m : Client.modules){
            for (Setting s : m.settings){
                if (s instanceof KeyBindSetting){
                    ((KeyBindSetting) s).pending = false;
                }
            }
        }
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
