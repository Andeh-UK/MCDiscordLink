package com.bisecthosting.mcordlink.commands;

import com.bisecthosting.mcordlink.MCordLink;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RemovePlayer implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("You didn't specify a player to Remove!");
            return false;
        }

        String targetName = args[0];

        Map<String, String> player_data = MCordLink.getPlugin().getConnection().getPlayer(targetName);
        if (player_data == null) {
            sender.sendMessage("Player with tag " + targetName + " is not currently registered on the database.");
        } else {
            MCordLink.getPlugin().getConnection().removePlayer(targetName);
            sender.sendMessage("Successfully removed player " + targetName + " from the database.");
        }
        return true;
    }
}
