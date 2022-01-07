package net.minecraft.pentahack.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.debug.DebugRendererCollisionBox;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.events.Event;
import net.minecraft.pentahack.events.listeners.EventRender;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.util.Timer;
import net.minecraft.pentahack.util.render.RenderUtils;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.AxisAlignedBB;
import org.darkstorm.minecraft.gui.util.RenderUtil;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.GLUtils;

import javax.annotation.Nullable;
import java.sql.Time;
import java.sql.Wrapper;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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
        /*
        for (Object entity : mc.world.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                ((EntityLivingBase) entity).setGlowing(false);
            }
        }

*/
    }

    @Override
    public void onEvent(Event e) {
        for (Entity entity : mc.world.loadedEntityList) {
            /*if (entity instanceof EntityLivingBase) {
                ((EntityLivingBase) entity).setGlowing(true);
            }*/
            entity.setGlowing(true);

            if (entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getEntityId() == mc.world.loadedEntityList.get(0).getEntityId()){
                AxisAlignedBB axis = ((EntityLivingBase) entity).boundingBox;
                //Client.addCustomChatMessage(this.name, String.valueOf(axis) + " ID: " + ((EntityLivingBase) entity).getEntityId());
            }
        }
    }
}


