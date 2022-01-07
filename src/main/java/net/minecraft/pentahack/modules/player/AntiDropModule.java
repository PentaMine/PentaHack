package net.minecraft.pentahack.modules.player;

import net.minecraft.client.Minecraft;
import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventItemDrop;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.BooleanSetting;
import net.minecraft.pentahack.util.Timer;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Keyboard;

public class AntiDropModule extends Module {

    private boolean cancelled = false;
    Timer timer = new Timer();

    public AntiDropModule() {
        super("AntiDrop", Keyboard.KEY_NONE, Category.PLAYER, "Prevents the player from accidentally dropping a item");
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventItemDrop){
            boolean sent = false;
            if (!cancelled){
                Client.addCustomChatMessage(this.name, "drop again to confirm");
                sent = true;
                cancelled = true;
            }
            else {
                cancelled = false;
            }
            


            if (timer.hasTimeElapsed(3000, true)){
                if (!sent) {
                    Client.addCustomChatMessage(this.name, "drop again to confirm");
                }
                cancelled = true;
            }

            e.setCacelled(cancelled);
        }

    }


}
