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
        this.file = new File(this.plugin.getDataFolder(), "configuration.yml");
    }

    public void init() {
        if(!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdir();
        }

        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                this.plugin.getLogger().info(ChatColor.RED + "" + ChatColor.BOLD + "[ERROR]" + ChatColor.WHITE + "Unable to c");
            }
        }
    }
}
