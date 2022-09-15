package com.bisecthosting.mcordlink.listeners;

import com.marcusslover.plus.lib.text.Text;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    static int generate_code() {
        int code = (int) Math.floor(Math.random() * (9999 - 1000 + 1) + 1000);
        // Do some kinda check here to see if the code already exists for a different player, if it does then generate
        // another.
        // Jake can you do the thing where we store codes alongside mc usernames + discord user IDs pls
        return code;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        int code = generate_code();
        player.sendMessage(
                ChatColor.YELLOW + "Your code is " + ChatColor.GREEN + ChatColor.UNDERLINE
                        + code + ChatColor.RESET + ChatColor.YELLOW + ". Please send this code to " +
                        ChatColor.RED + "BisectStudios Verification#1234!"

        );
        Text.of("&7 Coc").send(player);
    }

}
