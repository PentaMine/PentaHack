package net.minecraft.pentahack.modules;

import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.settings.Setting;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Module {

    public String name, description;
    public boolean toggled;
    public int keyCode;
    public Category category;
    public boolean expanded;
    public int index;
    public Minecraft mc = Minecraft.getMinecraft();

    public List<Setting> settings = new ArrayList<Setting>();

    public Module(String name, int key, Category c) {
        this.name = name;
        this.keyCode = key;
        this.category = c;
        this.description = "A module (This is a placeholder)";
    }

    public Module(String name, int key, Category c, String description) {
        this.name = name;
        this.keyCode = key;
        this.category = c;
        this.description = description;
    }

    public void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public boolean isEnabled() {
        return toggled;
    }

    public int getKey() {
        return keyCode;
    }

    public void onEvent(Event e) {

    }

    public void toggle() {
        this.toggled = !this.toggled;
        if (this.toggled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public enum Category {
        COMBAT("Combat", 10, 20, true),
        EXPLOIT("Exploit", 130, 20, true),
        MINIGAME("MiniGame", 250, 20, true),
        MISC("Misc", 370, 20, true),
        MOVEMENT("Movement", 490, 20, true),
        PLAYER("Player", 610, 20, true),
        RENDER("Render", 730, 20, true),
        WORLD("World", 850, 20, true);


        public String name;
        public double x;
        public double y;
        public boolean expanded;

        Category(String name, double x, double y, boolean expanded) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.expanded = expanded;
        }
    }
}
