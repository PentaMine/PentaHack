package net.minecraft.pentahack.modules.render;

import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventRender;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.BooleanSetting;
import net.minecraft.pentahack.ui.ClickGuiHandler;
import net.minecraft.pentahack.ui.GuiHudManager;
import org.lwjgl.input.Keyboard;

public class HudModule extends Module {

    public BooleanSetting ttf = new BooleanSetting("ttf", false);

    public HudModule() {
        super("HUD", Keyboard.KEY_NONE, Category.RENDER);
        this.addSettings(ttf);
        toggle();
    }

    @Override
    public void onEnable() {
        Client.hud.enabled = true;
    }

    @Override
    public void onDisable() {
        Client.hud.enabled = false;
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventRender) {
            Client.hud.ttf = ttf.enabled;

            if (mc.gameSettings.showDebugInfo || (!(mc.currentScreen == null || mc.currentScreen instanceof ClickGuiHandler || mc.currentScreen instanceof GuiHudManager))) {
                Client.hud.enabled = false;
            } else {
                Client.hud.enabled = true;
            }
        }
    }
}
