package net.minecraft.pentahack.modules.misc;

import com.mojang.realmsclient.dto.PingResult;
import net.minecraft.network.status.client.CPacketPing;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventUpdate;
import net.minecraft.pentahack.modules.Module;

import net.minecraft.pentahack.settings.NumberSetting;
import net.minecraft.pentahack.util.Timer;
import org.lwjgl.input.Keyboard;

public class DDosModule extends Module {


    private Timer timer = new Timer();
    public NumberSetting pause = new NumberSetting("pause", 2, .1, 10);
    /** put the module setting above this comment*/

    public DDosModule() {
        super("DDos", Keyboard.KEY_NONE, Category.MISC, "Pings the server repeatedly");
        this.addSettings(pause);
    }

    /**
     * triggered when the module is being turned on
     */
    @Override
    public void onEnable() {
        super.onEnable();
        timer.reset();
        mc.playerController.connection.getNetworkManager().sendPacket(new CPacketPing());
    }

    /**
     * triggered when the module is being turned off
     */
    @Override
    public void onDisable() {
        super.onDisable();
    }

    /**
     * executed every time a event is triggered
     */
    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate){
            if (timer.hasTimeElapsed((long) pause.value * 1000, true)){
                //mc.playerController.connection.sendPacket(new CPacketPing(1));
                //mc.playerController.connection.sendPacket(new CPacketPing(1000));
                mc.playerController.connection.getNetworkManager().sendPacket(new CPacketPing());

            }
        }
    }
}
