package io.github.ann0y1nghacker.plugin.modules;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;

import com.mojang.authlib.properties.Property;
import io.github.ann0y1nghacker.plugin.Plugin;
import net.minecraft.server.v1_16_R1.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.CraftServer;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class NPC {

    public NPC(Plugin plugin) {
        this.plugin = plugin;
    }

    private final Plugin plugin;
    private final List<EntityPlayer> NPC = new ArrayList<>();
    private final List<EntityPlayer> blankNPC = new ArrayList<>();

    public void createBlankNPC(int n) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Objects.requireNonNull(Bukkit.getWorld("world"))).getHandle();
        String[] name = getSkin("grey");

        for (int i = 0; i < n; i++) {
            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "");
            EntityPlayer npc = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));
            npc.setLocation(0, 0, 0, 0, 0);
            gameProfile.getProperties().put("textures", new Property("textures", name[0], name[1]));
            addNPCPacket(npc, false);
            blankNPC.add(npc);
        }
    }

    public void removeBlankNPC() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, blankNPC.get(0)));
            blankNPC.remove(0);
        }
    }

    public JsonObject createNPC(Player player, String skin) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Objects.requireNonNull(Bukkit.getWorld(player.getWorld().getName()))).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), skin);
        EntityPlayer npc = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));
        npc.setLocation(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());

        String[] name = getSkin(skin, player);
        gameProfile.getProperties().put("textures", new Property("textures", name[0], name[1]));

        addNPCPacket(npc, true);
        NPC.add(npc);

        JsonObject npcDetails = new JsonObject();
        npcDetails.addProperty("p", player.getLocation().getPitch());
        npcDetails.addProperty("yaw", player.getLocation().getYaw());
        npcDetails.addProperty("world", Objects.requireNonNull(player.getLocation().getWorld()).getName());
        npcDetails.addProperty("name", skin);
        npcDetails.addProperty("texture", name[0]);
        npcDetails.addProperty("signature", name[1]);
        npcDetails.addProperty("npcID", npc.getId());

        return npcDetails;
    }

    public JsonObject loadNPC(Location location, GameProfile profile, String uuid) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle();
        EntityPlayer npc = new EntityPlayer(server, world, profile, new PlayerInteractManager(world));
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        addNPCPacket(npc, true);
        NPC.add(npc);

        JsonObject npcDetails = new JsonObject();
        npcDetails.addProperty("npcID", npc.getId());

        return npcDetails;
    }

    private String[] getSkin(String name, Player player) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            String uuid = new JsonParser().parse(reader).getAsJsonObject().get("id").getAsString();

            URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader2 = new InputStreamReader(url2.openStream());
            JsonObject property = new JsonParser().parse(reader2).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = property.get("value").getAsString();
            String signature = property.get("signature").getAsString();
            return new String[] {texture, signature};

        } catch (Exception e) {
            EntityPlayer p = ((CraftPlayer) player).getHandle();
            GameProfile profile = p.getProfile();
            Property property = profile.getProperties().get("textures").iterator().next();
            String texture = property.getValue();
            String signature = property.getSignature();
            return new String[] {texture, signature};
        }
    }

    private String[] getSkin(String name) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            String uuid = new JsonParser().parse(reader).getAsJsonObject().get("id").getAsString();

            URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader2 = new InputStreamReader(url2.openStream());
            JsonObject property = new JsonParser().parse(reader2).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = property.get("value").getAsString();
            String signature = property.get("signature").getAsString();
            return new String[] {texture, signature};

        } catch (Exception e) {
            return new String[] {"", ""};
        }
    }

    public void addNPCPacket(EntityPlayer npc, boolean remove) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
            npc.getDataWatcher().set(DataWatcherRegistry.a.a(16), (byte)127);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), npc.getDataWatcher(), true));
            if (remove) {
                Bukkit.getScheduler().runTaskLater(plugin, task -> connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc)), 10);
            }
        }
    }

    public void removeNPC(int npcID) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutEntityDestroy(npcID));
            NPC.removeIf(npc -> npc.getId() == npcID);
        }
    }

    public void addJoinPacket(Player player, boolean blank) {
        if (blank) {
            for (EntityPlayer npc : blankNPC) {
                PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
                connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
                connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
                npc.getDataWatcher().set(DataWatcherRegistry.a.a(16), (byte)127);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), npc.getDataWatcher(), true));
            }
        }
        else {
            for (EntityPlayer npc : NPC) {
                PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
                connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
                connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
                npc.getDataWatcher().set(DataWatcherRegistry.a.a(16), (byte)127);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), npc.getDataWatcher(), true));
                Bukkit.getScheduler().runTaskLater(plugin, task -> connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc)), 10);
            }
        }
    }

    public List<EntityPlayer> getNPCs() {
        return NPC;
    }
    public List<EntityPlayer> getBlankNPCs() {
        return blankNPC;
    }
}
