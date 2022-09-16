package com.bisecthosting.mcordlink;

import com.bisecthosting.mcordlink.discord.DiscordLauncher;
import com.bisecthosting.mcordlink.discord.MessageListener;

import com.bisecthosting.mcordlink.listeners.JoinListener;
import com.bisecthosting.mcordlink.yaml.YamlCreation;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MCordLink extends JavaPlugin implements Listener {

    private DiscordLauncher discordLauncher = new DiscordLauncher();
    private YamlCreation yamlCreation = new YamlCreation(this);
    private MessageListener messageListener = new MessageListener(this, this.yamlCreation);



    @Override
    public void onEnable() {
        Logger logger = this.getLogger();
        logger.log(Level.INFO, "Loading MCordLink...");

        String db_url = "jdbc:mysql://66.248.193.2/mc155219?user=mc155219&password=3beb9537c7";
        try {
            Connection connection = DriverManager.getConnection(db_url);
            logger.log(Level.INFO, "Connected to MySQL Database.");

            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS config(channel_id BIGINT PRIMARY KEY)";
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.log(Level.INFO, "Failed to connect to MySQL Database.");
        }


        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        logger.log(Level.INFO, "Registered Events");

        this.discordLauncher.init();
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
