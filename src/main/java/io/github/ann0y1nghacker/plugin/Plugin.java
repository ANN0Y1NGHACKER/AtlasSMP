package io.github.ann0y1nghacker.plugin;

import com.google.gson.*;
import io.github.ann0y1nghacker.plugin.commands.*;
import io.github.ann0y1nghacker.plugin.events.OnPlayerChat;
import io.github.ann0y1nghacker.plugin.events.OnPlayerJoin;
import io.github.ann0y1nghacker.plugin.events.OnPlayerLeave;
import io.github.ann0y1nghacker.plugin.modules.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Objects;

public final class Plugin extends JavaPlugin {

    public TabManager tab;

    @Override
    public void onEnable() {
        // Plugin startup logic
        NPC npc = new NPC(this);

//        new Discord(this);

        new recipes(this);

        File tmpDir = new File("./AtlasPluginData.json");
        boolean exists = tmpDir.exists();

        if (!exists) {
            try (FileWriter file = new FileWriter("./AtlasPluginData.json")) {

                file.write("{}");
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File tmpDir2 = new File("./afkCordsData.json");
        boolean exists2 = tmpDir2.exists();

        if (!exists2) {
            try (FileWriter file = new FileWriter("./afkCordsData.json")) {

                file.write("{}");
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.tab = new TabManager(this);

        tab.addHeader("&6&eW&6elcome to ATLAS SMP");
        tab.addHeader("&6W&ee&6lcome to ATLAS SMP");
        tab.addHeader("&6We&el&6come to ATLAS SMP");
        tab.addHeader("&6Wel&ec&6ome to ATLAS SMP");
        tab.addHeader("&6Welc&eo&6me to ATLAS SMP");
        tab.addHeader("&6Welco&em&6e to ATLAS SMP");
        tab.addHeader("&6Welcom&ee&6 to ATLAS SMP");
        tab.addHeader("&6Welcome&e &6to ATLAS SMP");
        tab.addHeader("&6Welcome &et&6o ATLAS SMP");
        tab.addHeader("&6Welcome t&eo&6 ATLAS SMP");
        tab.addHeader("&6Welcome to&e &6ATLAS SMP");
        tab.addHeader("&6Welcome to &eA&6TLAS SMP");
        tab.addHeader("&6Welcome to A&eT&6LAS SMP");
        tab.addHeader("&6Welcome to AT&eL&6AS SMP");
        tab.addHeader("&6Welcome to ATL&eA&6S SMP");
        tab.addHeader("&6Welcome to ATLA&eS&6 SMP");
        tab.addHeader("&6Welcome to ATLAS&e &6SMP");
        tab.addHeader("&6Welcome to ATLAS &eS&6MP");
        tab.addHeader("&6Welcome to ATLAS S&eM&6P");
        tab.addHeader("&6Welcome to ATLAS SM&eP&6");
        tab.addHeader("&6Welcome to ATLAS SMP&e&6");
        tab.addHeader("&6Welcome to ATLAS SMP&e&6");
        tab.addHeader("&6Welcome to ATLAS SMP&e&6");
        tab.addHeader("&6Welcome to ATLAS SMP&e&6");
        tab.addHeader("&6Welcome to ATLAS SMP&e&6");
        tab.addHeader("&6Welcome to ATLAS SMP&e&6");
        tab.addHeader("&6Welcome to ATLAS SMP&e&6");
        tab.addHeader("&6Welcome to ATLAS SMP&e&6");
        tab.addHeader("&6Welcome to ATLAS SMP&e&6");

//        npc.createBlankNPC(51 - Bukkit.getOnlinePlayers().size());

        tab.showTab();

        System.out.println(" \n" +
                "/* ======================================================= */\n" +
                "/*                                                         */\n" +
                "/*       © Copyright 2020 - ANN0Y1NGHACKER's Plugins       */\n" +
                "/*                                                         */\n" +
                "/* ======================================================= */\n"
        );

        new afkManager(this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                ChatColor wColor = ChatColor.GREEN;

                if (player.getWorld().getName().endsWith("nether")) wColor = ChatColor.RED;
                if (player.getWorld().getName().endsWith("the_end")) wColor = ChatColor.YELLOW;

                player.setPlayerListName(wColor + "■" + ChatColor.WHITE + player.getPlayerListName()
                        .replace(ChatColor.GREEN + "■", "")
                        .replace(ChatColor.RED + "■", "")
                        .replace(ChatColor.YELLOW + "■", "")
                );
            }
        }, 10, 20);



        Objects.requireNonNull(getCommand("freecam")).setExecutor(new freecam(npc, this));
        Objects.requireNonNull(getCommand("freecam-ws")).setExecutor(new freecamWs());
        Objects.requireNonNull(getCommand("plrData")).setExecutor(new plrData());
        Objects.requireNonNull(getCommand("plrData")).setTabCompleter(new plrDataTab());

//        Objects.requireNonNull(getCommand("test")).setExecutor(new test());
//        getCommand("freecam-logs").setExecutor(new freecamLogs());

        getServer().getPluginManager().registerEvents(new OnPlayerJoin(npc, this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerLeave(npc), this);
        getServer().getPluginManager().registerEvents(new OnPlayerChat(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
