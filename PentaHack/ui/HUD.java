package net.minecraft.PentaHack.ui;

import net.minecraft.PentaHack.Client;
import net.minecraft.PentaHack.events.listeners.EventRender;
import net.minecraft.PentaHack.modules.Module;
import net.minecraft.PentaHack.modules.render.ClickGuiModule;
import net.minecraft.PentaHack.settings.BooleanSetting;
import net.minecraft.PentaHack.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

import java.util.Comparator;

public class HUD {

    public Minecraft mc = Minecraft.getMinecraft();
    public static boolean enabled = true;
    public static boolean ttf = false;



    public void draw() {
        ScaledResolution sr = new ScaledResolution(mc);


        FontRenderer fr = mc.fontRendererObj;


        if (enabled) {
            Client.modules.sort(Comparator.comparingInt(m -> mc.fontRendererObj.getStringWidth(((Module) m).name)).reversed());

            fr.drawStringWithShadow(Client.name + " v" + Client.version, 4, 4, 0xffbf2808);


            int count = 0;
            for (Module m : Client.modules) {




                if (m.settings.size() > 0 && (m.settings.get(m.settings.size() - 1) instanceof BooleanSetting) && ((BooleanSetting) m.settings.get(m.settings.size() - 1)).isEnabled()){
                    continue;
                }

                if (!m.toggled){
                    continue;
                }


                fr.drawString(m.name, sr.getScaledWidth() - fr.getStringWidth(m.name) - 4, 4 + count * (fr.FONT_HEIGHT + 6), RenderUtils.getRainbowWave(4, 0.8f, 1, count * 150));
                count++;
            }

        }
        EventRender e = new EventRender();
        Client.onEvent(e);
    }
}
