package com.bisecthosting.mcordlink.discord;

import com.bisecthosting.mcordlink.MCordLink;
import com.bisecthosting.mcordlink.yaml.YamlCreation;
import com.bisecthosting.mcordlink.database.DBConnection;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class MessageListener extends ListenerAdapter {

    private MCordLink plugin;
    private YamlCreation yamlCreation;
    private DBConnection dbConnection;
    private String channelID;
    private JDA jda;
    private String unicodeWarn = "\u26A0";
    private String unicodeYes = "\u2714";

    public MessageListener(MCordLink plugin, YamlCreation yaml, DBConnection dbConnection) {
        this.plugin = plugin;
        this.yamlCreation = yaml;
        this.dbConnection = dbConnection;
    }

    public void init() {
        this.channelID = this.yamlCreation.getChannelID();
        this.jda = this.plugin.getDiscordLauncher().getJDA();
        if (this.jda != null) {
            this.jda.addEventListener(this);
        }
    }

    public TextChannel getChannel() {
        if (this.channelID == "") {
            return null;
        }
        return this.jda.getTextChannelById(this.channelID);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String msg = message.getContentRaw();
        User user = event.getAuthor();

        if (user.isBot() || user.isSystem()) {
            return;
        }

        if (this.getChannel() == null) {
            return;
        }

        if (event.getChannel().equals(this.getChannel())) {
            if (msg.length() != 5) {
                try {
                    message.delete().queue();
                } catch (ErrorResponseException e) {
                    return;
                }
                return;
            }

            String hub_number = this.yamlCreation.getHubNumber();
            if (!(msg.startsWith(hub_number))) {
                return;
            }

            try {
                Integer.parseInt(msg);
            } catch (NumberFormatException e) {
                message.delete().queue();
                return;
            }
            Map<String, String> player_data = this.dbConnection.getPlayerByCode(msg);
            String minecraft_name = player_data.get("minecraft_name");
            if (minecraft_name == null) {
                message.addReaction(Emoji.fromUnicode(this.unicodeWarn)).queue();
            } else {
                String role_id = this.yamlCreation.getRoleID();
                Guild guild = event.getGuild();
                Role role = guild.getRoleById(role_id);
                guild.addRoleToMember(user, role).queue();
                this.dbConnection.attachDiscord(msg, event.getAuthor().getId());
                List reactions = message.getReactions();
                if (reactions.toArray().length == 0) {
                    message.addReaction(Emoji.fromUnicode(this.unicodeYes)).queue();
                }
                Player player = this.plugin.getServer().getPlayer(minecraft_name);
                assert player != null;
                player.sendMessage("Successfully Connected to Discord User " + user.getName() + "#" + user.getDiscriminator());
//                QueueFunction.addQueue(player);
                Bukkit.getScheduler().runTask(plugin, new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set ajqueue.queue.game");
                    }
                });

                Bukkit.getScheduler().runTask(plugin, new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ajqueue:joinq " + player.getName() + " game");
                    }
                });

            }
        }
    }
}