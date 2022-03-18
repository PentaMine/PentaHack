package net.minecraft.pentahack;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.pentahack.command.CommandManager;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventChat;
import net.minecraft.pentahack.events.listeners.EventKey;
import net.minecraft.pentahack.gui.click.MenuComponent;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.modules.combat.AimBotModule;
import net.minecraft.pentahack.modules.combat.FastBowModule;
import net.minecraft.pentahack.modules.combat.KillAuraModule;
import net.minecraft.pentahack.modules.misc.DDosModule;
import net.minecraft.pentahack.modules.movement.FlightModule;
import net.minecraft.pentahack.modules.movement.SafeWalkModule;
import net.minecraft.pentahack.modules.movement.SprintModule;
import net.minecraft.pentahack.modules.player.*;
import net.minecraft.pentahack.modules.render.*;
import net.minecraft.pentahack.modules.world.ScaffoldModule;
import net.minecraft.pentahack.settings.BooleanSetting;
import net.minecraft.pentahack.settings.KeyBindSetting;
import net.minecraft.pentahack.ui.HUD;
import net.minecraft.pentahack.util.render.PentaFontRenderer;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client {
    public static String name = "PentaHack", version = "1.0.0 alpha";
    public static String[] devs = {"PentaMine"};
    public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
    public static ArrayList<MenuComponent> menuComponents = new ArrayList<MenuComponent>();
    public static HUD hud = new HUD();
    public static int color = 0xffee2a00;
    public static Minecraft mc = Minecraft.getMinecraft();
    public static PentaFontRenderer fr;
    public static FontRenderer mfr;

    public static void clientInit() {
        System.out.println("Starting " + name + " v " + version);

        initiateModules();
        initiateGui();

        for (Module module : modules) {
            module.addSettings(new BooleanSetting("hidden", false));
            if (module instanceof ClickGuiModule) {
                module.addSettings(new KeyBindSetting("KeyBind", Keyboard.KEY_LEFT));
            } else {
                module.addSettings(new KeyBindSetting("KeyBind", Keyboard.KEY_NONE));
            }
        }

        fr = new PentaFontRenderer("roboto", 20.0f);
        mfr = mc.fontRendererObj;
    }

    public static void onEvent(Event e) {
        if (Minecraft.getMinecraft().player == null || Minecraft.getMinecraft().world == null) {
            return;
        }

        CommandManager commandManager = new CommandManager();
        if (e instanceof EventChat) {
            commandManager.handleChat((EventChat) e);
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

    public static void addChatMessage(Object message) {
        addCustomChatMessage(name, message);
    }

    public static void addCustomChatMessage(String name, Object message) {
        message = "\u00A74" + String.format("[%s]", name) + "\u00A77" + " " + message;

        Minecraft.getMinecraft().player.addChatMessage(new TextComponentString(String.valueOf(message)));

    }

    public static void initiateGui() {
        int bC = 0x92424242;
        int bMC = 0x92575757;
        int mOnC = 0xffffffff;
        int mOffC = 0xffC2C2C2;

        double height = 10;


        menuComponents.add(new MenuComponent(Client.getModuleByCategory(Module.Category.COMBAT), Module.Category.COMBAT.x, Module.Category.COMBAT.y, 100, height, "Combat", Module.Category.COMBAT, bC, bMC, mOnC, mOffC));
        menuComponents.add(new MenuComponent(Client.getModuleByCategory(Module.Category.EXPLOIT), Module.Category.EXPLOIT.x, Module.Category.EXPLOIT.y, 100, height, "Exploit", Module.Category.EXPLOIT, bC, bMC, mOnC, mOffC));
        menuComponents.add(new MenuComponent(Client.getModuleByCategory(Module.Category.MINIGAME), Module.Category.MINIGAME.x, Module.Category.MINIGAME.y, 100, height, "Minigame", Module.Category.MINIGAME, bC, bMC, mOnC, mOffC));
        menuComponents.add(new MenuComponent(Client.getModuleByCategory(Module.Category.MISC), Module.Category.MISC.x, Module.Category.MISC.y, 100, height, "Misc", Module.Category.MISC, bC, bMC, mOnC, mOffC));
        menuComponents.add(new MenuComponent(Client.getModuleByCategory(Module.Category.MOVEMENT), Module.Category.MOVEMENT.x, Module.Category.MOVEMENT.y, 100, height, "Movement", Module.Category.MOVEMENT, bC, bMC, mOnC, mOffC));
        menuComponents.add(new MenuComponent(Client.getModuleByCategory(Module.Category.PLAYER), Module.Category.PLAYER.x, Module.Category.PLAYER.y, 100, height, "Player", Module.Category.PLAYER, bC, bMC, mOnC, mOffC));
        menuComponents.add(new MenuComponent(Client.getModuleByCategory(Module.Category.RENDER), Module.Category.RENDER.x, Module.Category.RENDER.y, 100, height, "Render", Module.Category.RENDER, bC, bMC, mOnC, mOffC));
        menuComponents.add(new MenuComponent(Client.getModuleByCategory(Module.Category.WORLD), Module.Category.WORLD.x, Module.Category.WORLD.y, 100, height, "World", Module.Category.WORLD, bC, bMC, mOnC, mOffC));
    }

    public static void initiateModules() {
        modules.add(new FlightModule());
        modules.add(new SprintModule());
        modules.add(new FullBrightModule());
        modules.add(new NoFallModule());
        modules.add(new ClickGuiModule());
        modules.add(new KillAuraModule());
        modules.add(new AimBotModule());
        modules.add(new HudModule());
        modules.add(new EntityESPModule());
        modules.add(new AntiDropModule());
        modules.add(new FastBowModule());
        modules.add(new AntiAFKModule());
        modules.add(new AutoRespawnModule());
        modules.add(new HudManagerModule());
        modules.add(new ScaffoldModule());
        modules.add(new SafeWalkModule());
        modules.add(new DDosModule());
        modules.add(new InfinityJumpModule());
    }

    public static void initProperties(){
        
    }
}
