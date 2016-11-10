package me.timlampen.leaderboard;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Primary on 10/28/2016.
 */
public class CommandHandler implements CommandExecutor {
    GUICreator c;
    public CommandHandler(GUICreator c){
        this.c = c;
    }
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player player = (Player)sender;
        if(args.length>0){
            int page = 1;
            try{
                page = Integer.parseInt(args[0]);
            }catch (NumberFormatException nfe){
                nfe.printStackTrace();
                sender.sendMessage(ChatColor.RED + "Error: Cannot parse your page integer");
                return false;
            }
            if(!(page>0)){
                sender.sendMessage(ChatColor.RED + "Error: Your page integer has to be positive");
                return false;
            }
            c.show(player, page);
        }
        c.show(player, 1);
        return false;
    }
}
