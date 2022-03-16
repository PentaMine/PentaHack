package net.minecraft.pentahack.modules.world;

import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventMotion;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.BooleanSetting;
import net.minecraft.pentahack.settings.NumberSetting;
import net.minecraft.pentahack.util.BlockUtil;
import net.minecraft.pentahack.util.Timer;
import net.minecraft.pentahack.util.render.RenderUtils;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

public class ScaffoldModule extends Module {

    /**
     * put the module setting above this comment
     */

    private BlockPos currentPos;
    private EnumFacing currentFacing;
    public float yaw;
    public float pitch;
    public float prevYaw;
    public float prevPitch;
    public Timer timer = new Timer();
    public Timer timer2 = new Timer();

    public NumberSetting delay = new NumberSetting("delay", 0, 0, 1000);
    public BooleanSetting display = new BooleanSetting("display block number", true);
    public BooleanSetting test = new BooleanSetting("test", false);


    public ScaffoldModule() {
        super("Scaffold", Keyboard.KEY_NONE, Category.WORLD, "places blocks under the player");
        addSettings(delay, display, test);
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

        if (display.enabled){
            drawScreen();
        }

        findBlockToPlaceOn(new BlockPos(mc.player.posX, mc.player.posY - 1 - mc.player.fallDistance, mc.player.posZ));

        if (e instanceof EventMotion && e.isPre()) {

            isPlayerOnEdge(mc.player);

            if (timer.hasTimeElapsed((long) delay.getValue(), true) && isPlayerOnEdge(mc.player)) {

                pitch = ((EventMotion) e).pitch;
                yaw = ((EventMotion) e).yaw;

                if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemBlock && mc.player.fallDistance <= 3f) {
                    if (!placeBlock(new BlockPos(mc.player.posX, mc.player.posY - 1 - mc.player.fallDistance, mc.player.posZ), true, (EventMotion) e)) {
                        // failure management
                        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ)).getBlock() instanceof BlockAir) {
                            placeBlock(new BlockPos(mc.player.posX - 1, mc.player.posY - 1 - mc.player.fallDistance, mc.player.posZ), true, (EventMotion) e);
                        }
                        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ)).getBlock() instanceof BlockAir) {
                            placeBlock(new BlockPos(mc.player.posX + 1, mc.player.posY - 1 - mc.player.fallDistance, mc.player.posZ), true, (EventMotion) e);
                        }
                        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ)).getBlock() instanceof BlockAir) {
                            placeBlock(new BlockPos(mc.player.posX, mc.player.posY - 1 - mc.player.fallDistance, mc.player.posZ - 1), true, (EventMotion) e);
                        }
                        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ)).getBlock() instanceof BlockAir) {
                            placeBlock(new BlockPos(mc.player.posX, mc.player.posY - 1 - mc.player.fallDistance, mc.player.posZ + 1), true, (EventMotion) e);
                        }
                    }
                }

                if (test.isEnabled()) {
                    mc.player.rotationPitch = pitch;
                    mc.player.rotationYaw = yaw;
                }
            }

            if (!timer2.hasTimeElapsed(5000, true)) {
                pitch = prevPitch;
                yaw = prevYaw;
                //Client.addCustomChatMessage(this.name, "set");

            }

            ((EventMotion) e).setPitch(pitch);
            ((EventMotion) e).setYaw(yaw);

        }
    }

    public boolean placeBlock(BlockPos pos, boolean swing, EventMotion event) {
        if (event == null) {
            return false;
        }

        if (setBlockAndFacing(findBlockToPlaceOn(pos))) {
            float[] facing = BlockUtil.getDirectionToBlock(currentPos.getX(), currentPos.getY(), currentPos.getZ(), currentFacing);

            float varYaw = (float) getTheta(mc.player.motionX, mc.player.motionZ);
            float varPitch = facing[1] + 86.0f;

            yaw = varYaw;
            pitch = varPitch;

            prevPitch = varPitch;
            prevYaw = varYaw;

            ((EventMotion) event).setPitch(pitch);
            ((EventMotion) event).setYaw(yaw);

            mc.playerController.processRightClickBlock(mc.player, mc.world, pos, currentFacing, new Vec3d(currentPos.getX(), currentPos.getY(), currentPos.getZ()), EnumHand.MAIN_HAND);

            if (swing) {
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }

            timer2.reset();


            return true;
        }
        return false;
    }

    public boolean setBlockAndFacing(BlockPos var1) {

        if (var1 == null){
            return false;
        }

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

    public boolean isPlayerOnEdge(EntityPlayer playerIn) {

        double varZ = Math.abs(playerIn.posZ - (int) playerIn.posZ);
        double varX = Math.abs(playerIn.posX - (int) playerIn.posX);

        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ)).getBlock() instanceof BlockAir) {
            if (mc.player.getHorizontalFacing().equals(EnumFacing.WEST) || mc.player.getHorizontalFacing().equals(EnumFacing.EAST)) {
                return (varX > .030 && varX < .5) || (varX < .970 && varX > .5);
            }
            if (mc.player.getHorizontalFacing().equals(EnumFacing.NORTH) || mc.player.getHorizontalFacing().equals(EnumFacing.SOUTH)) {
                return (varZ > .030 && varZ < .5) || (varZ < .970 && varZ > .5);
            }
        }

        return false;
    }

    public double getTheta(double x, double y) {
        double theta = StrictMath.toDegrees(StrictMath.atan2(y, x));
        if ((theta < -360.0D) || (theta > 360.0D)) {
            theta %= 360.0D;
        }
        if (theta < 0.0D) {
            theta += 360.0D;
        }

        theta = theta + 90;

        return theta;
    }

    public int getNumberOfBlocksInInventory() {
        int number = 0;
        for (ItemStack item : mc.player.inventory.mainInventory) {
            if (item.getItem() instanceof ItemBlock){
                number += item.stackSize;
            }
        }

        return number;
    }

    public void drawScreen(){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        RenderUtils.drawRect((double) sr.getScaledWidth() / 2, (double) sr.getScaledHeight() / 2, Client.fr.getStringWidth(String.valueOf(getNumberOfBlocksInInventory())) + 10, Client.fr.FONT_HEIGHT + 4, Client.color);
        Client.fr.drawString(String.valueOf(getNumberOfBlocksInInventory()), (float) sr.getScaledWidth() / 2 + 5, (float) sr.getScaledHeight() / 2 + 2, -1);
    }

    public BlockPos findBlockToPlaceOn(BlockPos pos){

        BlockPos pos1 = pos.add(1, 0, 0);
        BlockPos pos2 = pos.add(-1, 0, 0);
        BlockPos pos3 = pos.add(0, 0, 1);
        BlockPos pos4 = pos.add(0, 0, -1);
        BlockPos pos5 = pos.add(0, -1, 0);

        BlockPos[] posList = new BlockPos[]{pos1, pos2, pos3, pos4, pos5};

        for (BlockPos pos6 : posList){
            if (!(mc.world.getBlockState(pos6).getBlock() instanceof BlockAir)){
                return pos6;
            }
        }

        return null;
    }
}
