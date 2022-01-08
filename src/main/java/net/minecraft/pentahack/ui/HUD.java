package net.minecraft.pentahack.ui;

import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.events.listeners.EventRender;
import net.minecraft.pentahack.gui.hud.HudComponent;
import net.minecraft.pentahack.gui.hud.impl.*;
import net.minecraft.pentahack.util.Wrapper;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

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
}
