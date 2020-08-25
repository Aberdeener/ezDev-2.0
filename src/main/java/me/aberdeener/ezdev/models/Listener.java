package me.aberdeener.ezdev.models;

import lombok.Getter;
import me.aberdeener.ezdev.ezDev;
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
        ezDev.getInstance().getLogger().info("ezDev event " + getEvent().getName() + " in script " + getScript().getFile());
    }

}
