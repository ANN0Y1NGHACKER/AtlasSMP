package io.github.ann0y1nghacker.plugin.events;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatToDiscord implements Listener {

    JDA bot;
    TextChannel textChannel;

    public ChatToDiscord(JDA bot) {
        this.bot = bot;
//        textChannel = bot.getTextChannelById("739592862253056030");
        this.textChannel = bot.getTextChannelById("739592862253056030");
        this.bot.addEventListener(this);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        System.out.println(textChannel.getName());
//        https://discordapp.com/api/webhooks/739905504444416140/IuIO3IU6ZlIyRUGoMpsoFne5l10YY_my8v9_YDUWbNzJHrVRzEs7XeEyPzk4mGC8FjTu
        textChannel.sendMessage(e.getMessage()).complete();
    }
}
