package net.minecraft.PentaHack.ui;

import net.minecraft.PentaHack.Client;
import net.minecraft.client.Minecraft;
import org.darkstorm.minecraft.gui.GuiManager;
import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.util.GuiManagerDisplayScreen;

public class UiRenderer {

    public static void renderAndUpdateFrames(){
        for(Frame f : Client.guiManager.getFrames()){
            f.update();
        }
        for(Frame f : Client.guiManager.getFrames()){
            if (f.isPinned() || Minecraft.getMinecraft().currentScreen instanceof GuiManagerDisplayScreen){
                f.render();
            }
        }
    }
}
