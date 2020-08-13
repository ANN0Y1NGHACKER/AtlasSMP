package io.github.ann0y1nghacker.plugin.events;

import io.github.ann0y1nghacker.plugin.modules.NPC;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerLeave implements Listener {

    private final NPC npc;
    public OnPlayerLeave(NPC npc) {
        this.npc = npc;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        e.setQuitMessage(ChatColor.RED + "◄ " + ChatColor.WHITE + e.getPlayer().getDisplayName() + ChatColor.YELLOW + " left the server");

//        npc.createBlankNPC(1);
    }
}