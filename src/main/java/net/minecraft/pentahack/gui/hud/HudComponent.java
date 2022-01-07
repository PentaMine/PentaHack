package net.minecraft.pentahack.gui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.pentahack.settings.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HudComponent {

    public List<Setting> settings = new ArrayList<Setting>();
    public Minecraft mc = Minecraft.getMinecraft();

    public String name; 
    public boolean hidden, expanded;
    public double top, bottom, left, right;

    public HudComponent(String name) {
        this.name = name;
        this.hidden = false;
        this.expanded = false;
    }

    public HudComponent(String name, boolean hidden) {
        this.name = name;
        this.hidden = hidden;
        this.expanded = false;
    }

    public void toggle(){
        hidden = !hidden;
    }

    public void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public double getTop() {
        return top;
    }

    public void setTop(double top) {
        this.top = top;
    }

    public double getBottom() {
        return bottom;
    }

    public void setBottom(double bottom) {
        this.bottom = bottom;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getRight() {
        return right;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public void render(){

    }
}
