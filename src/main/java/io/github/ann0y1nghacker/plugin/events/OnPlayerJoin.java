package io.github.ann0y1nghacker.plugin.events;

import com.google.gson.*;
import io.github.ann0y1nghacker.plugin.NPC;

import io.github.ann0y1nghacker.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class OnPlayerJoin implements Listener {

    private final Plugin plugin;
    private final NPC npc;

    public OnPlayerJoin(NPC npc, Plugin plugin) {
        this.npc = npc;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        try {
            String uuid = e.getPlayer().getUniqueId().toString();
            Object obj = new JsonParser().parse(new FileReader("./AtlasPluginData.json"));
            JsonObject data = (JsonObject) obj;

//            e.getPlayer().setResourcePack("https://www.dropbox.com/s/fbwuz233rempnkt/shadow.zip?dl=1");
//            e.getPlayer().getWorld().setMonsterSpawnLimit(e.getPlayer().getWorld().getMonsterSpawnLimit() + 20);

            if (!data.has("plrData")) data.add("plrData", new JsonObject());
            JsonObject plrData = ((JsonObject) obj).get("plrData").getAsJsonObject();

            if (!plrData.has(uuid)) {
                ChatColor color = ChatColor.GREEN;
                String tag = "MEMBER";
                e.getPlayer().setPlayerListName(" [" + color + tag + ChatColor.WHITE + "] " + color + e.getPlayer().getName() + " ");
                e.getPlayer().setDisplayName("[" + color + tag + ChatColor.WHITE + "] " + color + e.getPlayer().getName());
            }
            else {
                if (!plrData.get(uuid).getAsJsonObject().has("tag") && !plrData.get(uuid).getAsJsonObject().has("color")) {
                    e.getPlayer().setPlayerListName(" " + e.getPlayer().getName() + " ");
                    e.getPlayer().setDisplayName(ChatColor.WHITE + e.getPlayer().getName());
                }
                else if (!plrData.get(uuid).getAsJsonObject().has("tag") && plrData.get(uuid).getAsJsonObject().has("color")) {
                    e.getPlayer().setPlayerListName(" " + ChatColor.valueOf(plrData.get(uuid).getAsJsonObject().get("color").getAsString()) + e.getPlayer().getName() + " ");
                    e.getPlayer().setDisplayName(ChatColor.valueOf(plrData.get(uuid).getAsJsonObject().get("color").getAsString()) + e.getPlayer().getName());
                }
                else if (plrData.get(uuid).getAsJsonObject().has("tag") && !plrData.get(uuid).getAsJsonObject().has("color")) {
                    e.getPlayer().setPlayerListName(" [" + plrData.get(uuid).getAsJsonObject().get("tag").getAsString() + "] " + e.getPlayer().getName() + " ");
                    e.getPlayer().setDisplayName("[" + plrData.get(uuid).getAsJsonObject().get("tag").getAsString() + "] " + e.getPlayer().getName());
                }
                else if (plrData.get(uuid).getAsJsonObject().has("tag") && plrData.get(uuid).getAsJsonObject().has("color")) {
                    ChatColor color = ChatColor.valueOf(plrData.get(uuid).getAsJsonObject().get("color").getAsString());
                    String tag = plrData.get(uuid).getAsJsonObject().get("tag").getAsString();
                    e.getPlayer().setPlayerListName(" [" + color + tag + ChatColor.WHITE + "] " + color + e.getPlayer().getName() + " ");
                    e.getPlayer().setDisplayName("[" + color + tag + ChatColor.WHITE + "] " + color + e.getPlayer().getName());
                }
            }

            if (e.getPlayer().getName().equals("ANN0Y1NGHACKER")) {
                e.getPlayer().setPlayerListName(" [" + ChatColor.AQUA + "DEV" + ChatColor.WHITE + "] " + ChatColor.AQUA + e.getPlayer().getName() + " ");
                e.getPlayer().setDisplayName("[" + ChatColor.AQUA + "DEV" + ChatColor.WHITE + "] " + ChatColor.AQUA + e.getPlayer().getName());
            }

            e.setJoinMessage(ChatColor.GREEN + "► " + ChatColor.WHITE + e.getPlayer().getDisplayName() + ChatColor.YELLOW + " joined the server");

            try (FileWriter file = new FileWriter("./AtlasPluginData.json")) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonParser jp = new JsonParser();
                JsonElement je = jp.parse(data.toString());
                String out = gson.toJson(je);
                file.write(out);
                file.flush();
            } catch (IOException err) {
                err.printStackTrace();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

//        npc.removeBlankNPC();
//        npc.addJoinPacket(e.getPlayer(), true);

        if (npc.getNPCs() == null) return;
        if (npc.getNPCs().isEmpty()) return;

        npc.addJoinPacket(e.getPlayer(), false);
    }
}