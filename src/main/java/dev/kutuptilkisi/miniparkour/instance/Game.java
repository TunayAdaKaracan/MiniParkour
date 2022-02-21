package dev.kutuptilkisi.miniparkour.instance;

import dev.kutuptilkisi.miniparkour.MiniParkour;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.*;

public class Game {

    private static final Material complete = Material.TERRACOTTA;
    private static final Material goal = Material.EMERALD_BLOCK;


    private UUID player;
    private int point;
    private Location spawn;

    private List<Block> blocks;

    public Game(Player p){

        this.player = p.getUniqueId();
        this.point = 0;
        this.spawn = p.getLocation();

        this.blocks = new ArrayList<>();

        Block start = p.getLocation().getBlock().getRelative(0, -1, 0);
        start.setType(complete);
        this.blocks.add(start);

        createBlock();
    }
    /*
        GETTERS
     */

    public UUID getPlayer(){return player;}
    public Block getLastBlock(){return blocks.get(blocks.size()-1);}

    /*
        CORE
     */

    private void createBlock(){
        Location last = getLastBlock().getLocation().add(0.5, 0, 0.5);

        List<Location> empty = new ArrayList<>();
        for(int y=-1; y<2; y++){
            for(int d=0; d<360; d=d+10){
                double radian = Math.toRadians(d);
                Vector vector = new Vector(Math.cos(radian) * 3, y, Math.sin(radian) * 3);
                Location l = last.clone().add(vector);
                if(l.getBlock().getType().equals(Material.AIR)){
                    empty.add(l);
                }
            }
        }

        if(empty.size() == 0){
            // err message
        } else {
            Block randomBlock = empty.get(new Random().nextInt(empty.size())).getBlock();
            randomBlock.setType(goal);
            blocks.add(randomBlock);
        }


        /*
        Random random = new Random();

        List<Integer> vertical = Arrays.asList(4, 3, 2, -2, -3, -4);
        List<Integer> horizontal = Arrays.asList(-1, 0, 1);

        Block block = getLastBlock().getRelative(vertical.get(random.nextInt(vertical.size())),
                                                 horizontal.get(random.nextInt(horizontal.size())),
                                                 vertical.get(random.nextInt(vertical.size())));
        block.setType(goal);
        blocks.add(block); */
    }

    /*
        GAME
     */

    public void addPoint(){
        blocks.get(0).setType(Material.AIR);
        blocks.remove(0);

        getLastBlock().setType(complete);

        createBlock();

        Bukkit.getPlayer(player).sendMessage(ChatColor.GREEN + "+1 Point");
        point++;
    }

    public void lostGame(boolean force){
        if(!force) {
            Player p = Bukkit.getPlayer(player);
            p.teleport(spawn);
            p.sendMessage(ChatColor.GOLD + "Total Points: " + point);
        }
        for (Block block : blocks) {
            block.setType(Material.AIR);
        }
    }

    /*
        CHECKS
     */

    public boolean isValid(Block block){
        return isValid(block.getLocation());
    }

    public boolean isValid(Location l){
        for(Block block : blocks){
            if(block.getLocation().equals(l)){
                return true;
            }
        }
        return false;
    }
}
