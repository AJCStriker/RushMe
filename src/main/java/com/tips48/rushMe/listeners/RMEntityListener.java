package com.tips48.rushMe.listeners;

import com.tips48.rushMe.data.PlayerData;
import com.tips48.rushMe.teams.Team;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class RMEntityListener extends EntityListener {

        private Team t;
        public RMEntityListener(Team team) {
            this.t = team;
        }
    
    
	@Override
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Entity e = event.getEntity();
		if (!(e instanceof Player)) {
			return;
		}
		PlayerData.damage((Player) e, event.getDamage());
		event.setCancelled(true);
		if (PlayerData.getHealth((Player) e) <= 0) {
			((Player) e).setHealth(0);
		}
	}
        
        @Override
        public void onEntityDeath(EntityDeathEvent ev) {
            
            if (ev.getEntity() instanceof Player) {
                
                Player p = (Player)ev.getEntity();
                Set<String> team = t.getPlayers();
                for(String pl : team) {
                    Bukkit.getPlayer(pl).sendMessage(p.getName() + " has died by ");
                }
                
                
                
            }
            
            
            
          /**  Player p = (Player)ev.getEntity();
            EntityDamageEvent causeOfDeath = p.getLastDamageCause();

            final String item = causeOfDeath.getCause().name();
            
            Set<String> teamPlayers = t.getPlayers();
            
            for(String pl : teamPlayers) {
                Bukkit.getServer().getPlayer(pl).sendMessage( p.getName() + " has died of cause : " + item + " by ");
            }
            
            */
        }
        
        

	@Override
	public void onEntityRegainHealth(EntityRegainHealthEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Entity e = event.getEntity();
		if (!(e instanceof Player)) {
			return;
		}

		// TODO custom health regain
		/*
		 * if(!(PlayerData.isActive((Player) e))) { return; }
		 * PlayerData.heal((Player) e, event.getAmount() * 5);
		 * event.setCancelled(true); }
		 */
	}
}
