package com.bisecthosting.mcordlink;

import com.bisecthosting.mcordlink.discord.DiscordLauncher;
import com.bisecthosting.mcordlink.discord.MessageListener;

import com.bisecthosting.mcordlink.listeners.JoinListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class MCordLink extends JavaPlugin implements Listener {

    private DiscordLauncher discordLauncher = new DiscordLauncher();
    private MessageListener messageListener = new MessageListener(this);



    @Override
    public void onEnable() {
        // Plugin startup logic

        Logger logger = this.getLogger();
        logger.log(Level.INFO, "Loading MCordLink...");

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        logger.log(Level.INFO, "Registered Events");

        this.discordLauncher.init();

        // this.messageListener.init();
        // logger.log(Level.INFO, "Registering Message Event Listener.");
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

}
