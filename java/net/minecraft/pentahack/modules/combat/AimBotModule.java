package net.minecraft.pentahack.modules.combat;

import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventUpdate;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.BooleanSetting;
import net.minecraft.pentahack.settings.NumberSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AimBotModule extends Module {

    public NumberSetting range = new NumberSetting("Range", 4, 1, 6);
    public BooleanSetting hidden = new BooleanSetting("Hidden", false);


    public AimBotModule() {
        super("AimBot", Keyboard.KEY_U, Category.COMBAT);
        this.addSettings(range, hidden);
    }

    public static Minecraft mc = Minecraft.getMinecraft();

    public void onEnable() {

    }

    public void onDisable() {

    }

    public static float[] rotations(Entity e) {
        /*
        double d0 = entity.posX - mc.player.posX;
        double d1 = entity.posY - (mc.player.posY + (double) mc.player.getEyeHeight());
        double d2 = entity.posZ - mc.player.posZ;
        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        float f = (float)(MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
        float f1 = (float)(-(MathHelper.atan2(d1, d3) * (180D / Math.PI)))/3;

        return new float[]{f, f1};


                          / | \
                         /  |  \
                            |
                            |

        |''\     |'''|  |'''|       |''\     |'''|  |'''|
        |   |    |   |  |   |       |   |    |   |  |   |
        |../     |...|  |...|       |../     |...|  |...|



        */

        double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.player.posX;
        double deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.player.posY + mc.player.getEyeHeight();
        double deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.player.posZ;
        double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        float yaw = (float) Math.toDegrees(-Math.atan(deltaX - deltaZ));
        float pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));

        if (deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        } else if (deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }

        /*
        yaw += (yaw / 100) * offset;
        pitch += (pitch / 100) * offset;
         */

        return new float[]{yaw, pitch};
    }

    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre()) {
                List<EntityLivingBase> targets = (List) mc.world.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());

                targets = targets.stream().filter(en -> en.getDistanceToEntity(mc.player) < range.getValue() && en != mc.player).collect(Collectors.toList());
                targets.sort(Comparator.comparingDouble(ent -> ((EntityLivingBase) ent).getDistanceToEntity(mc.player)));
                targets.stream().filter(EntityLivingBase::isEntityAlive);
                if (!targets.isEmpty()) {
                    mc.player.rotationYaw = rotations(targets.get(0))[0];
                    mc.player.rotationPitch = rotations(targets.get(0))[1];
                }
            }
        }
    }
}
