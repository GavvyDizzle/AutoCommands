package com.github.gavvydizzle.autocommands.command.admin;

import com.github.gavvydizzle.autocommands.RunManager;
import com.github.gavvydizzle.autocommands.command.AdminCommandManager;
import com.github.mittenmc.serverutils.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class RunCommand extends SubCommand {

    private final RunManager runManager;

    public RunCommand(AdminCommandManager commandManager, RunManager runManager) {
        this.runManager = runManager;

        setName("run");
        setDescription("Run commands from the given file");
        setSyntax("/" + commandManager.getCommandDisplayName() + " run <file>");
        setColoredSyntax(ChatColor.YELLOW + getSyntax());
        setPermission(commandManager.getPermissionPrefix() + getName().toLowerCase());
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(getColoredSyntax());
            return;
        }

        runManager.runFile(sender, args[1]);
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}