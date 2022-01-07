package net.minecraft.pentahack.gui.hud.impl;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.gui.hud.HudComponent;
import net.minecraft.pentahack.settings.BooleanSetting;
import net.minecraft.pentahack.util.Wrapper;
import net.minecraft.pentahack.util.render.PentaFontRenderer;
import net.minecraft.util.math.MathHelper;


public class YawComponent extends HudComponent {

    public BooleanSetting pitch = new BooleanSetting("pitch", false);

    public YawComponent() {
        super("Yaw", true);
        this.addSettings(pitch);
    }

    @Override
    public void render() {
        PentaFontRenderer fr = Wrapper.fr;
        ScaledResolution sr = new ScaledResolution(mc);

        //Client.addChatMessage("" + Client.hud.components.get(2));

        String rotationYaw = String.format("Yaw: %.3f", MathHelper.wrapDegrees(mc.player.rotationYaw));
        String rotationPitch = String.format("Pitch: %.3f", MathHelper.wrapDegrees(mc.player.rotationPitch));

        String message = rotationYaw;

        if (pitch.enabled) {
            message = rotationYaw + " " + rotationPitch;
        }

        fr.drawStringWithShadow(message, sr.getScaledWidth() - 4 - fr.getStringWidth(message), (float) (Client.hud.components.get(2).top - 11), Client.color);
    }
}
