package net.minecraft.pentahack.util.render;

// Credit: https://github.com/HyperiumClient/Hyperium/blob/mcgradle/src/main/java/cc/hyperium/utils/HyperiumFontRenderer.java

/*
 *       Copyright (C) 2018-present Hyperium <https://hyperium.cc/>
 *
 *       This program is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU Lesser General Public License as published
 *       by the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class PentaFontRenderer{

    private static final Pattern COLOR_CODE_PATTERN = Pattern.compile("§[0123456789abcdefklmnor]");
    public final int FONT_HEIGHT = 9;
    private final Map<String, Float> cachedStringWidth = new HashMap<>();
    private float antiAliasingFactor;
    private UnicodeFont unicodeFont;
    private int prevScaleFactor = new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor();
    private final String name;
    private final float size;

    public PentaFontRenderer(String fontName, float fontSize) {
        name = fontName;
        size = fontSize;
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());

        try {
            prevScaleFactor = resolution.getScaleFactor();
            unicodeFont = new UnicodeFont(
                    getFontByName(fontName).deriveFont(fontSize * prevScaleFactor / 2));
            unicodeFont.addAsciiGlyphs();
            unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
            unicodeFont.loadGlyphs();
        } catch (FontFormatException | IOException | SlickException e) {
            e.printStackTrace();
        }

        this.antiAliasingFactor = resolution.getScaleFactor();
    }

    public PentaFontRenderer(Font font) {
        this(font.getFontName(), font.getSize());
    }

    public PentaFontRenderer(String fontName, int fontType, int size) {
        this(new Font(fontName, fontType, size));
    }

    private Font getFontByName(String name) throws IOException, FontFormatException {
        if (name.equalsIgnoreCase("roboto condensed")) {
            return getFontFromInput("/assets/PentaHack/fonts/RobotoCondensed-Regular.ttf");
        } else if (name.equalsIgnoreCase("roboto")) {
            return getFontFromInput("/assets/PentaHack/fonts/Roboto-Regular.ttf");
        } else if (name.equalsIgnoreCase("roboto medium")) {
            return getFontFromInput("/assets/PentaHack/fonts/Roboto-Medium.ttf");
        } else if (name.equalsIgnoreCase("montserrat")) {
            return getFontFromInput("/assets/PentaHack/fonts/Montserrat-Regular.ttf");
        } else if (name.equalsIgnoreCase("segoeui") || name.equalsIgnoreCase("segoeui light")) {
            return getFontFromInput("/assets/PentaHack/fonts/SegoeUI-Light.ttf");
        } else if (name.equalsIgnoreCase("raleway")) {
            return getFontFromInput("/assets/PentaHack/fonts/Raleway-SemiBold.ttf");
        }else if (name.equalsIgnoreCase("yantramanav")) {
            return getFontFromInput("/assets/PentaHack/fonts/Yantramanav-Light.ttf");
        } else {
            // Need to return the default font.
            return getFontFromInput("/assets/PentaHack/fonts/SegoeUI-Light.ttf");
        }
    }

    private Font getFontFromInput(String path) throws IOException, FontFormatException {
        return Font.createFont(Font.TRUETYPE_FONT, PentaFontRenderer.class.getResourceAsStream(path));
    }

    public int drawString(String text, float x, float y, int color) {
        if (text == null) {
            return 0;
        }

        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());

        try {
            if (resolution.getScaleFactor() != prevScaleFactor) {
                prevScaleFactor = resolution.getScaleFactor();
                unicodeFont = new UnicodeFont(getFontByName(name).deriveFont(size * prevScaleFactor / 2));
                unicodeFont.addAsciiGlyphs();
                unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
                unicodeFont.loadGlyphs();
            }
        } catch (FontFormatException | IOException | SlickException e) {
            e.printStackTrace();
        }

        this.antiAliasingFactor = resolution.getScaleFactor();

        GlStateManager.pushMatrix();
        GlStateManager.scale(1 / antiAliasingFactor, 1 / antiAliasingFactor, 1 / antiAliasingFactor);
        x *= antiAliasingFactor;
        y *= antiAliasingFactor;
        float originalX = x;
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        GlStateManager.color(red, green, blue, alpha);

        char[] characters = text.toCharArray();

        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        String[] parts = COLOR_CODE_PATTERN.split(text);
        int index = 0;
        for (String s : parts) {
            for (String s2 : s.split("\n")) {
                for (String s3 : s2.split("\r")) {

                    unicodeFont.drawString(x, y, s3, new org.newdawn.slick.Color(color));
                    x += unicodeFont.getWidth(s3);

                    index += s3.length();
                    if (index < characters.length && characters[index] == '\r') {
                        x = originalX;
                        index++;
                    }
                }
                if (index < characters.length && characters[index] == '\n') {
                    x = originalX;
                    y += getHeight(s2) * 2;
                    index++;
                }
            }
        }

        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.popMatrix();
        return (int) x;
    }

    public int drawStringWithShadow(String text, float x, float y, int color) {
        drawString(StringUtils.stripControlCodes(text), x + 0.5F, y + 0.5F, 0x000000);
        return drawString(text, x, y, color);
    }

    public void drawCenteredString(String text, float x, float y, int color) {
        drawString(text, x - ((int) getStringWidth(text) >> 1), y, color);
    }

    public float getStringWidth(String text) {
        if (cachedStringWidth.size() > 1000) {
            cachedStringWidth.clear();
        }
        return cachedStringWidth.computeIfAbsent(text,
                e -> unicodeFont.getWidth(ChatColor.stripColor(text)) / antiAliasingFactor);
    }

    public float getHeight(String s) {
        return unicodeFont.getHeight(s) / 2.0F;
    }

    public UnicodeFont getFont() {
        return unicodeFont;
    }

    public List<String> splitString(String text, int wrapWidth) {
        List<String> lines = new ArrayList<>();

        String[] splitText = text.split(" ");
        StringBuilder currentString = new StringBuilder();

        for (String word : splitText) {
            String potential = currentString + " " + word;

            if (getStringWidth(potential) >= wrapWidth) {
                lines.add(currentString.toString());
                currentString = new StringBuilder();
            }

            currentString.append(word).append(" ");
        }

        lines.add(currentString.toString());
        return lines;
    }

    public float getCharWidth(char c)
    {
        return unicodeFont.getWidth(String.valueOf(c));
    }
}