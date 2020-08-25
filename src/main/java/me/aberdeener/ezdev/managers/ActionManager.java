package me.aberdeener.ezdev.managers;

import lombok.Getter;
import me.aberdeener.ezdev.models.Action;

import java.util.ArrayList;
import java.util.List;

public class ActionManager {

    @Getter
    private static final List<Action> actions = new ArrayList<>();

    public static void addAction(Action action) {
        getActions().add(action);
    }

    public static Action findAction(String[] tokens) {
        for (Action action : getActions()) {
            if (action.getPhrase().equals(tokens[0])) {
                if (action.getLength() == -1 || action.getLength() == tokens.length) return action;
            }
        }
        return null;
    }
}
