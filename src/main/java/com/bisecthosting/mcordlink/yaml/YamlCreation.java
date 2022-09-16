package com.bisecthosting.mcordlink.yaml;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;


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

                // make default config go here
            } catch (IOException e) {
                this.plugin.getLogger().info(ChatColor.RED + "" + ChatColor.BOLD + "[ERROR]" + ChatColor.WHITE
                        + "Unable to create config file.");
            }
        }
    }

    public String getChannelID() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(this.file);
        return config.getString("Channel ID");
    }

    private YamlConfiguration makeDefaultConfiguration() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(this.file);

        FileConfiguration configFile = YamlConfiguration.loadConfiguration(this.file);
        configFile.setComments("MCL", Arrays.asList("Welcome to the MCordLink Configuration.", "", "",
        "Made by BH | Hiraku#2623. For any issues of any bugs, please contact him on discord @ " +
                "https://discord.com/users/1012482462586241134", "To begin with, enter your " +
                "Discord Text Channel ID below.", "", "", "For example: \"Channel ID\": \"983464907204882435\""));

        try {
            configFile.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        config.set("Channel ID", "");

        return config;
    }
}
