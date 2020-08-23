package me.aberdeener.ezdev;

import lombok.Getter;
import me.aberdeener.ezdev.models.Listener;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.Set;

public class ListenerManager implements org.bukkit.event.Listener {

    @Getter
    private static final Set<Listener> listeners = new HashSet<>();

    public static Set<Listener> getListeners(Class<? extends Event> event) {

        Set<Listener> availableListeners = new HashSet<>();

        for (Listener listener : listeners) if (listener.getEvent() == event) availableListeners.add(listener);

        return availableListeners;
    }

    public static Class<? extends Event> getEvent(String token) {
        switch (token) {
            case "join":
                return PlayerJoinEvent.class;
            case "quit":
                return PlayerQuitEvent.class;
            default:
                return null;
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Set<Listener> listeners = getListeners(event.getClass());
        for (Listener listener : listeners) listener.execute();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Set<Listener> listeners = getListeners(event.getClass());
        for (Listener listener : listeners) listener.execute();
    }
}
