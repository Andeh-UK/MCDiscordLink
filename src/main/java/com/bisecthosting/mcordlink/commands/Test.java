package com.bisecthosting.mcordlink.commands;

import com.bisecthosting.mcordlink.requests.Http;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class Test implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String status;
        Http API = new Http();
        try {
            status = API.getPlayers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sender.sendMessage(String.valueOf(status));
        return true;
    }
}
