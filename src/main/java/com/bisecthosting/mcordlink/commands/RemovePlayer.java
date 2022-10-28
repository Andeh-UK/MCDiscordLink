package com.bisecthosting.mcordlink.commands;

import com.bisecthosting.mcordlink.MCordLink;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Map;


public class RemovePlayer implements CommandExecutor {

    private MCordLink plugin;

    public RemovePlayer(MCordLink plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("You didn't specify a player to Remove!");
            return false;
        }

        boolean isPermitted = false;

        if (sender instanceof Player) {
            if(sender.hasPermission("mcordlink.removeplayer")) {
                isPermitted = true;
            }
        } else {
            isPermitted = true;
        }

        if (isPermitted) {
            String targetName = args[0];

            Map<String, String> player_data = null;
            try {
                player_data = MCordLink.getPlugin().API.getPlayerByName(targetName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (player_data == null) {
                sender.sendMessage("Player with tag " + targetName + " is not currently registered on the database.");
            } else {
                try {
                    MCordLink.getPlugin().API.removePlayer(targetName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                sender.sendMessage("Successfully removed player " + targetName + " from the database.");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+targetName+" permission unset ajqueue.queue.game");
            }
            return true;
        } else {
            sender.sendMessage("You do not have permissions to run this command.");
        }
        return true;
    }
}