package net.minecraft.pentahack.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.MapData;

public class BlockUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static float[] getDirectionToBlock(int var0, int var1, int var2, EnumFacing var3) {
        EntityEgg var4 = new EntityEgg(mc.world);
        var4.posX = (double) var0 + 0.5d;
        var4.posY = (double) var1 + 0.5d;
        var4.posZ = (double) var2 + 0.5d;
        var4.posX += (double) var3.getDirectionVec().getX() * 0.25d;
        var4.posY += (double) var3.getDirectionVec().getY() * 0.25d;
        var4.posZ += (double) var3.getDirectionVec().getZ() * 0.25d;
        return getDirectionToEntity(var4);
    }

    private static float[] getDirectionToEntity(Entity var0) {
        return new float[]{getYaw(var0) + mc.player.rotationYaw, getPitch(var0) + mc.player.rotationPitch};
    }

    public static float[] getRotationNeededForBlock(EntityPlayer paramEntityPlayer, BlockPos pos) {
        double d1 = pos.getX() - paramEntityPlayer.posX;
        double d2 = pos.getY() + 0.5 - (paramEntityPlayer.posY + paramEntityPlayer.getEyeHeight());
        double d3 = pos.getZ() - paramEntityPlayer.posZ;
        double d4 = Math.sqrt(d1 * d1 + d3 * d3);
        float f1 = (float) (Math.atan2(d3, d1) * 180d / Math.PI) - 90f;
        float f2 = (float) -(Math.atan2(d2, d4) * 180d / Math.PI);
        return new float[]{f1, f2};
    }


    public static float getYaw(Entity var0) {
        double var1 = var0.posX - mc.player.posX;
        double var3 = var0.posZ - mc.player.posZ;
        double var5;

        if (var3 < 0.0d && var1 < 0.0d) {
            var5 = 90d + Math.toDegrees(Math.atan(var3 / var1));
        } else if (var3 < 0.0d && var1 > 0.0d) {
            var5 = -90d + Math.toDegrees(Math.atan(var3 / var1));
        } else {
            var5 = Math.toDegrees(Math.atan(var3 / var1));
        }
        return MathHelper.wrapDegrees(-(mc.player.rotationYaw) - (float) var5);
    }

    public static float getPitch(Entity var0) {
        double var1 = var0.posX - mc.player.posX;
        double var3 = var0.posZ - mc.player.posZ;
        double var5 = var0.posY - 1.6d + (double) var0.getEyeHeight() - mc.player.posY;

        double var7 = (double) MathHelper.sqrt(var1 * var1 + var3 * var3);
        double var9 = -MathHelper.wrapDegrees(Math.atan(var5 / var7));
        return -MathHelper.wrapDegrees(mc.player.rotationPitch - (float) var9);
    }
}