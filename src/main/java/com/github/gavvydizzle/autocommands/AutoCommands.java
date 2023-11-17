package com.github.gavvydizzle.autocommands;

import com.github.gavvydizzle.autocommands.command.AdminCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class AutoCommands extends JavaPlugin {

    private static AutoCommands instance;

    @Override
    public void onEnable() {
        instance = this;

        RunManager runManager = new RunManager(instance);
        new AdminCommandManager(getCommand("autocommands"), runManager);
    }


    public static AutoCommands getInstance() {
        return instance;
    }
}
