package io.github.ann0y1nghacker.plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class freecamLogs implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (player.hasPermission("own")) {
                System.out.println("YES");
            }
            else System.out.println("NO");


//            if (player.hasPermission("a")) {
//                if (player.getGameMode().compareTo(GameMode.CREATIVE) == 0) return false;
//
//                if (player.getGameMode().compareTo(GameMode.SURVIVAL) == 0) player.setGameMode(GameMode.SPECTATOR);
//                else player.setGameMode(GameMode.SURVIVAL);
//            }
//            else {
//                player.sendMessage("Only ANN0Y1NGHACKER can use this command.");
//            }

        }
        else {
            System.out.println("You need to be a player to execute this command.");
        }

        return false;
    }
}
