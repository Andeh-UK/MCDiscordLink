package com.bisecthosting.mcordlink.commands;

import com.bisecthosting.mcordlink.MCordLink;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearDatabase implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("mcordlink.cleardatabase")) {
                MCordLink.getPlugin().getConnection().clearDatabase();
                sender.sendMessage("Successfully cleared Database.");
            } else {
                sender.sendMessage("You do not have permissions to run this command.");
            }
        } else {
            MCordLink.getPlugin().getConnection().clearDatabase();
            sender.sendMessage("Successfully cleared Database.");
        }
        return true;
    }
}
