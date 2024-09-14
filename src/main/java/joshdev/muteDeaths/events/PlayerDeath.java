/* Licensed under GNU General Public License v3.0 */
package joshdev.muteDeaths.events;

import joshdev.muteDeaths.MuteDeaths;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerDeath implements Listener {

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    Component deathMessage = event.deathMessage();
    event.deathMessage(null);
    MuteDeaths.pluginInstance
        .getServer()
        .getOnlinePlayers()
        .forEach(
            player -> {
              PersistentDataContainer playerContainer = player.getPersistentDataContainer();
              if (playerContainer.has(MuteDeaths.MUTE_KEY, PersistentDataType.BOOLEAN)) {
                try {
                  boolean playerToggle =
                      playerContainer.get(MuteDeaths.MUTE_KEY, PersistentDataType.BOOLEAN);
                  if (!playerToggle) {
                    if (deathMessage != null) {
                      player.sendMessage(deathMessage);
                    }
                  }
                } catch (NullPointerException exc) {
                  MuteDeaths.pluginInstance
                      .getLogger()
                      .warning("Couldn't get death message for player " + player.getName());
                }
              } else {
                if (deathMessage != null) {
                  player.sendMessage(deathMessage);
                }
              }
            });
  }
}
