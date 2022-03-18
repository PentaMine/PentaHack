package net.minecraft.pentahack.modules.player;

import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventMotion;
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
