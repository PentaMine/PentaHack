package net.minecraft.PentaHack.modules.combat;

import net.minecraft.PentaHack.events.Event;
import net.minecraft.PentaHack.events.listeners.EventPosition;
import net.minecraft.PentaHack.modules.Module;
import net.minecraft.PentaHack.settings.BooleanSetting;
import net.minecraft.PentaHack.settings.NumberSetting;
import net.minecraft.PentaHack.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class KillAuraModule extends Module {
    public static Minecraft mc = Minecraft.getMinecraft();
    public Timer timer = new Timer();


    public BooleanSetting clientAim = new BooleanSetting("ClientAim", false);
    public BooleanSetting noSwing = new BooleanSetting("NoSwing", false);
    public NumberSetting range = new NumberSetting("Range", 4, 1, 6);
    public NumberSetting offset = new NumberSetting("Offset %", 0, 0, 20);

    public BooleanSetting hidden = new BooleanSetting("Hidden", false);

    public KillAuraModule() {
        super("KillAura", Keyboard.KEY_R, Category.COMBAT);
        this.addSettings(clientAim, noSwing, range, hidden);
    }


    public void onEnable() {

    }

    public void onDisable() {

    }

    public void onEvent(Event e) {

        if (e instanceof EventPosition) {
            if (e.isPre()) {


                EventPosition event = (EventPosition) e;

                //sort entities by range, type and distance
                List<EntityLivingBase> targets = (List) mc.world.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());

                targets = targets.stream().filter(en -> en.getDistanceToEntity(mc.player) < range.getValue() && en != mc.player && !en.isDead && en.getHealth() > 0).collect(Collectors.toList());

                targets.sort(Comparator.comparingDouble(ent -> ((EntityLivingBase) ent).getDistanceToEntity(mc.player)));

                //targets.stream().filter(EntityPlayer.class::isInstance).collect(Collectors.toList());


                //use code above for filtering entities

                if (!targets.isEmpty()) {
                    float yaw = AimBotModule.rotations(targets.get(0))[0];
                    float pitch = AimBotModule.rotations(targets.get(0))[1];

                    yaw += (yaw / 100) * offset.getValue();
                    pitch += (pitch / 100) * offset.getValue();

                    EntityLivingBase target = targets.get(0);
                    if (clientAim.isEnabled()) {

                        mc.player.rotationYaw = yaw;
                        mc.player.rotationPitch = pitch;


                    }

                    event.setYaw(yaw);
                    event.setPitch(pitch);

                    if (mc.player.getCooledAttackStrength(0.000000001f) == 1) {
                        //hitting the entity

                        mc.playerController.attackEntity(mc.player, target);
                        if (!noSwing.isEnabled()) {
                            mc.player.swingArm(EnumHand.MAIN_HAND);
                        }
                        mc.player.resetCooldown();
                    }
                }
            }
        }
    }
}



