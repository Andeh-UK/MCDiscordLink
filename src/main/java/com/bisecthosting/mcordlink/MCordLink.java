package com.bisecthosting.mcordlink;

import com.bisecthosting.mcordlink.discord.DiscordLauncher;
import com.bisecthosting.mcordlink.discord.MessageListener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class MCordLink extends JavaPlugin implements Listener {

    private DiscordLauncher discordLauncher = new DiscordLauncher();
    private MessageListener messageListener = new MessageListener(this);

    static int generate_code() {
        int code = (int) Math.floor(Math.random() * (9999 - 1000 + 1) + 1000);
        // Do some kinda check here to see if the code already exists for a different player, if it does then generate
        // another.
        // Jake can you do the thing where we store codes alongside mc usernames + discord user IDs pls
        return code;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        Logger logger = this.getLogger();
        logger.log(Level.INFO, "Loading MCordLink...");

        getServer().getPluginManager().registerEvents(this, this);
        logger.log(Level.INFO, "Registered Events");

        this.discordLauncher.init();
        logger.log(Level.INFO, "Launching Discord Bot...");
        this.messageListener.init();
        logger.log(Level.INFO, "Registering Message Event Listener.");
    }

    public static MCordLink getInstance() {
        return JavaPlugin.getPlugin(MCordLink.class);
    }

    public DiscordLauncher getDiscordLauncher() {
        return this.discordLauncher;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        Logger logger = this.getLogger();
        logger.log(Level.INFO, "Plugin has unloaded.");
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

    }
}
