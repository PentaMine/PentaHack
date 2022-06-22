package net.minecraft.pentahack.modules.movement;

import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventUpdate;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.BooleanSetting;
import net.minecraft.pentahack.settings.NumberSetting;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

public class InfinityJumpModule extends Module {

    public NumberSetting heightRequirement = new NumberSetting("height requirement", 1, 0, 3);
    public BooleanSetting noFall = new BooleanSetting("Nofall", true);

    public InfinityJumpModule() {
        super("InfinityJump(TM)", Keyboard.KEY_NONE, Category.MOVEMENT, "makes you not need block to jump from");
        this.addSettings(heightRequirement, noFall);
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate && e.isPre()) {
            if (mc.player.fallDistance > heightRequirement.value) {
                mc.player.onGround = true;
            }
            /*if (noFall.enabled) {
                if (!((!(mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 3, mc.player.posZ)).getBlock() instanceof BlockAir) ||
                        !(mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 2, mc.player.posZ)).getBlock() instanceof BlockAir) ||
                        !(mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ)).getBlock() instanceof BlockAir)) &&
                        (!mc.player.onGround || mc.player.fallDistance > 0)) &&
                        mc.player.fallDistance > 3) {

                    mc.player.connection.sendPacket(new CPacketPlayer(true));
                    mc.player.fallDistance = (float) Math.random();
                }
            }*/
        }
    }
}
