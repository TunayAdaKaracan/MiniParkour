package dev.kutuptilkisi.miniparkour.manager;

import dev.kutuptilkisi.miniparkour.instance.Game;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ParkourManager {

    public final HashMap<UUID, Game> games;

    public ParkourManager(){
        this.games = new HashMap<>();
    }

    public Game getGame(Player p){
        return games.get(p.getUniqueId());
    }

}
