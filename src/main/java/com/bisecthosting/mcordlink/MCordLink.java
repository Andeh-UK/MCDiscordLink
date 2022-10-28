package com.bisecthosting.mcordlink;

import com.bisecthosting.mcordlink.commands.ClearDatabase;
import com.bisecthosting.mcordlink.commands.RemovePlayer;
import com.bisecthosting.mcordlink.commands.Test;
import com.bisecthosting.mcordlink.discord.DiscordLauncher;
import com.bisecthosting.mcordlink.discord.MessageListener;

import com.bisecthosting.mcordlink.listeners.JoinListener;
import com.bisecthosting.mcordlink.listeners.Message;
import com.bisecthosting.mcordlink.yaml.YamlCreation;
import com.bisecthosting.mcordlink.requests.HTTP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MCordLink extends JavaPlugin implements Listener {

    private static MCordLink plugin;
    public static BossBar prompt;
    private static Message msg;
    private DiscordLauncher discordLauncher = new DiscordLauncher();
    private YamlCreation yamlCreation = new YamlCreation(this);
    private MessageListener messageListener = new MessageListener(this, this.yamlCreation, this.API);
    public HTTP API = new HTTP();




    @Override
    public void onEnable() {
        prompt = Bukkit.createBossBar
                (ChatColor.YELLOW+"Please send the code "+ChatColor.GREEN+ChatColor.BOLD+"in chat"+
                        ChatColor.YELLOW+" to the "+ChatColor.GREEN+"#link "+ChatColor.YELLOW+"chat.",
                        BarColor.GREEN, BarStyle.SOLID);

        Logger logger = this.getLogger();
        logger.log(Level.INFO, "Loading MCordLink...");

        this.yamlCreation.init();
        msg = new Message(this.API);

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(
                new JoinListener(this.yamlCreation, this.API), this);
        logger.log(Level.INFO, "Registered Events");
        getCommand("removeplayer").setExecutor(new RemovePlayer(this));
        getCommand("cleardatabase").setExecutor(new ClearDatabase());
        getCommand("test").setExecutor(new Test());

        this.discordLauncher.init(this.yamlCreation.getBotToken());
        this.messageListener.init();
        plugin = this;
        logger.log(Level.INFO, "Registering Message Event Listener.");

        init();
    }

    public static MCordLink getInstance() {
        return JavaPlugin.getPlugin(MCordLink.class);
    }

    public DiscordLauncher getDiscordLauncher() {
        return this.discordLauncher;
    }

    @Override
    public void onDisable() {
        this.discordLauncher.getJDA().shutdown();
        Logger logger = this.getLogger();
        logger.log(Level.INFO, "Shut Down Discord Bot.");
        logger.log(Level.INFO, "Plugin has unloaded.");
    }

    public static MCordLink getPlugin() {
        return plugin;
    }

    public static YamlCreation getYaml() {
        return plugin.yamlCreation;
    }

    public static void init() {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    msg.everyMinute();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.runTaskTimer(MCordLink.getPlugin(MCordLink.class), 0L, 1200L);
    }
}
