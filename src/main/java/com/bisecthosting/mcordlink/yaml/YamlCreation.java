package com.bisecthosting.mcordlink.yaml;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;


import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import com.bisecthosting.mcordlink.MCordLink;

public class YamlCreation {

    private MCordLink plugin;
    private File file;
    public YamlCreation(MCordLink main) {
        this.plugin = main;
        this.file = new File(this.plugin.getDataFolder(), "config.yml");
    }

    public void init() {
        if(!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdir();
        }

        if(!this.file.exists()) {
            try {
                this.file.createNewFile();
                this.makeDefaultConfiguration().save(this.file);
                this.plugin.getLogger().log(Level.INFO, "Created Default Config");
            } catch (IOException e) {
                this.plugin.getLogger().info(ChatColor.RED + "" + ChatColor.BOLD + "[ERROR]" + ChatColor.WHITE
                        + "Unable to create config file.");
            }
        }
    }

    public String getChannelID() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(this.file);
        return config.getString("CHANNEL_ID");
    }

    public String getDatabaseURI() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(this.file);
        String host = config.getString("DB_HOST");
        String name = config.getString("DB_NAME");
        String username = config.getString("DB_USERNAME");
        String password = config.getString("DB_PASSWORD");
        System.out.println("host: " + host + " name: " + name + " username: " + username + " password: " + password);
        return "jdbc:mysql://" + host + "/" + name + "?user=" + username + "&password=" + password;
    }

    public String getBotToken() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(this.file);
        return config.getString("BOT_TOKEN");
    }

    private YamlConfiguration makeDefaultConfiguration() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(this.file);

        config.setComments("DC", Arrays.asList("Welcome to the MCordLink Configuration.", "", "",
        "Made by BH | Hiraku#2623. For any issues of any bugs, please contact him on discord @ " +
                "https://discord.com/users/1012482462586241134", "To begin with, enter your " +
                "Discord Bot Token, Channel ID and MySQL URI below."));

        config.set("BOT_TOKEN", "");
        config.set("CHANNEL_ID", "");
        config.set("DB_HOST", "");
        config.set("DB_NAME", "");
        config.set("DB_USERNAME", "");
        config.set("DB_PASSWORD", "");


        return config;
    }
}
