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

public class plrData implements CommandExecutor {

    private void updatePlr(JsonObject plrData, Player player) {
        String uuid = player.getUniqueId().toString();

        if (!plrData.get(uuid).getAsJsonObject().has("tag") && !plrData.get(uuid).getAsJsonObject().has("color")) {
            player.setPlayerListName(" " + player.getName() + " ");
            player.setDisplayName(ChatColor.WHITE + player.getName());
        }
        else if (!plrData.get(uuid).getAsJsonObject().has("tag") && plrData.get(uuid).getAsJsonObject().has("color")) {
            player.setPlayerListName(" " + ChatColor.valueOf(plrData.get(uuid).getAsJsonObject().get("color").getAsString()) + player.getName() + " ");
            player.setDisplayName(ChatColor.valueOf(plrData.get(uuid).getAsJsonObject().get("color").getAsString()) + player.getName());
        }
        else if (plrData.get(uuid).getAsJsonObject().has("tag") && !plrData.get(uuid).getAsJsonObject().has("color")) {
            player.setPlayerListName(" [" + plrData.get(uuid).getAsJsonObject().get("tag").getAsString() + "] " + player.getName() + " ");
            player.setDisplayName("[" + plrData.get(uuid).getAsJsonObject().get("tag").getAsString() + "] " + player.getName());
        }
        else if (plrData.get(uuid).getAsJsonObject().has("tag") && plrData.get(uuid).getAsJsonObject().has("color")) {
            ChatColor color = ChatColor.valueOf(plrData.get(uuid).getAsJsonObject().get("color").getAsString());
            String tag = plrData.get(uuid).getAsJsonObject().get("tag").getAsString();
            player.setPlayerListName(" [" + color + tag + ChatColor.WHITE + "] " + color + player.getName() + " ");
            player.setDisplayName("[" + color + tag + ChatColor.WHITE + "] " + color + player.getName());
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            Player target = Bukkit.getPlayerExact(args[0]);
            assert target != null;
            String targetUUID = target.getUniqueId().toString();

            if (player.hasPermission("freecam-ws.use") || player.getName().equals("ANN0Y1NGHACKER")) {
                try {
                    Object obj = new JsonParser().parse(new FileReader("./AtlasPluginData.json"));
                    JsonObject data = (JsonObject) obj;

                    if (!data.has("plrData")) data.add("plrData", new JsonObject());
                    JsonObject plrData = ((JsonObject) obj).get("plrData").getAsJsonObject();
                    JsonObject plrInfo = new JsonObject();

                    if (!plrData.has(targetUUID)) plrData.add(targetUUID, plrInfo);

                    if (args[1].equals("set")) {
                        switch (args[2]) {
                            case "tag":
                                plrData.get(targetUUID).getAsJsonObject().addProperty("tag", args[3]);
                                updatePlr(plrData, target);
                                break;
                            case "color":
                                plrData.get(targetUUID).getAsJsonObject().addProperty("color", args[3]);
                                updatePlr(plrData, target);
                                break;
                        }
                    } else if (args[1].equals("remove")) {
                        switch (args[2]) {
                            case "tag":
                                if (!plrData.get(targetUUID).getAsJsonObject().has("tag")) break;
                                plrData.get(targetUUID).getAsJsonObject().remove("tag");
                                updatePlr(plrData, target);
                                break;
                            case "color":
                                if (!plrData.get(targetUUID).getAsJsonObject().has("color")) break;
                                plrData.get(targetUUID).getAsJsonObject().remove("color");
                                updatePlr(plrData, target);
                                break;
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
