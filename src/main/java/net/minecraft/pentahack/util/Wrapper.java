package net.minecraft.pentahack.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.pentahack.util.render.PentaFontRenderer;

public class Wrapper {
    public static FontRenderer mfr = Minecraft.getMinecraft().fontRendererObj;
    public static ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
    public static PentaFontRenderer fr = new PentaFontRenderer("roboto", 20.0f);
}
