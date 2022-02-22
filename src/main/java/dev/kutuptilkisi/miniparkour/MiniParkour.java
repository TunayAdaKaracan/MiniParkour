package dev.kutuptilkisi.miniparkour;

import dev.kutuptilkisi.miniparkour.command.ParkourCommand;
import dev.kutuptilkisi.miniparkour.listener.ParkourListener;
import dev.kutuptilkisi.miniparkour.manager.ParkourManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MiniParkour extends JavaPlugin {

    private ParkourManager manager;

    @Override
    public void onEnable() {
        manager = new ParkourManager();
        Bukkit.getPluginManager().registerEvents(new ParkourListener(this),this);
        //noinspection ConstantConditions
        getCommand("park").setExecutor(new ParkourCommand(this));
    }

    public ParkourManager getManager(){return manager;}
}
