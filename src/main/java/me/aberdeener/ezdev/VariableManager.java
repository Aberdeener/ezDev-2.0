package me.aberdeener.ezdev;

import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class VariableManager {

    @Getter
    private static List<String> variables;
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
        YamlConfiguration configuration = new YamlConfiguration();
        try {
            configuration.load(variablesFile);
            variables = configuration.getStringList("variables");
            VARIABLES_ENABLED = true;
        } catch (Exception e) {
            e.printStackTrace();
            ezDev.getInstance().getLogger().severe("Failed to load variables from file!");
        }
    }

}
