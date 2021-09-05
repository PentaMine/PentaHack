package net.minecraft.PentaHack.events.listeners;

import net.minecraft.PentaHack.events.Event;

public class EventMouse extends Event<EventMouse> {
    public int mouseX, mouseY, mouseButton;


    public EventMouse(int mouseX, int mouseY, int mouseButton) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.mouseButton = mouseButton;

    }

    public int getMouseX() {
        return mouseX;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public int getMouseButton() {
        return mouseButton;
    }

    public void setMouseButton(int mouseButton) {
        this.mouseButton = mouseButton;
    }

}
