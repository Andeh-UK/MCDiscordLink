package com.bisecthosting.mcordlink.listeners;

import com.bisecthosting.mcordlink.MCordLink;
import com.bisecthosting.mcordlink.yaml.YamlCreation;
import com.bisecthosting.mcordlink.requests.HTTP;

import com.marcusslover.plus.lib.sound.Note;
import com.marcusslover.plus.lib.text.Text;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
import java.util.Map;

public class JoinListener implements Listener {

    private YamlCreation yamlCreation = null;
    private HTTP API = null;

    public JoinListener(YamlCreation yamlCreation, HTTP API) {
        this.yamlCreation = yamlCreation;
        this.API = API;
    }

    public String generate_code() {
        int code = (int) Math.floor(Math.random() * (9999 - 1000 + 1) + 1000);
        String digit_code = Integer.toString(code);
        String hub_number = this.yamlCreation.getHubNumber();
        return hub_number + digit_code;
    }

    private void send_code(Player player, String code) {
        Text.of(" ").send(player);
        Text.of("&bWelcome! &ePlease send the code &a&l"+code+" &eto the &a#link &echat.").send(player);
        Text.of(" ").send(player);
//        player.sendMessage(
//                ChatColor.YELLOW + "Your code is " + ChatColor.GREEN + ChatColor.UNDERLINE
//                        + code + ChatColor.RESET + ChatColor.YELLOW + ". Please send this code to " +
//                        ChatColor.RED + "BisectStudios Verification#1234!"
//
//        );
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        Player player = event.getPlayer();
        String name = player.getName();
        String hub_number = this.yamlCreation.getHubNumber();
        Map<String, String> player_data = this.API.getPlayerByName(name);
        player.sendMessage(player_data.toString());
        String code = player_data.get("code");
        String discord_id = player_data.get("discord_id");
        Note.of(Sound.ENTITY_EXPERIENCE_ORB_PICKUP).play(player);
        if (code == null) {
            String new_code = generate_code();
            this.API.addPlayer(new_code, player.getName());
            this.send_code(player, new_code);
            MCordLink.prompt.addPlayer(player);
        } else {
            if (discord_id == null) {
                if (!(code.startsWith(hub_number))) {
                    code = generate_code();
                    this.API.updateCode(name, code);
                }
                this.send_code(player, code);
                MCordLink.prompt.addPlayer(player);
            } else {
                Text.of("&aWelcome back!").send(player);
                MCordLink.prompt.removePlayer(player);
            }
        }
    }
}
