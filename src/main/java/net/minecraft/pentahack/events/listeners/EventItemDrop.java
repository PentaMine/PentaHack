package net.minecraft.pentahack.events.listeners;

import net.minecraft.pentahack.events.Event;

public class EventItemDrop extends Event<EventItemDrop> {

    public int timesDropped;

    public EventItemDrop() {
        this.timesDropped = 0;
    }


    public int getTimesDropped() {
        return timesDropped;
    }

}
