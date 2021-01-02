package lol.haha;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerJoinEvent;

import lol.haha.MyListener;


public final class Haha extends JavaPlugin {

    private static Haha instance;

    public static Haha getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        // Plugin startup logic
        System.out.println("PLUGIN_INIT");
        getServer().broadcastMessage("plugin work haha yay");
        getServer().getPluginManager().registerEvents(new MyListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
