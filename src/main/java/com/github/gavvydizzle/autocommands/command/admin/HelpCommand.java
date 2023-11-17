package com.github.gavvydizzle.autocommands.command.admin;

import com.github.gavvydizzle.autocommands.command.AdminCommandManager;
import com.github.mittenmc.serverutils.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class HelpCommand extends SubCommand {

    private final AdminCommandManager commandManager;

    public HelpCommand(AdminCommandManager commandManager) {
        this.commandManager = commandManager;

        setName("help");
        setDescription("Opens this help menu");
        setSyntax("/" + commandManager.getCommandDisplayName() + " help");
        setColoredSyntax(ChatColor.YELLOW + getSyntax());
        setPermission(commandManager.getPermissionPrefix() + getName().toLowerCase());
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.GOLD + "-----(AutoCommands Commands)-----");
        for (SubCommand subCommand : commandManager.getSubcommands()) {
            sender.sendMessage(ChatColor.GOLD + subCommand.getSyntax() + " - " + ChatColor.YELLOW + subCommand.getDescription());
        }
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }

}