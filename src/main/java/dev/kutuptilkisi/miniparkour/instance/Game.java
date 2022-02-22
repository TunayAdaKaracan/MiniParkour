package dev.kutuptilkisi.miniparkour.instance;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private static final Material COMPLETE = Material.TERRACOTTA;
    private static final Material GOAL = Material.EMERALD_BLOCK;


    private final UUID player;
    private int point;
    private final Location spawn;

    private final Block startBlock;

    private final LinkedList<Block> blocks;

    public Game(Player p){

        this.player = p.getUniqueId();
        this.point = 0;
        this.spawn = p.getLocation();

        this.blocks = new LinkedList<>();

        Block start = p.getLocation().getBlock().getRelative(0, -1, 0);
        this.startBlock = start;

        this.blocks.add(start);

        createBlock();
    }
    /*
        GETTERS
     */

    public Block getLastBlock(){return blocks.getLast();}

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
            //err
        } else {
            Block randomBlock = empty.get(ThreadLocalRandom.current().nextInt(empty.size())).getBlock();
            randomBlock.setType(GOAL);
            blocks.add(randomBlock);
        }
    }

    /*
        GAME
     */

    public void addPoint(){
        if(!blocks.getFirst().equals(startBlock)) {
            blocks.getFirst().setType(Material.AIR);
        }
        blocks.removeFirst();

        getLastBlock().setType(COMPLETE);

        createBlock();
        //noinspection ConstantConditions
        Bukkit.getPlayer(player).sendMessage(ChatColor.GREEN + "+1 Point");
        point++;
    }

    public void lostGame(boolean force){
        if(!force) {
            Player p = Bukkit.getPlayer(player);

            //noinspection ConstantConditions
            p.teleport(spawn);
            p.sendMessage(ChatColor.GOLD + "Total Points: " + point);
        }
        for (Block block : blocks) {
            if(!block.equals(startBlock)) {
                block.setType(Material.AIR);
            }
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
