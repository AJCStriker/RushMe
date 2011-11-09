/*
 *
 * This code was contributed to RushMe by tips48, from the CounterCraftDev team. Original file can be found at https://github.com/AJCStriker/Counter-Craft/edit/master/src/net/countercraft/ccserver/maths/MathsHelper.java
 *
 */
package com.tips48.rushMe.util;


import com.tips48.rushMe.RushMe;
import java.util.ArrayList;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 *
 * @author alexanderchristie
 */
public class MathsHelper {
    
   /**
     * This function can be called and will return a list of any players that
     * have been spotted by the player casting. I
     * 
     * It will return a empty ArrayList<Player>
     * if none are found. 
     * 
     * @param Player who has pressed the spot button as org.bukkit.entity.Player.
     * @return ArrayList<Player> of spotted players.
     */
   public static ArrayList<Player> rayCast(Player spotter){
        ArrayList<Player> spottedList = new ArrayList();
        
        for(Player player : RushMe.getInstance().getServer().getOnlinePlayers()){
            
            if(!player.equals(spotter)){
            
            double x = spotter.getLocation().toVector().distance(player.getLocation().toVector());
            
            Vector direction = spotter.getLocation().getDirection().multiply(x);
            
            Vector answer = direction.add(spotter.getLocation().toVector());
            
            
            
            
            
            
            
            if(((CraftLivingEntity)player).getHandle().f(((CraftEntity)spotter).getHandle())){
                
            
           
                if(answer.distance(player.getLocation().toVector()) < 1.37){
                        
                        RushMe.getInstance().getServer().broadcastMessage(spotter.getDisplayName() + " spotted " + player.getDisplayName());
                        spottedList.add(player);
                        
                    }
                    
                } 
                
            }
            
        
        }
        return spottedList;
    }
        
}
