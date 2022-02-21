package dev.kutuptilkisi.miniparkour.listener;

import dev.kutuptilkisi.miniparkour.MiniParkour;
import dev.kutuptilkisi.miniparkour.instance.Game;
import dev.kutuptilkisi.miniparkour.manager.ParkourManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ParkourListener implements Listener {

    private MiniParkour mp;

    public ParkourListener(MiniParkour mp){
        this.mp = mp;
    }

    @EventHandler
    public void onMovement(PlayerMoveEvent e){
        Game game = mp.getManager().getGame(e.getPlayer());
        Block above = e.getTo().getBlock().getRelative(0, -1, 0);
        if(game != null && !above.getType().equals(Material.AIR)){
            if(game.isValid(above)){
                if(above.equals(game.getLastBlock())){
                    game.addPoint();
                }
            } else {
                game.lostGame(false);
                mp.getManager().games.remove(e.getPlayer().getUniqueId());
            }
        }
    }

}
