package net.minecraft.PentaHack.ui;

import net.minecraft.PentaHack.Client;
import net.minecraft.PentaHack.modules.Module;
import net.minecraft.PentaHack.settings.BooleanSetting;
import net.minecraft.PentaHack.settings.ModeSetting;
import net.minecraft.PentaHack.settings.NumberSetting;
import net.minecraft.PentaHack.settings.Setting;
import net.minecraft.PentaHack.util.MathUtils;
import net.minecraft.PentaHack.util.RenderUtils;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class GuiHandler extends GuiScreen {
    public static final int height = 13;
    public static final int width = 100;
    public static int mouseXPos;
    public static int mouseYPos;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {


        //drawDefaultBackground();
        List<Module.Category> categiries = (Arrays.asList(Module.Category.values()));


        //FontRenderer fr = mc.fontRendererObj;
        final PentaFontRenderer fr = new PentaFontRenderer("roboto condensed", 20.0f);

        int i = 1;

        int index1 = 1;


        for (Module.Category c : categiries) {
            int settingCount = 0;

            String name = c.toString().toLowerCase();
            name = Character.toUpperCase(name.charAt(0)) + name.substring(1);

            RenderUtils.drawRectRewrite(c.x, c.y, width, height, 0x9f424242);

            GlStateManager.pushMatrix();

            GlStateManager.translate(c.x, c.y, 1);
            GlStateManager.scale(1, 1, 1);
            fr.drawString(name, 3, 1, 0xffffffff);

            GlStateManager.popMatrix();
            if (c.expanded) {
                int index = 1;
                for (Module m : Client.getModuleByCategory(c)) {


                    RenderUtils.drawRectRewrite(c.x, c.y + (height * index) + (height * settingCount) + 2, width, height, 0x9f575757);

                    GlStateManager.pushMatrix();

                    GlStateManager.translate(c.x, c.y + (height * index) + (height * settingCount), 1);
                    GlStateManager.scale(1, 1, 1);
                    if (m.name.equals("ClickGUI")) {
                        fr.drawString(m.name, 3, 3, -1);
                    }
                    fr.drawString(m.name, 3, 3, m.toggled ? -1 : 0xffB6B5B5);

                    GlStateManager.popMatrix();

                    if (m.expanded && m.settings.size() > 0) {
                        for (Setting s : m.settings) {
                            settingCount++;
                            RenderUtils.drawRectRewrite(c.x, c.y + (height * index) + (height * settingCount) + 2, width, height, 0x9f575757);
                            RenderUtils.drawRectRewrite(c.x + width - 2, c.y + (height * index) + (height * settingCount) + 2, 2, height, Client.color);

                            if (s instanceof NumberSetting) {
                                double dif = MathUtils.map(((NumberSetting) s).value, ((NumberSetting) s).minimum, ((NumberSetting) s).maximum, 0, 100);


                                RenderUtils.drawRectRewrite(c.x, c.y + (height * index) + (height * settingCount) + 2, dif, height, Client.color);
                            }
                            GlStateManager.pushMatrix();

                            GlStateManager.translate(c.x, c.y + (height * index) + (height * settingCount), 1);
                            GlStateManager.scale(1, 1, 1);

                            DecimalFormat df2 = new DecimalFormat("#.##");
                            if (s instanceof BooleanSetting) {
                                fr.drawString(s.name + (((BooleanSetting) s).isEnabled() ? " [X]" : " [  ]"), 3, 3, -1);
                            } else if (s instanceof NumberSetting) {
                                //RenderUtils.drawRectRewrite(c.x, c.y + (height * index) + (height * settingCount) + 2, ((NumberSetting) s).value, height, Client.color);
                                fr.drawString(s.name + " " + df2.format(((NumberSetting) s).getValue()), 3, 3, -1);
                            } else if (s instanceof ModeSetting) {
                                fr.drawString(s.name + ((ModeSetting) s).getMode(), 3, 3, -1);
                            }
                            GlStateManager.popMatrix();
                            index1++;
                        }

                    }
                    index++;
                }
            }
            i++;
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

        mouseXPos = mouseX;
        mouseYPos = mouseY;
        if (mouseButton == 0) {
            for (GuiButton b : buttonList) {

                if (b.isMouseOver()) {
                    actionPerformed(b);
                }

            }
        }

        if (mouseButton == 1) {
            for (GuiButton b : buttonList) {
                if (mouseX >= b.xPosition && mouseY >= b.yPosition && mouseX < b.xPosition + b.getButtonWidth() && mouseY < b.yPosition + b.getButtonHegiht()) {
                    for (Module m : Client.modules) {
                        if (m.name.equals(b.displayString)) {
                            m.expanded = !m.expanded;
                            mc.displayGuiScreen(new GuiScreen());
                            mc.displayGuiScreen(new GuiHandler());
                        }
                    }
                }
            }
        }


        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {

        List<Module.Category> categories = (Arrays.asList(Module.Category.values()));
        int i = 1;
        int settingCountId = 1;
        for (Module.Category c : categories) {
            int settingCount = 0;
            String name = c.name.toLowerCase();
            name = Character.toUpperCase(name.charAt(0)) + name.substring(1);

            this.buttonList.add(new GuiButton(i, (int) c.x, (int) c.y, width, height, name));

            if (c.expanded) {

                int count = 1;
                int countId = categories.size();
                for (Module m : Client.getModuleByCategory(c)) {

                    int id = Integer.parseInt(String.valueOf(i) + countId);
                    this.buttonList.add(new GuiButton(id, (int) c.x, (int) c.y + (height * count) + (height * settingCount), width, height, m.name));

                    if (m.expanded) {


                        //GlStateManager.translate(c.x, c.y + (height * index) + (height * settingCount), 1);


                        for (Setting s : m.settings) {
                            settingCount++;
                            settingCountId++;
                            int id2 = Integer.parseInt(id + String.valueOf(settingCountId));
                            if (s instanceof BooleanSetting) {
                                this.buttonList.add(new GuiButton(id2, (int) c.x, (int) c.y + (height * count) + (height * settingCount), width, height, "b"));
                            } else if (s instanceof NumberSetting) {
                                this.buttonList.add(new GuiButton(id2, (int) c.x, (int) c.y + (height * count) + (height * settingCount), width, height, "n"));
                            } else if (s instanceof ModeSetting) {
                                this.buttonList.add(new GuiButton(id2, (int) c.x, (int) c.y + (height * count) + (height * settingCount), width, height, "m"));
                            }

                        }

                    }

                    count++;
                    countId++;
                }
                i++;
            }
        }
    }


    @Override
    protected void actionPerformed(GuiButton button) throws IOException {

        int i = 1;
        FontRenderer fr = mc.fontRendererObj;
        List<Module.Category> categories = (Arrays.asList(Module.Category.values()));
        int settingCountId = 1;
        for (Module.Category c : categories) {
            int settingCount = 0;
            if (button.id == i) {
                c.expanded = !c.expanded;

            }
            if (c.expanded) {

                int count = categories.size();
                for (Module m : Client.getModuleByCategory(c)) {
                    int id = Integer.parseInt(String.valueOf(i) + count);
                    if (button.id == id) {
                        m.toggle();
                    }

                    if (m.expanded) {

                        for (Setting s : m.settings) {
                            settingCountId++;
                            int id2 = Integer.parseInt(id + String.valueOf(settingCountId));

                            if (button.id == id2) {
                                if (s instanceof BooleanSetting) {
                                    ((BooleanSetting) s).toggle();
                                } else if (s instanceof NumberSetting) {
                                    //Y = (X-A)/(B-A) * (D-C) + C
                                    double posDif = mouseXPos - button.xPosition;

                                    double dif = (((NumberSetting) s).maximum - ((NumberSetting) s).minimum + .05) / 100;

                                    ((NumberSetting) s).setValue(((NumberSetting) s).minimum + dif * posDif);
                                } else if (s instanceof ModeSetting) {
                                    ((ModeSetting) s).cycle();
                                }


                            }
                        }
                    }
                    count++;
                }
            }
            i++;
        }
    }
}
