package com.bisecthosting.mcordlink.database;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;

public class DBConnection {

    private Logger logger = null;
    private String uri = null;

    public void init(Logger logger, String uri) {
        System.out.println(uri);
        this.logger = logger;
        this.uri = uri;
    }

    public Connection createConnection() {
        Connection connection = null;
        if (this.uri != null) {
            try {
                if (!(this.uri.equals(""))) {
                    connection = DriverManager.getConnection(this.uri);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public void createTables() {
        Connection connection = this.createConnection();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                String query = "CREATE TABLE IF NOT EXISTS players(" +
                        "code VARCHAR(4) NOT NULL PRIMARY KEY," +
                        "minecraft_name VARCHAR(64) NOT NULL UNIQUE, " +
                        "discord_id VARCHAR(18) UNIQUE)";
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
        Connection connection = this.createConnection();
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
        Connection connection = this.createConnection();
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
        Connection connection = this.createConnection();
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
}