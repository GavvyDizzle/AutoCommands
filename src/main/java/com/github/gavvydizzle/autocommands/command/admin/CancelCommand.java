package com.github.gavvydizzle.autocommands.command.admin;

import com.github.gavvydizzle.autocommands.RunManager;
import com.github.gavvydizzle.autocommands.command.AdminCommandManager;
import com.github.mittenmc.serverutils.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class CancelCommand extends SubCommand {

    private final RunManager runManager;

    public CancelCommand(AdminCommandManager commandManager, RunManager runManager) {
        this.runManager = runManager;

        setName("cancel");
        setDescription("Cancel the running job");
        setSyntax("/" + commandManager.getCommandDisplayName() + " cancel");
        setColoredSyntax(ChatColor.YELLOW + getSyntax());
        setPermission(commandManager.getPermissionPrefix() + getName().toLowerCase());
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        runManager.cancelJob(sender);
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}