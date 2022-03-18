package net.minecraft.pentahack.modules.player;

import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventMotion;
import net.minecraft.pentahack.events.listeners.EventUpdate;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.ModeSetting;
import org.lwjgl.input.Keyboard;

public class NoFallModule extends Module {

    public ModeSetting mode = new ModeSetting("mode", "Vanilla", "FastFall", "Vanilla");

    public NoFallModule() {
        super("NoFall", Keyboard.KEY_NONE, Category.PLAYER);
        this.addSettings(mode);
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventMotion && e.isPre()) {
            if (mc.player.fallDistance > 3.0f) {

                if (mode.getMode().equalsIgnoreCase("Vanilla")) {

                    mc.player.connection.sendPacket(new CPacketPlayer(true));
                    mc.player.fallDistance = (float) Math.random();
                    mc.player.onGround = true;

                } else if (mode.getMode().equalsIgnoreCase("FastFall")) {
                    mc.player.posY -= mc.player.fallDistance;

                    mc.player.connection.sendPacket(new CPacketPlayer(true));
                    mc.player.fallDistance = (float) Math.random();
                    mc.player.onGround = true;
                }
            }
        }
    }
}

/*
public class NoFall extends Module {

    private String mode;

    int counter = 0;

    int posYcounter = 0;

    double height;

    double y;

    public NoFall() {

        super("NoFall", Category.PLAYER, Keyboard.KEY\_H);


    }

    boolean spoofed = false;

    public void setup() {

        ArrayList<String> options = new ArrayList<>();

        options.add("Vanilla");

        options.add("FastFall");
        me.omer.tutorial.Client.settingsManager.rSetting(new Setting("NoFall Mode", this, "Vanilla", options));

    }


    u/Override

    public void onPreUpdate() {

        super.onPreUpdate();

	[//System.out.println](//System.out.println)("spoofed: "+counter+", "+mc.player.onGround );

        mode = me.omer.tutorial.Client.settingsManager.getSettingByName("NoFall Mode").getValString();

        if (mode.equalsIgnoreCase("FastFall")) {

            if (mc.player.fallDistance >= 3.0f && !mc.player.onGround) {

                mc.player.onGround = true;

                mc.player.posY -= mc.player.fallDistance + 10;

                mc.player.onGround = true;

            }

        } else if (mode.equalsIgnoreCase("Vanilla")) {
            if (mc.player.fallDistance >= 3.0f)

                mc.player.onGround = true;

        }

    }
}
 */
