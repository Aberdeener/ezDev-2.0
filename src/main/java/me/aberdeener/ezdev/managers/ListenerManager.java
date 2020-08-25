package me.aberdeener.ezdev.managers;

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

    private static Set<Listener> getListeners(Class<? extends Event> event) {

        Set<Listener> availableListeners = new HashSet<>();

        for (Listener listener : getListeners()) if (listener.getEvent() == event) availableListeners.add(listener);

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
    public void onPlayerJoin(PlayerJoinEvent event) { handle(event); }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) { handle(event); }

    private void handle(Event event) {
        for (Listener listener : getListeners(event.getClass())) listener.execute();
    }
}
