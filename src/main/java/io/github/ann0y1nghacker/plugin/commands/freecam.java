package io.github.ann0y1nghacker.plugin.commands;

import com.google.gson.*;
import io.github.ann0y1nghacker.plugin.NPC;
import io.github.ann0y1nghacker.plugin.Plugin;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class freecam implements CommandExecutor {

    private final NPC npc;
    private final Plugin plugin;

    public freecam(NPC npc, Plugin plugin) {
        this.npc = npc;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            String playerUUID = player.getUniqueId().toString();

            if (player.getGameMode().compareTo(GameMode.CREATIVE) == 0) return false;

            try {
                Object obj = new JsonParser().parse(new FileReader("./AtlasPluginData.json"));
                JsonObject data = (JsonObject) obj;

                if (!data.has("freecam-plrCords")) data.add("freecam-plrCords", new JsonObject());
                if (!data.has("freecam-wsPlayers")) data.add("freecam-wsPlayers", new JsonObject());

                JsonObject plrCords = ((JsonObject) obj).get("freecam-plrCords").getAsJsonObject();
                JsonObject wsPlayers = ((JsonObject) obj).get("freecam-wsPlayers").getAsJsonObject();

                if (!wsPlayers.has(playerUUID)) wsPlayers.addProperty(playerUUID, false);

                if (wsPlayers.get(playerUUID).getAsBoolean() || player.getName().equals("ANN0Y1NGHACKER")) {

                    if (player.getGameMode().compareTo(GameMode.SPECTATOR) == 0) {

                        if (plrCords.get(playerUUID) != null) {
                            double plrX = plrCords.get(playerUUID).getAsJsonObject().get("x").getAsDouble();
                            double plrY = plrCords.get(playerUUID).getAsJsonObject().get("y").getAsDouble();
                            double plrZ = plrCords.get(playerUUID).getAsJsonObject().get("z").getAsDouble();
                            float plrYaw = plrCords.get(playerUUID).getAsJsonObject().get("yaw").getAsFloat();
                            float plrPitch = plrCords.get(playerUUID).getAsJsonObject().get("p").getAsFloat();

                            if (args.length == 0) {
                                player.teleport(new Location(player.getWorld(), plrX, plrY, plrZ, plrYaw, plrPitch));
                                player.setGameMode(GameMode.SURVIVAL);
                            }
                            else if (args[0].equals("here") && player.getName().equals("ANN0Y1NGHACKER")) {
                                player.setGameMode(GameMode.SURVIVAL);
                            }

                            player.showPlayer(plugin, player);
                            npc.removeNPC(plrCords.get(playerUUID).getAsJsonObject().get("npcID").getAsInt());
                        }
                    }
                    else {
                        Double[] newLocation = { player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ() };

                        JsonObject npcDetails = npc.createNPC(player, player.getName());

                        JsonObject plrDetails = new JsonObject();

                        plrDetails.addProperty("name", npcDetails.get("name").getAsString());
                        plrDetails.addProperty("x", newLocation[0].toString());
                        plrDetails.addProperty("y", newLocation[1].toString());
                        plrDetails.addProperty("z", newLocation[2].toString());
                        plrDetails.addProperty("p", npcDetails.get("p").getAsString());
                        plrDetails.addProperty("yaw", npcDetails.get("yaw").getAsString());
                        plrDetails.addProperty("world", npcDetails.get("world").getAsString());
                        plrDetails.addProperty("texture", npcDetails.get("texture").getAsString());
                        plrDetails.addProperty("signature", npcDetails.get("signature").getAsString());
                        plrDetails.addProperty("npcID", npcDetails.get("npcID").getAsString());

                        plrCords.add(playerUUID, plrDetails);

                        try (FileWriter file = new FileWriter("./AtlasPluginData.json")) {
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            JsonParser jp = new JsonParser();
                            JsonElement je = jp.parse(data.toString());
                            String out = gson.toJson(je);
                            file.write(out);
                            file.flush();
                            player.sendMessage("Toggled FreeCam");
                            player.setGameMode(GameMode.SPECTATOR);
                            player.hidePlayer(plugin, player);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    player.sendMessage("You have to be a donator to use this command.");
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        else {
            System.out.println("You need to be a player to execute this command.");
        }

        return false;
    }
}
