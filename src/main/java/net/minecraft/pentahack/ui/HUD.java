package net.minecraft.pentahack.ui;

import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.events.listeners.EventRender;
import net.minecraft.pentahack.gui.hud.HudComponent;
import net.minecraft.pentahack.gui.hud.impl.*;
import net.minecraft.client.Minecraft;
import net.minecraft.pentahack.settings.ModeSetting;
import net.minecraft.pentahack.settings.Setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class HUD {

    public Minecraft mc = Minecraft.getMinecraft();
    public boolean enabled = true;
    public boolean ttf = false;
    public List<HudComponent> components = new ArrayList<HudComponent>();

    public HUD(){
        components.add(new WatermarkComponent());
        components.add(new ModuleArrayComponent());
        components.add(new CoordinatesComponent());
        components.add(new FpsComponent());
        components.add(new YawComponent());
        components.add(new SpeedComponent());
    }

    public void render() {

        Client.onEvent(new EventRender());

        if (!enabled){
            return;
        }

        for (HudComponent component : components){
            if (!component.hidden){
                component.render();
            }
        }
    }

    public HudComponent getComponentByName(String name){
        for (HudComponent hc : components){
            if (hc.name.equals(name)){
                return hc;
            }
        }
        return null;
    }

    public Setting getSettingByName(HudComponent component, String name){
        for (Setting s : component.settings){
            if (s.name.equals(name)){
                return s;
            }
        }
        return null;
    }
}
