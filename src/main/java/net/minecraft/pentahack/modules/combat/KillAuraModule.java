package net.minecraft.pentahack.modules.combat;

import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventMotion;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.BooleanSetting;
import net.minecraft.pentahack.settings.ModeSetting;
import net.minecraft.pentahack.settings.NumberSetting;
import net.minecraft.pentahack.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KillAuraModule extends Module {
    public static Minecraft mc = Minecraft.getMinecraft();
    public Timer timer = new Timer();

    public ModeSetting aim = new ModeSetting("aim", "Packet", "Packet", "Client", "None");
    public BooleanSetting noSwing = new BooleanSetting("NoSwing", false);
    public NumberSetting range = new NumberSetting("Range", 4, 1, 6);
    public BooleanSetting multiShot = new BooleanSetting("MultiShot", false);
    public BooleanSetting bypass = new BooleanSetting("Bypass", true);
    public NumberSetting delayAfterDamage = new NumberSetting("DelayAfterDamage", 10, 5, 15);

    public KillAuraModule() {
        super("KillAura", Keyboard.KEY_NONE, Category.COMBAT);
        this.addSettings(aim, noSwing, range, multiShot, bypass, delayAfterDamage);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEvent(Event e) {

        if (e instanceof EventMotion) {
            if (e.isPre()) {


                EventMotion event = (EventMotion) e;

                //sort entities by range, type and distance
                List<EntityLivingBase> targets = (List) mc.world.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());

                targets = targets.stream().filter(en -> en.getDistanceToEntity(mc.player) < range.getValue() && en != mc.player && !en.isDead && en.getHealth() > 0).collect(Collectors.toList());

                targets.sort(Comparator.comparingDouble(ent -> ((EntityLivingBase) ent).getDistanceToEntity(mc.player)));

                //targets.stream().filter(EntityPlayer.class::isInstance).collect(Collectors.toList());


                //use code above for filtering entities

                if (!targets.isEmpty()) {

                    if (bypass.enabled && !(mc.player.ticksExisted - mc.player.getLastAttackerTime() >= delayAfterDamage.value)){
                        return;
                    }

                    EntityLivingBase target = targets.get(0);

                    if (aim.getMode().equalsIgnoreCase("client")) {
                        AimBotModule.faceEntity(target);
                    }
                    if (aim.getMode().equalsIgnoreCase("packet")) {
                        float[] rotations = AimBotModule.getRotationsNeeded(target);

                        if (rotations != null) {
                            event.setYaw(rotations[0]);
                            event.setPitch(rotations[1]);
                        }
                    }

                    if (mc.player.getCooledAttackStrength(0.000000001f) == 1) {
                        //hitting the entity
                        if (multiShot.isEnabled()) {

                            for (EntityLivingBase entity : targets) {
                                mc.player.setCooldown(9999999);
                                mc.playerController.attackEntity(mc.player, entity);
                            }
                        } else {
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
}



