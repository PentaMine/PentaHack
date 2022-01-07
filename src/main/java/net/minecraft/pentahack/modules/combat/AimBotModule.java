package net.minecraft.pentahack.modules.combat;

import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventUpdate;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.BooleanSetting;
import net.minecraft.pentahack.settings.NumberSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AimBotModule extends Module {

    public NumberSetting range = new NumberSetting("Range", 4, 1, 6);

    public AimBotModule() {
        super("AimBot", Keyboard.KEY_NONE, Category.COMBAT);
        this.addSettings(range);
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
            if (e.isPre()) {
                List<EntityLivingBase> targets = (List) mc.world.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());

                targets = targets.stream().filter(en -> en.getDistanceToEntity(mc.player) < range.getValue() && en != mc.player).collect(Collectors.toList());
                targets.sort(Comparator.comparingDouble(ent -> ((EntityLivingBase) ent).getDistanceToEntity(mc.player)));
                targets.stream().filter(EntityLivingBase::isEntityAlive);
                if (!targets.isEmpty()) {
                    /*mc.player.rotationYaw = rotations(targets.get(0))[0];
                    mc.player.rotationPitch = rotations(targets.get(0))[1];
                    */
                    faceEntity(targets.get(0));
                }
            }
        }
    }

    public static synchronized void faceEntity(EntityLivingBase entity) {
        final float[] rotations = getRotationsNeeded(entity);

        if (rotations != null) {
            mc.player.rotationYaw = rotations[0];
            mc.player.rotationPitch = rotations[1] + 1.0F;// 14
        }
    }

    public static float[] getRotationsNeeded(Entity entity) {
        if (entity == null) {
            return null;
        }

        final double diffX = entity.posX - mc.player.posX;
        final double diffZ = entity.posZ - mc.player.posZ;
        double diffY;


        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight());
        } else {
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (mc.player.posY + mc.player.getEyeHeight());
        }

        final double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        final float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[]{mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw), mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch)};
    }
}
