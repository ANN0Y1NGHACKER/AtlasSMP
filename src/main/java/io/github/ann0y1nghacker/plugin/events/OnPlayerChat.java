package io.github.ann0y1nghacker.plugin.events;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;


public class OnPlayerChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        e.setFormat(e.getPlayer().getDisplayName() + ChatColor.WHITE + " » " + e.getMessage());
    }
}
