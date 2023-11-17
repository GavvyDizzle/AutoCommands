package com.github.gavvydizzle.autocommands.command.admin;

import com.github.gavvydizzle.autocommands.AutoCommands;
import com.github.gavvydizzle.autocommands.RunManager;
import com.github.gavvydizzle.autocommands.command.AdminCommandManager;
import com.github.mittenmc.serverutils.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends SubCommand {

    private final RunManager runManager;

    public ReloadCommand(AdminCommandManager commandManager, RunManager runManager) {
        this.runManager = runManager;

        setName("reload");
        setDescription("Reload this plugin");
        setSyntax("/" + commandManager.getCommandDisplayName() + " reload");
        setColoredSyntax(ChatColor.YELLOW + getSyntax());
        setPermission(commandManager.getPermissionPrefix() + getName().toLowerCase());
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        try {
            AutoCommands.getInstance().reloadConfig();
            runManager.reload();
            sender.sendMessage(ChatColor.GREEN + "[AutoCommands] Successfully reloaded");
        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage(ChatColor.RED + "[AutoCommands] Encountered an error when reloading. Check the console");
        }
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}