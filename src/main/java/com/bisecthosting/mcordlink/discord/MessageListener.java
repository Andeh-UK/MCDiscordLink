package com.bisecthosting.mcordlink.discord;

import com.bisecthosting.mcordlink.MCordLink;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener  extends ListenerAdapter{

    private MCordLink plugin;
    private JDA jda;
    private char unicodeWarn = '\u26A0';
    private char unicodeYes = '\u2714';
    public MessageListener(MCordLink main) {
        this.plugin = main;
    }

    public void init() {
        this.plugin.getDiscordLauncher().getJDA();
        this.jda.addEventListener(this);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        User user = event.getAuthor();

        if (event.getChannel() instanceof PrivateChannel) {
            if (user.isBot() || user.isSystem()) {
                return;
            }
        }
    }

}
