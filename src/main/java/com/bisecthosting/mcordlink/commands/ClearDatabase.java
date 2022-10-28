package com.bisecthosting.mcordlink.commands;

import com.bisecthosting.mcordlink.MCordLink;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class ClearDatabase implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("mcordlink.cleardatabase")) {
                try {
                    MCordLink.getPlugin().API.removeAll();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                sender.sendMessage("Successfully cleared Database.");
            } else {
                sender.sendMessage("You do not have permissions to run this command.");
            }
        } else {
            try {
                MCordLink.getPlugin().API.removeAll();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sender.sendMessage("Successfully cleared Database.");
        }
        return true;
    }
}
