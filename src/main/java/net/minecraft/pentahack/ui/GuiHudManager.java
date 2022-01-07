package net.minecraft.pentahack.ui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.gui.click.HudMenuComponent;
import net.minecraft.pentahack.gui.hud.HudComponent;
import net.minecraft.pentahack.settings.BooleanSetting;
import net.minecraft.pentahack.settings.ModeSetting;
import net.minecraft.pentahack.settings.NumberSetting;
import net.minecraft.pentahack.settings.Setting;
import net.minecraft.pentahack.util.MathUtil;
import net.minecraft.pentahack.util.render.PentaFontRenderer;
import net.minecraft.pentahack.util.render.RenderUtils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GuiHudManager extends GuiScreen {

    final PentaFontRenderer fr = new PentaFontRenderer("roboto medium", 15.1f);
    public static int mouseXPos;
    public static int mouseYPos;

    public List<HudMenuComponent> components = new ArrayList<HudMenuComponent>();

    public GuiHudManager() {
        int bC = 0x92424242;
        int bMC = 0x92575757;
        int mOnC = 0xffffffff;
        int mOffC = 0xffC2C2C2;

        components.add(new HudMenuComponent(Client.hud.components, 490, 20, 100, 10, "Hud Components", bC, bMC, mOnC, mOffC));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        mouseXPos = mouseX;
        mouseYPos = mouseY;

        for (HudMenuComponent c : components) {
            int width = (int) c.width;
            int height = (int) c.height;


            int settingCount = 0;


            String name = c.displayText.toLowerCase();
            name = Character.toUpperCase(name.charAt(0)) + name.substring(1);

            RenderUtils.drawRect(c.x, c.y, width, height, c.backgroundColor);

            GlStateManager.pushMatrix();

            GlStateManager.translate(c.x, c.y, 1);

            GlStateManager.scale(1, 1, 1);
            fr.drawStringWithShadow(name, 3, 1, c.moduleOnColor);

            GlStateManager.popMatrix();
            if (c.expanded) {
                int index = 1;
                for (HudComponent component : c.components) {

                    RenderUtils.drawRect(c.x, c.y + (height * index) + (height * settingCount) + 2, width, height, c.backgroundModuleColor);

                    GlStateManager.pushMatrix();

                    GlStateManager.translate(c.x, c.y + (height * index) + (height * settingCount), 1);
                    GlStateManager.scale(1, 1, 1);

                    fr.drawStringWithShadow(component.name, 3, 3, component.hidden ? c.moduleOffColor : c.moduleOnColor);

                    GlStateManager.popMatrix();

                    if (component.expanded && component.settings.size() > 0) {
                        for (Setting s : component.settings) {
                            settingCount++;

                            RenderUtils.drawRect(c.x, c.y + (height * index) + (height * settingCount) + 2, width, height, c.backgroundModuleColor);
                            RenderUtils.drawRect(c.x + width - 2, c.y + (height * index) + (height * settingCount) + 2, 2, height, Client.color);

                            if (s instanceof NumberSetting) {
                                double dif = MathUtil.map(((NumberSetting) s).value, ((NumberSetting) s).minimum, ((NumberSetting) s).maximum, 0, 100);

                                RenderUtils.drawRect(c.x, c.y + (height * index) + (height * settingCount) + 2, dif, height, Client.color);
                            }
                            GlStateManager.pushMatrix();

                            GlStateManager.translate(c.x, c.y + (height * index) + (height * settingCount), 1);
                            GlStateManager.scale(1, 1, 1);

                            DecimalFormat df2 = new DecimalFormat("#.##");
                            if (s instanceof BooleanSetting) {
                                fr.drawStringWithShadow(s.name + (((BooleanSetting) s).isEnabled() ? " [X]" : " [  ]"), 3, 3, -1);
                            } else if (s instanceof NumberSetting) {
                                fr.drawStringWithShadow(s.name + ": " + df2.format(((NumberSetting) s).getValue()), 3, 3, -1);
                            } else if (s instanceof ModeSetting) {
                                fr.drawStringWithShadow(s.name + ": " + ((ModeSetting) s).getMode(), 3, 3, -1);
                            }
                            GlStateManager.popMatrix();
                        }
                    }
                    index++;
                }
            }
        }
    }

    @Override
    public void initGui() {
        int id = 1;
        boolean sound = false;
        int settingCountId = 1;
        for (HudMenuComponent component : components) {
            int settingCount = 0;
            GuiButton button = new GuiButton(id, (int) component.x, (int) component.y, (int) component.width, (int) component.height, sound, component.displayText);
            button.setRender(false);
            this.buttonList.add(button);


            int index = 1;

            if (component.expanded) {
                for (HudComponent component1 : component.components) {

                    int id2 = Integer.parseInt(String.valueOf(id) + String.valueOf(index));
                    GuiButton button2 = new GuiButton(id2, (int) component.x, (int) (component.y + (index * component.height) + (component.height * settingCount)) + 2, (int) component.width, (int) component.height, sound, component1.name);
                    button2.setRender(false);
                    this.buttonList.add(button2);

                    if (component1.expanded) {
                        for (Setting setting : component1.settings) {
                            settingCount++;
                            settingCountId++;
                            int id3 = Integer.parseInt(id2 + String.valueOf(settingCountId));
                            GuiButton button3 = new GuiButton(id3, (int) component.x, (int) (component.y + (index * component.height) + (component.height * settingCount)) + 2, (int) component.width, (int) component.height, sound, setting instanceof NumberSetting ? "num" : setting.name);
                            button3.setRender(false);
                            this.buttonList.add(button3);

                        }
                    }
                    index++;
                }
            }
            id++;
        }
        //this.buttonList.add(new GuiButton(0, (int) 67, (int) 67, 100, 13, "FUCKYOU")); // this was a test
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        int id = 1;
        int settingCountId = 1;
        for (HudMenuComponent component : components) {

            if (button.id == id) {
                component.toggle();
                mc.displayGuiScreen(new GuiHudManager());
            }

            int index = 1;

            if (component.expanded) {
                for (HudComponent component1 : component.components) {

                    int id2 = Integer.parseInt(String.valueOf(id) + String.valueOf(index));

                    if (button.id == id2) {
                        component1.toggle();
                    }

                    if (component1.expanded) {
                        for (Setting setting : component1.settings) {
                            settingCountId++;
                            int id3 = Integer.parseInt(String.valueOf(id2) + String.valueOf(settingCountId));

                            if (button.id == id3) {
                                if (setting instanceof NumberSetting) {
                                    double posDif = Math.abs(mouseXPos - button.xPosition);

                                    double dif = (((NumberSetting) setting).maximum - ((NumberSetting) setting).minimum + .05) / 100;

                                    ((NumberSetting) setting).setValue(((NumberSetting) setting).minimum + dif * posDif);
                                } else if (setting instanceof ModeSetting) {
                                    ((ModeSetting) setting).cycle();
                                } else if (setting instanceof BooleanSetting) {
                                    ((BooleanSetting) setting).toggle();
                                }
                            }
                        }
                    }

                    index++;
                }
            }
            id++;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);


        mouseXPos = mouseX;
        mouseYPos = mouseY;
        /*if (mouseButton == 0) {
            for (GuiButton b : buttonList) {

                if (b.isMouseOver()) {
                    actionPerformed(b);
                }

            }
        }*/

        if (mouseButton == 1) {
            for (GuiButton b : buttonList) {
                if (mouseX >= b.xPosition && mouseY >= b.yPosition && mouseX < b.xPosition + b.getButtonWidth() && mouseY < b.yPosition + b.getButtonHeight()) {
                    for (HudComponent component : components.get(0).components) {
                        if (component.name.equals(b.displayString)) {
                            component.expanded = !component.expanded;
                            //mc.displayGuiScreen(new GuiScreen());
                            mc.displayGuiScreen(new GuiHudManager());
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);


        mouseXPos = mouseX;
        mouseYPos = mouseY;

        if (clickedMouseButton == 0) {
            for (GuiButton b : buttonList) {
                if (mouseX >= b.xPosition && mouseY >= b.yPosition && mouseX < b.xPosition + b.getButtonWidth() && mouseY < b.yPosition + b.getButtonHeight() && String.valueOf(b.id).length() == 3) {

                    for (HudComponent component : components.get(0).components) {
                        if (getButtonByName(component.name).id != Integer.parseInt(String.valueOf(b.id).replaceAll(".$", ""))) {
                            continue;
                        }
                        for (Setting s : component.settings) {
                            if (b.displayString.equals("num")) {
                                if (s instanceof NumberSetting) {
                                    double posDif = Math.abs(mouseXPos - b.xPosition);

                                    double dif = (((NumberSetting) s).maximum - ((NumberSetting) s).minimum + .05) / 100;

                                    ((NumberSetting) s).setValue(((NumberSetting) s).minimum + dif * posDif);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public GuiButton getButtonByName(String name) {
        for (GuiButton button : this.buttonList) {
            if (button.displayString.equals(name)) {
                return button;
            }
        }
        return null;
    }
}
