/* Licensed under GNU General Public License v3.0 */
package joshdev.muteDeaths.commands;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import joshdev.muteDeaths.MuteDeaths;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.annotations.processing.CommandContainer;

@CommandContainer
public class MuteCommand {

  private static final TextComponent MUTE_ON =
          Component.text("✔ ", NamedTextColor.DARK_GREEN)
                  .append(Component.text("Your death messages are now muted.", NamedTextColor.GREEN));

  private static final TextComponent MUTE_OFF =
          Component.text("✖ ", NamedTextColor.RED)
                  .append(Component.text("Your death messages are now visible to others.", NamedTextColor.GREEN));

  private static final TextComponent ERROR_MESSAGE =
      Component.text(
          "An error occurred while trying to mute death messages. Please contact the server manager.",
          NamedTextColor.RED);

  @Command("mutedeaths|mutedeathmessages|mutedeathmsgs")
  @CommandDescription("Mute death messages.")
  @Permission("mutedeaths.mute")
  @SuppressWarnings("unused")
  public void muteDeathMessages(CommandSourceStack sourceStack) {
    CommandSender sender = sourceStack.getSender();
    if (!(sender instanceof Player player)) {
      sender.sendMessage("You must be a player to use this command.");
      return;
    }
    PersistentDataContainer playerContainer = player.getPersistentDataContainer();
    if (playerContainer.has(MuteDeaths.MUTE_KEY, PersistentDataType.BOOLEAN)) {
      try {
        boolean currentValue = playerContainer.get(MuteDeaths.MUTE_KEY, PersistentDataType.BOOLEAN);
        boolean newValue = !currentValue;
        playerContainer.set(MuteDeaths.MUTE_KEY, PersistentDataType.BOOLEAN, newValue);
        player.sendMessage(newValue ? MUTE_ON : MUTE_OFF);
      } catch (NullPointerException exc) {
        player.sendMessage(ERROR_MESSAGE);
      }
    } else {
      playerContainer.set(MuteDeaths.MUTE_KEY, PersistentDataType.BOOLEAN, true);
      player.sendMessage(MUTE_ON);
    }
  }
}
