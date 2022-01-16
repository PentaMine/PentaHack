package net.minecraft.pentahack.modules.world;

import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventMotion;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.BooleanSetting;
import net.minecraft.pentahack.settings.NumberSetting;
import net.minecraft.pentahack.util.BlockUtil;
import net.minecraft.pentahack.util.Timer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Keyboard;

import javax.swing.text.StyledEditorKit;
import java.sql.Time;

public class ScaffoldModule extends Module {

    /**
     * put the module setting above this comment
     */

    private BlockPos currentPos;
    private EnumFacing currentFacing;
    private boolean rotated = false;
    public float yaw;
    public float pitch;
    public Timer timer = new Timer();

    public NumberSetting delay = new NumberSetting("delay", 0, 0, 1000);

    public ScaffoldModule() {
        super("Scaffold", Keyboard.KEY_NONE, Category.WORLD, "places blocks under the player");
        addSettings(delay);
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
        if (e instanceof EventMotion && e.isPre()) {
            if (timer.hasTimeElapsed((long) delay.getValue(), true)) {
                pitch = ((EventMotion) e).pitch;
                yaw = ((EventMotion) e).yaw;

                if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemBlock && mc.player.fallDistance < 3f && mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ)).getBlock() instanceof BlockAir) {
                    if (!placeBlock(new BlockPos(mc.player.posX, mc.player.posY - 1 - mc.player.fallDistance, mc.player.posZ), true)) {
                        // failure management
                        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ)).getBlock() instanceof BlockAir) {
                            placeBlock(new BlockPos(mc.player.posX - 1, mc.player.posY - 1 - mc.player.fallDistance, mc.player.posZ), true);
                        }
                        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ)).getBlock() instanceof BlockAir) {
                            placeBlock(new BlockPos(mc.player.posX + 1, mc.player.posY - 1 - mc.player.fallDistance, mc.player.posZ), true);
                        }
                        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ)).getBlock() instanceof BlockAir) {
                            placeBlock(new BlockPos(mc.player.posX, mc.player.posY - 1 - mc.player.fallDistance, mc.player.posZ - 1), true);
                        }
                        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ)).getBlock() instanceof BlockAir) {
                            placeBlock(new BlockPos(mc.player.posX, mc.player.posY - 1 - mc.player.fallDistance, mc.player.posZ + 1), true);
                        }
                    }
                }
                ((EventMotion) e).setPitch(pitch);
                ((EventMotion) e).setYaw(yaw);
            }
        }
    }

    public boolean placeBlock(BlockPos pos, boolean swing) {
        if (setBlockAndFacing(pos)) {
            mc.playerController.processRightClickBlock(mc.player, mc.world, pos, currentFacing, new Vec3d(currentPos.getX(), currentPos.getY(), currentPos.getZ()), EnumHand.MAIN_HAND);
            if (swing) {
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }

            float[] facing = BlockUtil.getDirectionToBlock(currentPos.getX(), currentPos.getY(), currentPos.getZ(), currentFacing);

            float varYaw = facing[0];
            float varPitch = facing[1] + 90;

            yaw = varYaw;
            pitch = varPitch;

            mc.player.rotationYaw = yaw;
            mc.player.rotationPitch = pitch;

            return true;
        }
        return false;
    }

    public boolean setBlockAndFacing(BlockPos var1) {
        if (mc.world.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.AIR) {
            this.currentPos = var1.add(0, -1, 0);
            currentFacing = EnumFacing.UP;
        } else if (mc.world.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.AIR) {
            this.currentPos = var1.add(-1, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (mc.world.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.AIR) {
            this.currentPos = var1.add(1, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (mc.world.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.AIR) {
            this.currentPos = var1.add(0, 0, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (mc.world.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.AIR) {
            this.currentPos = var1.add(0, 0, 1);
            currentFacing = EnumFacing.NORTH;
        } else {
            currentPos = null;
            currentFacing = null;
            return false;
        }
        return true;
    }

}
