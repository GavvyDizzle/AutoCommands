package com.github.gavvydizzle.autocommands;

import com.github.mittenmc.serverutils.RepeatingTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;

public class RunManager {

    private final AutoCommands instance;
    private RepeatingTask repeatingTask;

    private int ticksBetweenCommands;
    private boolean stopFlag;

    public RunManager(AutoCommands instance) {
        this.instance = instance;
        repeatingTask = null;

        reload();
    }

    public void reload() {
        FileConfiguration config = instance.getConfig();
        config.options().copyDefaults(true);
        config.addDefault("ticksBetweenCommands", 1);
        instance.saveConfig();

        ticksBetweenCommands = Math.max(1, config.getInt("ticksBetweenCommands"));
    }

    public void runFile(CommandSender sender, String fileName) {
        if (!fileName.endsWith(".txt")) {
            fileName += ".txt";
        }

        if (repeatingTask != null) {
            sender.sendMessage(ChatColor.YELLOW + "Another task is running. Please wait for it to finish");
            return;
        }

        File file = new File(instance.getDataFolder(), fileName);
        if (!file.exists()) {
            sender.sendMessage(ChatColor.RED + "No file exists with the name: " + fileName);
            return;
        }

        ArrayDeque<String> lines = new ArrayDeque<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("/")) {
                    lines.add(line.substring(1));
                }
            }
        } catch (IOException e) {
            sender.sendMessage(ChatColor.RED + "[AutoCommands] Failed to read the file");
            throw new RuntimeException(e);
        }

        if (lines.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "[AutoCommands] No commands were executed. Make sure to start each line with a " + ChatColor.YELLOW + "/");
            return;
        }

        repeatingTask = new RepeatingTask(instance, 0, ticksBetweenCommands) {
            int total = 0;

            @Override
            public void run() {
                if (stopFlag) {
                    stopFlag = false;
                    cancel();
                    repeatingTask = null;

                    if (total > 0) {
                        sender.sendMessage(ChatColor.GREEN + "[AutoCommands] Successfully executed " + total + " command(s)");
                    }
                    else {
                        sender.sendMessage(ChatColor.GREEN + "[AutoCommands] No commands were executed. Make sure to start each line with a " + ChatColor.YELLOW + "/");
                    }

                    return;
                }

                if (!lines.isEmpty()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), lines.pop());
                    total++;
                }
                else {
                    cancel();
                    repeatingTask = null;
                    stopFlag = false;

                    sender.sendMessage(ChatColor.GREEN + "[AutoCommands] Successfully executed " + total + " command(s)");
                }
            }
        };
    }

    public void cancelJob(CommandSender sender) {
        if (repeatingTask != null) {
            stopFlag = true;
            sender.sendMessage(ChatColor.YELLOW + "--- Cancelled Running Job ---");
        }
        else {
            sender.sendMessage(ChatColor.YELLOW + "[AutoCommands] There is no running job");
        }
    }

}
