package dev.kutuptilkisi.miniparkour.command;

import dev.kutuptilkisi.miniparkour.MiniParkour;
import dev.kutuptilkisi.miniparkour.instance.Game;
import dev.kutuptilkisi.miniparkour.manager.ParkourManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ParkourCommand implements CommandExecutor {

    private MiniParkour mp;

    public ParkourCommand(MiniParkour mp){
        this.mp = mp;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!mp.getManager().games.containsKey(p.getUniqueId())) {
                mp.getManager().games.put(p.getUniqueId(), new Game(p));
            } else {
                p.sendMessage(ChatColor.RED + "You are already playing!");
            }
        } else {
            sender.sendMessage("You cant play this game!");
        }

        return true;
    }
}
