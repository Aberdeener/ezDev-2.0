package me.aberdeener.ezdev.managers;

import lombok.Getter;
import lombok.SneakyThrows;
import me.aberdeener.ezdev.models.Addon;
import me.aberdeener.ezdev.models.Listener;
import me.aberdeener.ezdev.models.ezDevException;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ListenerManager implements org.bukkit.event.Listener {

    @Getter
    private static final Set<Listener> listeners = new HashSet<>();
    @Getter
    private static final HashMap<String, Class<? extends PlayerEvent>> events = new HashMap<>();

    // TODO: Let addons create events with names and addons attached.
    // Issue: @EventHandler must exist for all Events... Workaround?
    @SneakyThrows
    public static void addEvent(String name, Class<? extends PlayerEvent> event, Addon addon) {
        if (getEvents().size() > 0) {
            for (String eventName : getEvents().keySet()) {
                if (!eventName.equalsIgnoreCase(name)) {
                    getEvents().put(name, event);
                } else {
                    throw new ezDevException("Event with same name already registered. Event: " + name);
                }
            }
        } else getEvents().put(name, event);
    }

    private static Set<Listener> getListeners(Class<? extends PlayerEvent> event) {
        Set<Listener> availableListeners = new HashSet<>();
        for (Listener listener : getListeners()) if (listener.getEvent() == event) availableListeners.add(listener);
        return availableListeners;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) { handle(event); }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) { handle(event); }

    private void handle(PlayerEvent event) {
        for (Listener listener : getListeners(event.getClass())) listener.execute(event);
    }
}
