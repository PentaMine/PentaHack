package net.minecraft.pentahack;


import net.minecraft.pentahack.command.CommandManager;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventChat;
import net.minecraft.pentahack.events.listeners.EventKey;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.modules.render.*;
import net.minecraft.pentahack.modules.movement.*;
import net.minecraft.pentahack.modules.player.*;
import net.minecraft.pentahack.modules.combat.*;
import net.minecraft.pentahack.ui.HUD;
import net.minecraft.pentahack.ui.ClientGuiManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import org.darkstorm.minecraft.gui.theme.simple.SimpleTheme;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client {

    public static String name = "PentaHack", version = "1.0.0";
    public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
    public static HUD hud = new HUD();
    public static int color = 0xffee2a00;
    public static ClientGuiManager guiManager;
    public static CommandManager commandManager= new CommandManager();

    public static void startup() {
        System.out.println("Starting " + name + " v " + version);

        modules.add(new FlightModule());
        modules.add(new SprintModule());
        modules.add(new FullBrightModule());
        modules.add(new NoFallModule());
        modules.add(new ClickGuiModule());
        modules.add(new KillAuraModule());
        modules.add(new AimBotModule());
        modules.add(new HudModule());


        guiManager = new ClientGuiManager();
        guiManager.setTheme(new SimpleTheme());
        guiManager.setup();




    }

    public static void onEvent(Event e) {

        if (e instanceof EventChat){
            commandManager.handleChat((EventChat)e);
        }

        for (Module m : modules) {
            if (!m.toggled) {
                continue;
            }
            m.onEvent(e);
        }
    }

    public static void keyPress(int key) {
        Client.onEvent(new EventKey(key));
        for (Module m : modules) {
            if (m.getKey() == key) {
                m.toggle();

            }
        }
    }

    public static List<Module> getModuleByCategory(Module.Category c) {
        List<Module> modules = new ArrayList<Module>();

        for (Module m : Client.modules) {
            if (m.category == c) {
                modules.add(m);
            }
        }
        return modules;
    }

    public static void addChatMessage(String message){
        message = "\2479" + name + "\2477" + " " + message;

        Minecraft.getMinecraft().player.addChatMessage(new TextComponentString(message));

    }
}
