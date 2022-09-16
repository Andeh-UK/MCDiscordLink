package com.bisecthosting.mcordlink;

import com.bisecthosting.mcordlink.discord.DiscordLauncher;
import com.bisecthosting.mcordlink.discord.MessageListener;

import com.bisecthosting.mcordlink.database.DBConnection;
import com.bisecthosting.mcordlink.listeners.JoinListener;
import com.bisecthosting.mcordlink.yaml.YamlCreation;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class MCordLink extends JavaPlugin implements Listener {

    private DiscordLauncher discordLauncher = new DiscordLauncher();
    private YamlCreation yamlCreation = new YamlCreation(this);
    private DBConnection dbConnection = new DBConnection();
    private MessageListener messageListener = new MessageListener(this, this.yamlCreation, this.dbConnection);



    @Override
    public void onEnable() {
        Logger logger = this.getLogger();
        logger.log(Level.INFO, "Loading MCordLink...");

        this.yamlCreation.init();
        dbConnection.init(logger, this.yamlCreation.getDatabaseURI());
        dbConnection.createTables();

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new JoinListener(this.dbConnection), this);
        logger.log(Level.INFO, "Registered Events");

        this.discordLauncher.init(this.yamlCreation.getBotToken());
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
        Logger logger = this.getLogger();
        logger.log(Level.INFO, "Plugin has unloaded.");
    }

}
