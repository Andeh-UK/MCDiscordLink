package com.bisecthosting.mcordlink.discord;

import com.bisecthosting.mcordlink.MCordLink;

import com.bisecthosting.mcordlink.yaml.YamlCreation;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    private MCordLink plugin;
    private YamlCreation yamlCreation;
    private String channelID;
    private JDA jda;
    private char unicodeWarn = '\u26A0';
    private char unicodeYes = '\u2714';
    public MessageListener(MCordLink main, YamlCreation yaml) {
        this.plugin = main;
        this.yamlCreation = yaml;
    }

    public void init() {
        this.channelID = this.yamlCreation.getChannelID();
        this.jda = this.plugin.getDiscordLauncher().getJDA();
        this.jda.addEventListener(this);
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
            // Do check to see if code exists, if it does remove the code and give the user a discord role
        }
    }

}
