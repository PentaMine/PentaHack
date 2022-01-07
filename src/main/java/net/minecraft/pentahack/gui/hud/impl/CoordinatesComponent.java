package net.minecraft.pentahack.gui.hud.impl;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.gui.hud.HudComponent;
import net.minecraft.pentahack.settings.BooleanSetting;
import net.minecraft.pentahack.settings.ModeSetting;
import net.minecraft.pentahack.util.Wrapper;
import net.minecraft.pentahack.util.render.PentaFontRenderer;


public class CoordinatesComponent extends HudComponent {

    public ModeSetting mode = new ModeSetting("Mode", "Next line", "In line", "Next line");
    public BooleanSetting netherCoordinates = new BooleanSetting("nether cords", true);


    public CoordinatesComponent() {
        super("Cords");
        addSettings(mode, netherCoordinates);
    }

    @Override
    public void render() {
        PentaFontRenderer fr = Wrapper.fr;
        ScaledResolution sr = new ScaledResolution(mc);

        String dataX = String.format("X: %.3f", mc.player.posX);
        String dataY = String.format("Y: %.3f", mc.player.posY);
        String dataZ = String.format("Z: %.3f", mc.player.posZ);

        if (netherCoordinates.enabled && mc.player.dimension == -1){
            dataX = dataX + String.format(" [%.3f]", mc.player.posX * 8);
            dataY = dataY + String.format(" [%.3f]", mc.player.posY);
            dataZ = dataZ + String.format(" [%.3f]", mc.player.posZ * 8);
        }

        else if (netherCoordinates.enabled && (mc.player.dimension == 0 || mc.player.dimension == 1)){
            dataX = dataX + String.format(" [%.3f]", mc.player.posX / 8);
            dataY = dataY + String.format(" [%.3f]", mc.player.posY);
            dataZ = dataZ + String.format(" [%.3f]", mc.player.posZ / 8);
        }

        String message = dataX + " " + dataY + " " + dataZ;

        if (mode.getMode().equalsIgnoreCase("In line")) {
            fr.drawStringWithShadow(message, sr.getScaledWidth() - (fr.getStringWidth(message) + 4), sr.getScaledHeight() - (fr.FONT_HEIGHT + 4), Client.color);
            setTop(sr.getScaledHeight() - (fr.FONT_HEIGHT + 4));
            setLeft(sr.getScaledWidth() - (fr.getStringWidth(message) + 4));

        }
        else {

            float x1 = sr.getScaledWidth() - (fr.getStringWidth(dataX) + 4);
            float x2 = sr.getScaledWidth() - (fr.getStringWidth(dataY) + 4);
            float x3 = sr.getScaledWidth() - (fr.getStringWidth(dataZ) + 4);

            fr.drawStringWithShadow(dataX, sr.getScaledWidth() - (fr.getStringWidth(dataX) + 4), sr.getScaledHeight() - ((fr.FONT_HEIGHT * 3) + 4 + 4), Client.color);
            fr.drawStringWithShadow(dataY, sr.getScaledWidth() - (fr.getStringWidth(dataY) + 4), sr.getScaledHeight() - ((fr.FONT_HEIGHT * 2) + 4 + 2), Client.color);
            fr.drawStringWithShadow(dataZ, sr.getScaledWidth() - (fr.getStringWidth(dataZ) + 4), sr.getScaledHeight() - (fr.FONT_HEIGHT + 4), Client.color);
            setTop(sr.getScaledHeight() - ((fr.FONT_HEIGHT * 3) + 4 + 4));

            this.setLeft(Math.min(Math.min(x1,x2), x3));
        }

        this.setBottom(sr.getScaledHeight() - 4);
        this.setRight(sr.getScaledWidth() - 4);

    }
}
