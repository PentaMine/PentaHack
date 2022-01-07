package net.minecraft.pentahack.gui.click;

import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.util.render.PentaFontRenderer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MenuComponent {

    public List<Module> modules;
    public double x, y, width, height, defaultX, defaultY;
    public String displayText;
    public boolean expanded;
    public Module.Category category;
    public int backgroundColor, backgroundModuleColor, moduleOnColor, moduleOffColor;

    final PentaFontRenderer fr = new PentaFontRenderer("roboto condensed", 20.0f);

    public MenuComponent(List<Module> modules, double x, double y, double width, double height, String name, Module.Category category, int backgroundColor, int backgroundModuleColor, int moduleOnColor, int moduleOffColor) {
        this.modules = modules;
        this.x = x;
        this.y = y;
        this.defaultX = x;
        this.defaultY = y;
        this.width = width;
        this.height = height;
        this.expanded = true;
        this.category = category;
        this.displayText = name;
        this.backgroundColor = backgroundColor;
        this.backgroundModuleColor = backgroundModuleColor;
        this.moduleOnColor = moduleOnColor;
        this.moduleOffColor = moduleOffColor;

        modules.sort(Comparator.comparing(o -> o.name));

    }

    public List<Module> getModules() {
        return modules;
    }

    public void addModule(Module module) {
        this.modules.add(module);
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

    public Module.Category getCategory() {
        return category;
    }

    public void setCategory(Module.Category category) {
        this.category = category;
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
