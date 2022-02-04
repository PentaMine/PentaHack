package net.minecraft.pentahack.gui.hud.impl;

import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.gui.hud.HudComponent;
import net.minecraft.pentahack.util.Wrapper;
import net.minecraft.pentahack.util.render.PentaFontRenderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FpsComponent extends HudComponent {

    public FpsComponent() {
        super("FPS", true);
    }

    @Override
    public void render() {
        PentaFontRenderer fr = Wrapper.fr;

        List<String> info = new ArrayList<String>(Arrays.asList(mc.debug.split(" ")));

        fr.drawStringWithShadow("FPS: " + info.get(0), 4, 100, Client.color);
        this.setRight(fr.getStringWidth("FPS: " + info.get(0)) + 4);
        this.setLeft(4);
        this.setTop(100 + fr.FONT_HEIGHT);
        this.setBottom(100);
    }
}
