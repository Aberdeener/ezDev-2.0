package me.aberdeener.ezdev.managers;

import lombok.Getter;
import me.aberdeener.ezdev.ezDev;

import java.io.File;
import java.io.IOException;

public class VariableManager {

    @Getter
    private static boolean VARIABLES_ENABLED = false;

    public static void init() {
        File variablesFile = new File(ezDev.getInstance().getDataFolder() + File.separator + "variables.yml");
        if (!variablesFile.exists()) {
            ezDev.getInstance().getLogger().info("Variables file does not exist! Attempting to create...");
            try {
                if (!ezDev.getInstance().getDataFolder().exists()) ezDev.getInstance().getDataFolder().mkdir();
                variablesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                ezDev.getInstance().getLogger().severe("Failed to create variables.yml file!");
                return;
            }
        }
        try {
            ezDev.getInstance().getConfig().load(variablesFile);
            VARIABLES_ENABLED = true;
        } catch (Exception e) {
            e.printStackTrace();
            ezDev.getInstance().getLogger().severe("Failed to load variables from file!");
        }
    }

    public static boolean isVariable(String key) {
        if (!VARIABLES_ENABLED) return false;
        key = extractKey(key);
        return ezDev.getInstance().getConfig().get(key) != null;
    }

    public static String get(String key) {
        if (!VARIABLES_ENABLED) return key;
        key = extractKey(key);
        Object value = ezDev.getInstance().getConfig().get(key);
        if (value == null) return key;
        return value.toString();
    }

    private static String extractKey(String raw) {
        try {
            return raw.substring(raw.indexOf("{") + 1, raw.indexOf("}"));
        } catch (StringIndexOutOfBoundsException ignored) { return raw; }
    }
}
