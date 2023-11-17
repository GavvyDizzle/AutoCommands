package com.github.gavvydizzle.autocommands.command;

import com.github.gavvydizzle.autocommands.RunManager;
import com.github.gavvydizzle.autocommands.command.admin.CancelCommand;
import com.github.gavvydizzle.autocommands.command.admin.HelpCommand;
import com.github.gavvydizzle.autocommands.command.admin.ReloadCommand;
import com.github.gavvydizzle.autocommands.command.admin.RunCommand;
import com.github.mittenmc.serverutils.CommandManager;
import org.bukkit.command.PluginCommand;

public class AdminCommandManager extends CommandManager {

    public AdminCommandManager(PluginCommand command, RunManager runManager) {
        super(command);

        registerCommand(new CancelCommand(this, runManager));
        registerCommand(new HelpCommand(this));
        registerCommand(new ReloadCommand(this, runManager));
        registerCommand(new RunCommand(this, runManager));
        sortCommands();
    }
}