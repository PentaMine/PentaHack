package net.minecraft.pentahack.gui.hud.impl;

import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.gui.hud.HudComponent;
import net.minecraft.pentahack.settings.ModeSetting;
import net.minecraft.pentahack.util.Timer;
import net.minecraft.pentahack.util.Wrapper;
import net.minecraft.pentahack.util.render.PentaFontRenderer;
import net.minecraft.util.math.MathHelper;


public class SpeedComponent extends HudComponent {

    ModeSetting unit = new ModeSetting("unit", "b/s", "b/s", "m/s", "km/h");
    public Timer timer = new Timer();
    public double prevPosX;
    //public double prevZ;
    public double prevPosZ;
    private String message;

    public SpeedComponent() {
        super("Speed", true);
        this.addSettings(unit);
    }

    @Override
    public void render() {

        PentaFontRenderer fr = Wrapper.fr;

        if (timer.hasTimeElapsed(1000, false))
        {
            prevPosX = mc.player.prevPosX;
            prevPosZ = mc.player.prevPosZ;
        }

        final double deltaX = mc.player.posX - prevPosX;
        final double deltaZ = mc.player.posZ - prevPosZ;

        float l_Distance = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        double l_BPS = l_Distance * 20;
        double l_KMH = Math.floor(( l_Distance/1000.0f ) / ( 0.05f/3600.0f ));

        if (unit.getMode().equalsIgnoreCase("km/h")){
            message = String.format("%.1f km/h", l_KMH);
        }
        else {
            message = String.format("%.1f " + unit.getMode(), l_BPS);
        }

        fr.drawStringWithShadow(message, 4, 111, Client.color);
    }
}
