package net.minecraft.pentahack.modules.render;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.input.Keyboard;


public class EntityESPModule extends Module {

    public EntityESPModule() {
        super("EntityESP", Keyboard.KEY_NONE, Category.RENDER);
    }

    @Override
    public void onEnable() {
      /*  for (Object entity : mc.world.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                ((EntityLivingBase) entity).setGlowing(true);
            }
        }*/
    }

    @Override
    public void onDisable() {
        for (Entity entity : mc.world.loadedEntityList) {
            entity.setGlowing(true);
            if (entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getEntityId() == mc.world.loadedEntityList.get(0).getEntityId()){
                AxisAlignedBB axis = ((EntityLivingBase) entity).boundingBox;
            }
        }
    }

    @Override
    public void onEvent(Event e) {
        for (Entity entity : mc.world.loadedEntityList) {
            entity.setGlowing(true);
            if (entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getEntityId() == mc.world.loadedEntityList.get(0).getEntityId()){
                AxisAlignedBB axis = ((EntityLivingBase) entity).boundingBox;
            }
        }
    }
}


