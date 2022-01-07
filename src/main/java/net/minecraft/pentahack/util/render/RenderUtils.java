package net.minecraft.pentahack.util.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderUtils {
    public static float[] RGBA(int color) {
        if ((color & -67108864) == 0) {
            color |= -16777216;
        }

        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color & 255) / 255.0F;
        float blue = (float) (color >> 8 & 255) / 255.0F;
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        return new float[]{red, green, blue, alpha};
    }

    public static void drawRect(double x, double y, double width, double height, int color) {
        Gui.drawRect(x, y, x + width, y + height, color);
    }

    public static int getRainbowWave(float seconds, float sturation, float brightness){
        float hue = (System.currentTimeMillis() % (int)(seconds * 1000)) / (float)(seconds * 1000);
        int color = Color.HSBtoRGB(hue, sturation, brightness);
        return color;
    }

    public static int getRainbowWave(float seconds, float sturation, float brightness, long index){
        float hue = ((System.currentTimeMillis() + index) % (int)(seconds * 1000)) / (float)(seconds * 1000);
        int color = Color.HSBtoRGB(hue, sturation, brightness);
        return color;
    }
}
