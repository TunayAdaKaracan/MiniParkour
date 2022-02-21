package dev.kutuptilkisi.miniparkour.manager;

import dev.kutuptilkisi.miniparkour.instance.Game;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ParkourManager {

    public HashMap<UUID, Game> games;

    public ParkourManager(){
        this.games = new HashMap<>();
    }

    public Game getGame(Player p){

        for(Game game : games.values()){
            if(game.getPlayer().equals(p.getUniqueId())){
                return game;
            }
        }

        return null;
    }

}
