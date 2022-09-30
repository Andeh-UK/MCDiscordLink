package com.bisecthosting.mcordlink.listeners;

import com.bisecthosting.mcordlink.database.DBConnection;
import com.marcusslover.plus.lib.text.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class Message {

    private DBConnection dbConnection = null;

    public Message(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void everyMinute() {

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

    public String getCode(Player player) {
        Map<String, String> player_data = this.dbConnection.getPlayer(player.getName());
        return player_data.get("code");
    }

    public boolean hasLinked(Player player) {
        Map<String, String> player_data = this.dbConnection.getPlayer(player.getName());
        String discord_id = player_data.get("discord_id");
        return discord_id != null;
    }
}
