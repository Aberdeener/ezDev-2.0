package me.aberdeener.ezdev.models;

import lombok.Getter;
import org.bukkit.event.Event;

public class Listener {

    @Getter
    private final Class<? extends Event> event;
    @Getter
    private final Script script;

    public Listener(Class<? extends Event> event, Script script) {
        this.event = event;
        this.script = script;

    }

    public void execute() {
    }

}
