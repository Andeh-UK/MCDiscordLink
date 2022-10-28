package com.bisecthosting.mcordlink.requests;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import com.google.common.base.Splitter;

public class HTTP {

    private HttpURLConnection makeConnection(String url_string, String method) throws IOException {
        URL url = new URL(url_string);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("Authorization", "Token 01bc83b68d7a4445b2088e1543d5f4df32523e67");
        return con;
    }

    private String getResponse(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }

    private Map<String, String> toMapping(String stringMap) {
        return Splitter.on(", ").withKeyValueSeparator(":").split(stringMap);
    }

    private String handleParams(HttpURLConnection con, Map<String, String> params) throws IOException {
        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(params));
        out.flush();
        out.close();
        return getResponse(con);
    }

    public String getPlayers() throws IOException {
        HttpURLConnection con = makeConnection("http://mcdiscordlink.bisecthosting.com/players" ,"GET");
        return getResponse(con);
    }

    public Map<String, String> getPlayerByName(String minecraft_name) throws IOException {
        HttpURLConnection con = makeConnection(
                "http://mcdiscordlink.bisecthosting.com/players?minecraft_name=" + minecraft_name,
                "GET"
        );
        String response = getResponse(con);
        if (response.equals("[]")) {
            return null;
        }
        return toMapping(response);
    }

    public Map<String, String> getPlayerByCode(String code) throws IOException {
        HttpURLConnection con = makeConnection(
                "http://mcdiscordlink.bisecthosting.com/players?code=" + code,
                "GET"
        );
        String response = getResponse(con);
        if (response.equals("[]")) {
            return null;
        }
        return toMapping(response);
    }

    public String addPlayer(String code, String minecraft_name) throws IOException {
        HttpURLConnection con = makeConnection(
                "http://mcdiscordlink.bisecthosting.com/players",
                "POST"
        );
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("minecraft_name", minecraft_name);
        return handleParams(con, params);
    }

    public String updateCode(String minecraft_name, String new_code) throws IOException {
        Map<String, String> player = getPlayerByName(minecraft_name);
        HttpURLConnection con = makeConnection(
                "http://mcdiscordlink.bisecthosting.com/players/" + player.get("id"),
                "PUT"
        );
        Map<String, String> params = new HashMap<>();
        params.put("id", player.get("id"));
        params.put("code", new_code);
        params.put("minecraft_name", player.get("minecraft_name"));
        params.put("discord_id", player.get("discord_id"));
        return handleParams(con, params);
    }

    public String attachDiscord(String minecraft_name, String discord_id) throws IOException {
        Map<String, String> player = getPlayerByName(minecraft_name);
        HttpURLConnection con = makeConnection(
                "http://mcdiscordlink.bisecthosting.com/players/" + player.get("id"),
                "PUT"
        );
        Map<String, String> params = new HashMap<>();
        params.put("id", player.get("id"));
        params.put("code", player.get("code"));
        params.put("minecraft_name", player.get("minecraft_name"));
        params.put("discord_id", discord_id);
        return handleParams(con, params);
    }

    public String removePlayer(String minecraft_name) throws IOException {
        Map<String, String> player = getPlayerByName(minecraft_name);
        HttpURLConnection con = makeConnection(
                "http://mcdiscordlink.bisecthosting.com/players/" + player.get("id"),
                "DELETE"
        );
        return getResponse(con);
    }

    public String removeAll() throws IOException {
        HttpURLConnection con = makeConnection(
                "http://mcdiscordlink.bisecthosting.com/players",
                "DELETE"
        );
        return getResponse(con);
    }


}
