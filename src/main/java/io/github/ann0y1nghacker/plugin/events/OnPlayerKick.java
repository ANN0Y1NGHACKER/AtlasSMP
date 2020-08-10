package io.github.ann0y1nghacker.plugin.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class OnPlayerKick implements Listener {

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e) {
        if (e.getPlayer().getName().equals("ANN0Y1NGHACKER")) {
            e.setLeaveMessage(ChatColor.YELLOW + "• " + ChatColor.AQUA + e.getPlayer().getName() + ChatColor.YELLOW + " was kicked from the server");
        }
    }
}