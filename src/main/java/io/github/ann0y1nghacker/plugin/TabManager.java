package io.github.ann0y1nghacker.plugin;

import net.minecraft.server.v1_16_R1.ChatComponentText;
import net.minecraft.server.v1_16_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_16_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TabManager {

    private final List<String> headers = new ArrayList<>();
    private final List<String> footers = new ArrayList<>();

    private final Plugin plugin;
    public TabManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void showTab() {
        if (headers.isEmpty() && footers.isEmpty())
            return;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            final PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
            int count = 0;

            @Override
            public void run() {
                try {

                    if (Bukkit.getOnlinePlayers().size() != 0) {
                        if (count >= headers.size()) count = 0;
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
                            Field a = packet.getClass().getDeclaredField("header");
                            a.setAccessible(true);
                            a.set(packet, new ChatComponentText(" \n" + headers.get(count) + "\n "));

                            Field b = packet.getClass().getDeclaredField("footer");
                            b.setAccessible(true);
                            b.set(packet, new ChatComponentText(" \n" +
                                    "Players Online: " + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + "\n" +
                                    "Players in " + ChatColor.RED + "Nether" + ChatColor.WHITE + ": " + Bukkit.getOnlinePlayers().stream().filter(p -> p.getWorld().getName().endsWith("nether")).count() + "\n" +
                                    "Players in " + ChatColor.YELLOW + "The End" + ChatColor.WHITE + ": " + Bukkit.getOnlinePlayers().stream().filter(p -> p.getWorld().getName().endsWith("the_end")).count() + "\n"
//                                    + ChatColor.GREEN + " ■ - " + ChatColor.WHITE + "OVERWORLD | " +
//                                    ChatColor.RED + " ■ - " + ChatColor.WHITE + "NETHER | " +
//                                    ChatColor.YELLOW + " ■ - " + ChatColor.WHITE + "THE END "
//                                    + "                                                                                                                                                  "
                            ));

                            connection.sendPacket(packet);
                        }
                    }

                    count++;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 10, 1);
    }

    public void addHeader(String header) {
        headers.add(format(header));
    }

    public void addFooter(String footer) {
        footers.add(format(footer));
    }

    private String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
