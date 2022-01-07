package net.minecraft.pentahack.modules.world;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventMotion;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.BooleanSetting;
import net.minecraft.pentahack.settings.NumberSetting;
import net.minecraft.pentahack.util.BlockUtil;
import net.minecraft.pentahack.util.Timer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
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
    private Timer timer = new Timer();

    public NumberSetting delay = new NumberSetting("delay", 0, 0, 1000);
    public BooleanSetting eagle = new BooleanSetting("eagle", false);

    public ScaffoldModule() {
        super("Scaffold", Keyboard.KEY_NONE, Category.WORLD, "places blocks under the player");
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
            rotated = false;
            currentPos = null;
            currentFacing = null;
            BlockPos pos = mc.player.getPosition().add(0, -1, 0);

            if (mc.world.getBlockState(pos) instanceof BlockAir){
                setBlockAndFacing(pos);

                if (currentPos != null){
                    float [] facing = BlockUtil.getDirectionToBlock(currentPos.getX(), currentPos.getY(), currentPos.getZ(), currentFacing);
                    float yaw = facing[0];
                    float pitch = facing[1] + 9;
                    rotated = true;
                    ((EventMotion) e).setY(yaw);
                    ((EventMotion) e).setPitch(pitch);
                }
            }

        }
    }

    public void setBlockAndFacing(BlockPos var1) {
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
        }
    }

}
