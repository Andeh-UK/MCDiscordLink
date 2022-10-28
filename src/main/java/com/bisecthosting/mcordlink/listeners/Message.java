package com.bisecthosting.mcordlink.listeners;

import com.bisecthosting.mcordlink.requests.HTTP;
import com.marcusslover.plus.lib.text.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Map;

public class Message {

    private HTTP API = null;

    public Message(HTTP API) {
        this.API = API;
    }

    public void everyMinute() throws IOException {

        for (Player p : Bukkit.getOnlinePlayers()) {
            String code = getCode(p);

            if (!hasLinked(p)) {
                Text.of(" ").send(p);
                Text.of("&bYou haven't linked your account yet!").send(p);
                Text.of("&eSend the code &a&l"+code+" &eto the &a#link &echat.").send(p);
                Text.of(" ").send(p);
            }

        }
    }

    public String getCode(Player player) throws IOException {
        Map<String, String> player_data = this.API.getPlayerByName(player.getName());
        return player_data.get("code");
    }

    public boolean hasLinked(Player player) throws IOException {
        Map<String, String> player_data = this.API.getPlayerByName(player.getName());
        String discord_id = player_data.get("discord_id");
        return discord_id != null;
    }
}
