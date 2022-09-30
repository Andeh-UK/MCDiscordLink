package com.bisecthosting.mcordlink;

import com.bisecthosting.mcordlink.commands.ClearDatabase;
import com.bisecthosting.mcordlink.commands.RemovePlayer;
import com.bisecthosting.mcordlink.discord.DiscordLauncher;
import com.bisecthosting.mcordlink.discord.MessageListener;

import com.bisecthosting.mcordlink.database.DBConnection;
import com.bisecthosting.mcordlink.listeners.JoinListener;
import com.bisecthosting.mcordlink.yaml.YamlCreation;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MCordLink extends JavaPlugin implements Listener {

    private static MCordLink plugin;
    private DiscordLauncher discordLauncher = new DiscordLauncher();
    private YamlCreation yamlCreation = new YamlCreation(this);
    private DBConnection dbConnection = new DBConnection();
    private MessageListener messageListener = new MessageListener(this, this.yamlCreation, this.dbConnection);
    public Connection connection = null;



    @Override
    public void onEnable() {
        Logger logger = this.getLogger();
        logger.log(Level.INFO, "Loading MCordLink...");

        this.yamlCreation.init();
        dbConnection.init(logger, this.yamlCreation.getDatabaseURI(), this);
        dbConnection.createTables();
        this.connection = dbConnection.createConnection();

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(
                new JoinListener(this.dbConnection, this.yamlCreation), this);
        logger.log(Level.INFO, "Registered Events");
        getCommand("removeplayer").setExecutor(new RemovePlayer());
        getCommand("cleardatabase").setExecutor(new ClearDatabase());

        this.discordLauncher.init(this.yamlCreation.getBotToken());
        this.messageListener.init();
        plugin = this;
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

    public static DBConnection getConnection() {
        return plugin.dbConnection;
    }


}
