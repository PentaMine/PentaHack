package net.minecraft.pentahack.gui.click;

import net.minecraft.pentahack.gui.hud.HudComponent;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.util.render.PentaFontRenderer;

import java.util.List;

public class HudMenuComponent {

    public List<HudComponent> components;
    public double x, y, width, height, defaultX, defaultY;
    public String displayText;
    public boolean expanded;
    public int backgroundColor, backgroundModuleColor, moduleOnColor, moduleOffColor;

    final PentaFontRenderer fr = new PentaFontRenderer("roboto condensed", 20.0f);

    public HudMenuComponent(List<HudComponent> components, double x, double y, double width, double height, String name, int backgroundColor, int backgroundModuleColor, int moduleOnColor, int moduleOffColor) {
        this.components = components;
        this.x = x;
        this.y = y;
        this.defaultX = x;
        this.defaultY = y;
        this.width = width;
        this.height = height;
        this.expanded = true;
        this.displayText = name;
        this.backgroundColor = backgroundColor;
        this.backgroundModuleColor = backgroundModuleColor;
        this.moduleOnColor = moduleOnColor;
        this.moduleOffColor = moduleOffColor;

    }

    public List<HudComponent> getModules() {
        return components;
    }

    public void addModule(HudComponent component) {
        this.components.add(component);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getBackgroundModuleColor() {
        return backgroundModuleColor;
    }

    public void setBackgroundModuleColor(int backgroundModuleColor) {
        this.backgroundModuleColor = backgroundModuleColor;
    }

    public int getModuleOnColor() {
        return moduleOnColor;
    }

    public void setModuleOnColor(int moduleOnColor) {
        this.moduleOnColor = moduleOnColor;
    }

    public int getModuleOffColor() {
        return moduleOffColor;
    }

    public void setModuleOffColor(int moduleOffColor) {
        this.moduleOffColor = moduleOffColor;
    }

    public double[] getDefaultPos() {
        return new double[]{defaultX, defaultY};
    }

    public void toggle(){
        this.expanded = !this.expanded;
    }

}
