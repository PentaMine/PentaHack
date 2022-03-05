package net.minecraft.pentahack.modules.movement;

import net.minecraft.block.BlockAir;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventMotion;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

public class SafeWalkModule extends Module {



    /** put the module setting above this comment*/

    public SafeWalkModule() {
        super("SafeWalk", Keyboard.KEY_NONE, Category.MOVEMENT, "Prevents you from falling");
    }

    /**
     * triggered when the module is being turned on
     */
    @Override
    public void onEnable() {
        super.onEnable();
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
        if (e instanceof EventMotion){
            if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - .1, mc.player.posZ)).getBlock() instanceof BlockAir){
                mc.player.motionX = 0;
                mc.player.motionZ = 0;
                mc.player.motionY = 0;
            }
        }
    }
}
