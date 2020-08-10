package io.github.ann0y1nghacker.plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class test implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            String playerUUID = player.getUniqueId().toString();


        }

        else {
            System.out.println("You need to be a player to execute this command.");
        }

        return false;
    }
}
