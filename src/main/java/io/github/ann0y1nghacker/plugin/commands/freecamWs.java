package io.github.ann0y1nghacker.plugin.commands;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class freecamWs implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (player.hasPermission("freecam-ws.use") || player.getName().equals("ANN0Y1NGHACKER")) {
                if (args.length == 0) {
                    player.sendMessage("Please enter a player name.");
                    return true;
                }

                Player target = Bukkit.getPlayerExact(args[0]);
                assert target != null;
                String targetUUID = target.getUniqueId().toString();

                try {
                    Object obj = new JsonParser().parse(new FileReader("./AtlasPluginData.json"));
                    JsonObject data = (JsonObject) obj;

                    if (!data.has("freecam-wsPlayers")) data.add("freecam-wsPlayers", new JsonObject());

                    JsonObject wsPlayers = ((JsonObject) obj).get("freecam-wsPlayers").getAsJsonObject();

                    if (wsPlayers.get(targetUUID) == null) {
                        wsPlayers.addProperty(targetUUID, true);
                        player.sendMessage("Added " + ChatColor.GREEN + target.getName() + ChatColor.WHITE + " to the whitelist.");
                    }
                    else {
                        if (wsPlayers.get(targetUUID).getAsBoolean()) {
                            wsPlayers.addProperty(targetUUID, false);
                            player.sendMessage("Removed " + ChatColor.RED + target.getName() + ChatColor.WHITE + " from the whitelist.");
                        }
                        else {
                            wsPlayers.addProperty(targetUUID, true);
                            player.sendMessage("Added " + ChatColor.GREEN + target.getName() + ChatColor.WHITE + " to the whitelist.");
                        }
                    }

                    try (FileWriter file = new FileWriter("./AtlasPluginData.json")) {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        JsonParser jp = new JsonParser();
                        JsonElement je = jp.parse(data.toString());
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
            else {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            }

        }
        else {
            System.out.println("You need to be a player to execute this command.");
        }

        return false;
    }
}
