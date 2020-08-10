package io.github.ann0y1nghacker.plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class plrDataTab implements TabCompleter {

    List<String> args1 = new ArrayList<>();
    List<String> args2 = new ArrayList<>();
    List<String> args3 = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String lable, String[] args) {

        if (args1.isEmpty()) {
            args1.add("set"); args1.add("remove");
        }

        if (args2.isEmpty()) {
            args2.add("tag"); args2.add("color");
        }

        if (args3.isEmpty()) {
            args3.add("BLACK"); args3.add("DARK_BLUE");
            args3.add("DARK_GREEN"); args3.add("DARK_AQUA");
            args3.add("DARK_RED"); args3.add("DARK_PURPLE");
            args3.add("GOLD"); args3.add("GRAY");
            args3.add("DARK_GRAY"); args3.add("BLUE");
            args3.add("GREEN"); args3.add("AQUA");
            args3.add("RED"); args3.add("LIGHT_PURPLE");
            args3.add("YELLOW"); args3.add("WHITE");
        }

        List<String> result = new ArrayList<>();
        if (args.length == 2) {
            for (String a : args1) {
                if (a.toLowerCase().startsWith(args[1].toLowerCase()))
                    result.add(a);
            }
            return result;
        }

        if (args.length == 3) {
            for (String a : args2) {
                if (a.toLowerCase().startsWith(args[2].toLowerCase()))
                    result.add(a);
            }
            return result;
        }

        if (args.length == 4 && args[1].equals("set") && args[2].equals("color")) {
            for (String a : args3) {
                if (a.toLowerCase().startsWith(args[3].toLowerCase()))
                    result.add(a);
            }
            return result;
        }

        return null;
    }
}
