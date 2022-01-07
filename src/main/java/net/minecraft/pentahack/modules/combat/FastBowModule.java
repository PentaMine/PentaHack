package net.minecraft.pentahack.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBow;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventUpdate;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.BooleanSetting;
import net.minecraft.pentahack.util.Timer;
import org.lwjgl.input.Keyboard;


public class FastBowModule extends Module {

    public BooleanSetting onlyOnGround = new BooleanSetting("Only on ground", false);

    Timer timer = new Timer();

    public FastBowModule() {
        super("FastBow", Keyboard.KEY_NONE, Category.COMBAT);
        this.addSettings(onlyOnGround);
    }

    public static Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }


    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (mc.player.getHealth() > 0
                    //&& (mc.player.onGround || mc.player.capabilities.isCreativeMode)
                    && mc.player.inventory.getCurrentItem() != null
                    && mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow
                    && mc.gameSettings.keyBindUseItem.isKeyDown()
                    && timer.hasTimeElapsed(250, true)) {

                if (onlyOnGround.isEnabled() && (!mc.player.onGround)){
                    if (!mc.player.capabilities.isCreativeMode){
                        return;
                    }

                }
                mc.playerController.onStoppedUsingItem(mc.player);

            }
            //Client.addCustomChatMessage(this.name, "this happened badly");
        }
    }
}
