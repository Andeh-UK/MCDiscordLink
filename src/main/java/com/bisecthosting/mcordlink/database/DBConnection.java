package com.bisecthosting.mcordlink.database;


import com.bisecthosting.mcordlink.MCordLink;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;

public class DBConnection {

    private Logger logger = null;
    private String uri = null;
    private MCordLink plugin = null;

    public void init(Logger logger, String uri, MCordLink plugin) {
        this.logger = logger;
        this.uri = uri;
        this.plugin = plugin;
    }

    public Connection createConnection() {
        Connection connection = null;

        if (this.uri != null) {
            try {
                if (!(this.uri.equals(""))) {
                    this.logger.log(Level.INFO, this.uri);
                    connection = DriverManager.getConnection(this.uri);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public void createTables() {
        Connection connection = this.plugin.connection;
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                String query = "CREATE TABLE IF NOT EXISTS players(" +
                        "code VARCHAR(5) NOT NULL PRIMARY KEY," +
                        "minecraft_name VARCHAR(64) NOT NULL UNIQUE, " +
                        "discord_id VARCHAR(20) UNIQUE)";
                statement.execute(query);
                statement.close();
                connection.close();
                this.logger.info("Prepared Database.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addPlayer(String code, String minecraft_name) {
        Connection connection = this.plugin.connection;
        if (connection != null) {
            try {
                String query = "INSERT INTO players (code, minecraft_name) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, code);
                statement.setString(2, minecraft_name);
                statement.execute();
                this.logger.log(Level.INFO, "Added " + minecraft_name + "to database with code: " + code);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, String> getPlayer(String minecraft_name) {
        Connection connection = this.plugin.connection;
        String code = null;
        String discord_id = null;
        if (connection != null) {
            try {
                String query = "SELECT * FROM players WHERE minecraft_name=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, minecraft_name);
                ResultSet results = statement.executeQuery();

                if(results.next()) {
                    code = results.getString("code");
                    discord_id = results.getString("discord_id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Map<String, String> player_details = new HashMap<String, String>();
        player_details.put("code", code);
        player_details.put("discord_id", discord_id);
        return player_details;
    }

    public Map<String, String> getPlayerByCode(String code) {
        Connection connection = this.plugin.connection;
        String minecraft_name = null;
        if (connection != null) {
            try {
                String query = "SELECT * FROM players WHERE code=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, code);
                ResultSet results = statement.executeQuery();

                if(results.next()) {
                    minecraft_name = results.getString("minecraft_name");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Map<String, String> player_details = new HashMap<String, String>();
        player_details.put("code", code);
        player_details.put("minecraft_name", minecraft_name);
        return player_details;
    }

    public void attachDiscord(String code, String user_id) {
        Connection connection = this.plugin.connection;
        if (connection != null) {
            try {
                String query = "UPDATE players SET discord_id=? WHERE code=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, user_id);
                statement.setString(2, code);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateCode(String code, String minecraft_name) {
        Connection connection = this.plugin.connection;
        if (connection != null) {
            try {
                String query = "UPDATE players SET code=? WHERE minecraft_name=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, code);
                statement.setString(2, minecraft_name);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removePlayer(String minecraft_name) {
        Connection connection = this.plugin.connection;
        if (connection != null) {
            try {
                String query = "DELETE FROM players WHERE minecraft_name=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, minecraft_name);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearDatabase() {
        Connection connection = this.plugin.connection;
        if (connection != null) {
            try {
                String query = "DELETE FROM players";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
