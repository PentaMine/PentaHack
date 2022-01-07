package net.minecraft.pentahack.ui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.gui.click.MenuComponent;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.*;
import net.minecraft.pentahack.util.MathUtil;
import net.minecraft.pentahack.util.render.PentaFontRenderer;
import net.minecraft.pentahack.util.render.RenderUtils;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.text.DecimalFormat;

public class ClickGuiHandler extends GuiScreen {

    final PentaFontRenderer fr = new PentaFontRenderer("roboto medium", 15.1f);
    public static int mouseXPos;
    public static int mouseYPos;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        mouseXPos = mouseX;
        mouseYPos = mouseY;

        for (MenuComponent m : Client.menuComponents) {
            int width = (int) m.width;
            int height = (int) m.height;

            int settingCount = 0;


            String name = m.displayText.toLowerCase();
            name = Character.toUpperCase(name.charAt(0)) + name.substring(1);

            RenderUtils.drawRect(m.x, m.y, width, height, m.backgroundColor);

            GlStateManager.pushMatrix();

            GlStateManager.translate(m.x, m.y, 1);

            GlStateManager.scale(1, 1, 1);
            fr.drawStringWithShadow(name, 3, 1, m.moduleOnColor);

            GlStateManager.popMatrix();
            if (m.expanded) {
                int index = 1;
                for (Module module : m.modules) {

                    RenderUtils.drawRect(m.x, m.y + (height * index) + (height * settingCount) + 2, width, height, m.backgroundModuleColor);

                    GlStateManager.pushMatrix();

                    GlStateManager.translate(m.x, m.y + (height * index) + (height * settingCount), 1);
                    GlStateManager.scale(1, 1, 1);

                    fr.drawStringWithShadow(module.name, 3, 3, module.toggled ? m.moduleOnColor : m.moduleOffColor);

                    GlStateManager.popMatrix();

                    if (module.expanded && module.settings.size() > 0) {
                        for (Setting s : module.settings) {
                            settingCount++;

                            RenderUtils.drawRect(m.x, m.y + (height * index) + (height * settingCount) + 2, width, height, m.backgroundModuleColor);
                            RenderUtils.drawRect(m.x + width - 2, m.y + (height * index) + (height * settingCount) + 2, 2, height, Client.color);

                            if (s instanceof NumberSetting) {
                                double dif = MathUtil.map(((NumberSetting) s).value, ((NumberSetting) s).minimum, ((NumberSetting) s).maximum, 0, 100);

                                RenderUtils.drawRect(m.x, m.y + (height * index) + (height * settingCount) + 2, dif, height, Client.color);
                            }
                            GlStateManager.pushMatrix();

                            GlStateManager.translate(m.x, m.y + (height * index) + (height * settingCount), 1);
                            GlStateManager.scale(1, 1, 1);

                            DecimalFormat df2 = new DecimalFormat("#.##");
                            if (s instanceof BooleanSetting) {
                                fr.drawStringWithShadow(s.name + (((BooleanSetting) s).isEnabled() ? " [X]" : " [  ]"), 3, 3, -1);
                            } else if (s instanceof NumberSetting) {
                                fr.drawStringWithShadow(s.name + ": " + df2.format(((NumberSetting) s).getValue()), 3, 3, -1);
                            } else if (s instanceof ModeSetting) {
                                fr.drawStringWithShadow(s.name + ": " + ((ModeSetting) s).getMode(), 3, 3, -1);
                            } else if (s instanceof KeyBindSetting) {
                                fr.drawStringWithShadow(((KeyBindSetting) s).pending ? "Press a key" : s.name + ": " + String.valueOf(Keyboard.getKeyName(((KeyBindSetting) s).key)), 3, 3, -1);
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
        for (MenuComponent menuComponent : Client.menuComponents) {
            int settingCount = 0;
            GuiButton button = new GuiButton(id, (int) menuComponent.x, (int) menuComponent.y, (int) menuComponent.width, (int) menuComponent.height, sound, menuComponent.displayText);
            button.setRender(false);
            this.buttonList.add(button);


            int index = 1;

            if (menuComponent.expanded) {
                for (Module module : menuComponent.modules) {

                    int id2 = Integer.parseInt(String.valueOf(id) + String.valueOf(index));
                    GuiButton button2 = new GuiButton(id2, (int) menuComponent.x, (int) (menuComponent.y + (index * menuComponent.height) + (menuComponent.height * settingCount)) + 2, (int) menuComponent.width, (int) menuComponent.height, sound, module.name);
                    button2.setRender(false);
                    this.buttonList.add(button2);

                    if (module.expanded) {
                        for (Setting setting : module.settings) {
                            settingCount++;
                            settingCountId++;
                            int id3 = Integer.parseInt(id2 + String.valueOf(settingCountId));
                            GuiButton button3 = new GuiButton(id3, (int) menuComponent.x, (int) (menuComponent.y + (index * menuComponent.height) + (menuComponent.height * settingCount)) + 2, (int) menuComponent.width, (int) menuComponent.height, sound, setting instanceof NumberSetting ? "num" : setting.name);
                            button3.setRender(false);
                            this.buttonList.add(button3);

                        }
                    }
                    index++;
                }
            }
            id++;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        int id = 1;
        int settingCountId = 1;
        for (MenuComponent menuComponent : Client.menuComponents) {

            if (button.id == id) {
                menuComponent.toggle();
                mc.displayGuiScreen(new ClickGuiHandler());
            }

            int index = 1;

            if (menuComponent.expanded) {
                for (Module module : menuComponent.modules) {

                    int id2 = Integer.parseInt(String.valueOf(id) + String.valueOf(index));

                    if (button.id == id2) {
                        module.toggle();
                    }

                    if (module.expanded) {
                        for (Setting setting : module.settings) {
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
                                } else if (setting instanceof KeyBindSetting) {
                                    ((KeyBindSetting) setting).pending = true;

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

        if (mouseButton == 1) {
            for (GuiButton b : buttonList) {
                if (mouseX >= b.xPosition && mouseY >= b.yPosition && mouseX < b.xPosition + b.getButtonWidth() && mouseY < b.yPosition + b.getButtonHeight()) {
                    for (Module m : Client.modules) {
                        if (m.name.equals(b.displayString)) {
                            m.expanded = !m.expanded;
                            //mc.displayGuiScreen(new GuiScreen());
                            mc.displayGuiScreen(new ClickGuiHandler());
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

                    for (Module m : Client.modules) {
                        if (getButtonByName(m.name).id != Integer.parseInt(String.valueOf(b.id).replaceAll(".$", ""))) {
                            continue;
                        }
                        for (Setting s : m.settings) {
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

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        for (Module m : Client.modules) {
            for (Setting s : m.settings) {
                if (s instanceof KeyBindSetting && ((KeyBindSetting) s).pending) {

                    if (keyCode == Keyboard.KEY_ESCAPE) {
                        return;
                    } else if (keyCode == Keyboard.KEY_DELETE) {
                        ((KeyBindSetting) s).setKey(Keyboard.KEY_NONE);
                        ((KeyBindSetting) s).pending = false;
                        m.keyCode = ((KeyBindSetting) s).key;
                    } else {
                        ((KeyBindSetting) s).setKey(keyCode);
                        ((KeyBindSetting) s).pending = false;
                        m.keyCode = ((KeyBindSetting) s).key;
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
