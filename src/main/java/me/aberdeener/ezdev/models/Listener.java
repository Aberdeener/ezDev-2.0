package me.aberdeener.ezdev.models;

import lombok.Getter;
import org.bukkit.event.Event;

public class Listener {

    @Getter
    private final Script script;
    @Getter
    private final Class<? extends Event> event;

    public Listener(Script script, Class<? extends Event> event) {

        this.script = script;
        this.event = event;
    }

    public void execute() {
    }

}
