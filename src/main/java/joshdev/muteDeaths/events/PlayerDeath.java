/* Licensed under GNU General Public License v3.0 */
package joshdev.muteDeaths.events;

import joshdev.muteDeaths.MuteDeaths;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerDeath implements Listener {

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    Player player = event.getPlayer();
    PersistentDataContainer playerContainer = player.getPersistentDataContainer();

    Boolean isMuted = playerContainer.get(MuteDeaths.MUTE_KEY, PersistentDataType.BOOLEAN);

    if (Boolean.TRUE.equals(isMuted)) {
      // Cancel death message if player is muted.
      event.deathMessage(null);
    }
  }
}
