package com.bisecthosting.mcordlink.discord;

import com.bisecthosting.mcordlink.MCordLink;
import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.*;

import java.util.logging.Logger;

public class DiscordLauncher {

    private JDA jda;
    public void init() {
        this.beginBot();
    }

    public JDA getJDA() {
        return this.jda;
    }

    private void beginBot() {
        jda = JDABuilder.createDefault
                ("MTAxOTM1NTEzNTY5NDA5MDI3Mg.GPT939.0g6v_7IwnFbkBNErdGSLShyfW4Ql5ZESKSnObM").build();
        System.out.println("Logged into Discord Bot");

    }
}
