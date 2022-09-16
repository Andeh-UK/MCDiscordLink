package com.bisecthosting.mcordlink.discord;

import com.bisecthosting.mcordlink.MCordLink;
import com.bisecthosting.mcordlink.yaml.YamlCreation;
import com.bisecthosting.mcordlink.database.DBConnection;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Map;

public class MessageListener extends ListenerAdapter {

    private MCordLink plugin;
    private YamlCreation yamlCreation;
    private DBConnection dbConnection;
    private String channelID;
    private JDA jda;
    private String unicodeWarn = "\u26A0";
    private String unicodeYes = "\u2714";
    public MessageListener(MCordLink main, YamlCreation yaml, DBConnection dbConnection) {
        this.plugin = main;
        this.yamlCreation = yaml;
        this.dbConnection = dbConnection;
    }

    public void init() {
        this.channelID = this.yamlCreation.getChannelID();
        this.jda = this.plugin.getDiscordLauncher().getJDA();
        if(this.jda != null) {
            this.jda.addEventListener(this);
        }
    }

    public TextChannel getChannel() {
        if(this.channelID == "") {
            return null;
        }
        return this.jda.getTextChannelById(this.channelID);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        User user = event.getAuthor();

        if(user.isBot() || user.isSystem()) {
            return;
        }

        if(this.getChannel() == null) {
            return;
        }

        if(event.getChannel().equals(this.getChannel())) {
            if (msg.length() != 4) {
            event.getMessage().delete().queue();
            }

            try {
                Integer.parseInt(msg);
            } catch (NumberFormatException e) {
                event.getMessage().delete().queue();
            }
            Map<String, String> player_data = this.dbConnection.getPlayerByCode(msg);
            if (player_data.get("minecraft_name") == null) {
                event.getMessage().addReaction(Emoji.fromUnicode(this.unicodeWarn)).queue();
            } else {
                //add discord role to user then do db entry adding their discord user ID to the row that the inputted
                // code is in.
            }
        }
    }

}
