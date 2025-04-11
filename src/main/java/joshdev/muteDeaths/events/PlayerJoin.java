package joshdev.muteDeaths.events;

import joshdev.muteDeaths.MuteDeaths;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class PlayerJoin implements Listener {
    private static final TextComponent MUTE_REMINDER =
        Component.text("\uD83D\uDD15 ", NamedTextColor.GOLD)
                .append(Component.text("Your death messages are muted. ", NamedTextColor.YELLOW))
                .append(Component.text("[Toggle]", NamedTextColor.AQUA)
                        .clickEvent(ClickEvent.runCommand("/mutedeaths"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to toggle your death message visibility."))));

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer playerContainer = player.getPersistentDataContainer();

        Boolean isMuted = playerContainer.get(MuteDeaths.MUTE_KEY, PersistentDataType.BOOLEAN);

        if (Boolean.TRUE.equals(isMuted)) {
            // When player joins, send them a reminder when they are still muted.
            player.sendMessage(MUTE_REMINDER);
        }
    }
}
