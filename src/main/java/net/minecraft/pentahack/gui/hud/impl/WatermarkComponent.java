package net.minecraft.pentahack.gui.hud.impl;

import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.gui.hud.HudComponent;
import net.minecraft.pentahack.util.Wrapper;
import net.minecraft.pentahack.util.render.PentaFontRenderer;


public class WatermarkComponent extends HudComponent {

    public WatermarkComponent() {
        super("Watermark");

    }

    @Override
    public void render() {

        PentaFontRenderer fr = Wrapper.fr;

        fr.drawStringWithShadow(Client.name + " v" + Client.version, 4, 4, Client.color);
    }
}
