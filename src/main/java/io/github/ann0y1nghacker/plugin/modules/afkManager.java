package io.github.ann0y1nghacker.plugin.modules;

import com.google.gson.*;
import io.github.ann0y1nghacker.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class afkManager {
    Plugin plugin;

    public afkManager(Plugin plugin) {
        this.plugin = plugin;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                String uuid = player.getUniqueId().toString();
                int afkMarker = 5;

                try {
                    Object obj = new JsonParser().parse(new FileReader("./afkCordsData.json"));
                    JsonObject afkCords = (JsonObject) obj;

                    Object obj2 = new JsonParser().parse(new FileReader("./AtlasPluginData.json"));
                    JsonObject data = (JsonObject) obj2;

                    JsonObject plrData = data.get("plrData").getAsJsonObject();

                    if (!afkCords.has(uuid)) afkCords.add(uuid, new JsonObject());
                    JsonObject plrInfo = afkCords.get(uuid).getAsJsonObject();

                    if (!plrInfo.has("afkMeter")) plrInfo.addProperty("afkMeter", 0);
                    if (!plrInfo.has("x")) plrInfo.addProperty("x", player.getLocation().getX());
                    if (!plrInfo.has("y")) plrInfo.addProperty("y", player.getLocation().getY());
                    if (!plrInfo.has("z")) plrInfo.addProperty("z", player.getLocation().getZ());

                    if (
                            plrInfo.get("x").getAsDouble() == player.getLocation().getX() &&
                            plrInfo.get("y").getAsDouble() == player.getLocation().getY() &&
                            plrInfo.get("z").getAsDouble() == player.getLocation().getZ()
                    ) {
                        plrInfo.addProperty("afkMeter", plrInfo.get("afkMeter").getAsInt() + 1);
                    } else plrInfo.addProperty("afkMeter", 0);

                    plrInfo.addProperty("afk", plrInfo.get("afkMeter").getAsInt() >= afkMarker);

                    if (plrInfo.get("afk").getAsBoolean()) {
                        ChatColor color = ChatColor.DARK_GRAY;
                        String tag = "AFK";
                        player.setPlayerListName(" [" + color + tag + ChatColor.WHITE + "] " + color + player.getName() + " ");
                        player.setDisplayName("[" + color + tag + ChatColor.WHITE + "] " + color + player.getName());
                    } else {
                        ChatColor color = ChatColor.valueOf(plrData.get(uuid).getAsJsonObject().get("color").getAsString());
                        String tag = plrData.get(uuid).getAsJsonObject().get("tag").getAsString();
                        player.setPlayerListName(" [" + color + tag + ChatColor.WHITE + "] " + color + player.getName() + " ");
                        player.setDisplayName("[" + color + tag + ChatColor.WHITE + "] " + color + player.getName());
                    }

                    plrInfo.addProperty("x", player.getLocation().getX());
                    plrInfo.addProperty("y", player.getLocation().getY());
                    plrInfo.addProperty("z", player.getLocation().getZ());

                    try (FileWriter file = new FileWriter("./afkCordsData.json")) {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        JsonParser jp = new JsonParser();
                        JsonElement je = jp.parse(afkCords.toString());
                        String out = gson.toJson(je);
                        file.write(out);
                        file.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }, 10, 200);
    }
}
