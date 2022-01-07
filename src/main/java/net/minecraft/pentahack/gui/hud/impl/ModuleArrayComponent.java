package net.minecraft.pentahack.gui.hud.impl;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.gui.hud.HudComponent;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.BooleanSetting;
import net.minecraft.pentahack.util.Wrapper;
import net.minecraft.pentahack.util.render.PentaFontRenderer;
import net.minecraft.pentahack.util.render.RenderUtils;
import org.darkstorm.minecraft.gui.util.RenderUtil;

import java.util.Comparator;
import java.util.List;


public class ModuleArrayComponent extends HudComponent {

    public BooleanSetting rainbowWave = new BooleanSetting("rainbowWave", true);

    public ModuleArrayComponent() {
        super("ModuleArray");
        addSettings(rainbowWave);
    }

    @Override
    public void render() {
        PentaFontRenderer fr = Wrapper.fr;
        ScaledResolution sr = new ScaledResolution(mc);

        List<Module> modules = Client.modules;
        modules.sort(Comparator.comparingInt(m -> (int) mc.pentaFontRenderer.getStringWidth(((Module) m).name)).reversed());

        int count = 0;

        for (Module m : modules){

            if (!m.toggled || (m.settings.size() > 0 && m.settings.get(m.settings.size() - 2) instanceof BooleanSetting && ((BooleanSetting) m.settings.get(m.settings.size() - 2)).enabled)){
                continue;
            }

            fr.drawString(m.name, sr.getScaledWidth() - fr.getStringWidth(m.name) - 4, 4 + count * (fr.FONT_HEIGHT + 6), rainbowWave.enabled ? RenderUtils.getRainbowWave(4, 0.8f, 1, count * 150) : Client.color);
            count++;
        }

    }
    final Comparator<Module> comparator = (first, second) -> {
        final String firstName = first.name;
        final String secondName = "";
        final float dif = RenderUtil.getStringWidth(secondName) - RenderUtil.getStringWidth(firstName);
        return (dif != 0.0f) ? ((int)dif) : secondName.compareTo(firstName);
    };
}
