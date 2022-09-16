package com.bisecthosting.mcordlink.discord;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.block.data.type.Gate;

public class DiscordLauncher {

    private JDA jda;
    public void init(String token) {
        if(token != "") {
            this.beginBot(token);
        }
    }

    public JDA getJDA() {
        return this.jda;
    }

    private void beginBot(String token) {
        jda = JDABuilder.create(
                token,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_MESSAGES)
                .build();
    }
}
