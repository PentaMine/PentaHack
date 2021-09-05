package net.minecraft.PentaHack.ui;

import net.minecraft.client.gui.GuiScreen;
import java.io.IOException;

public class MainMenu extends GuiScreen {

    public MainMenu() {

    }

    public void initGui() {

    }
/*
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(new ResourceLocation("PentaHack/background.jpg"));
        this.drawModalRectWithCustomSizedTexture( 0,  0, 0, 0, this.width, this.height, this.width, this.height);
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        for (Module.Category c : TabGUI.categiries){
            float x = (float) c.x;
            float y = (float) c.y;
            if ((mouseX >= x && mouseX <= x + 100) && (mouseY >= c.y && mouseY <= 5 + mc.fontRendererObj.FONT_HEIGHT)){
                System.out.println(String.valueOf(c));
            }
        }
    }
 */

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return super.doesGuiPauseGame();
    }

    public void onGuiClosed() {

    }
}
