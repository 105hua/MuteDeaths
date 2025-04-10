/* Licensed under GNU General Public License v3.0 */
package joshdev.muteDeaths;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import joshdev.muteDeaths.events.PlayerDeath;
import joshdev.muteDeaths.events.PlayerJoin;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;

public final class MuteDeaths extends JavaPlugin {

  public static MuteDeaths pluginInstance;
  public static final NamespacedKey MUTE_KEY = new NamespacedKey("mutedeaths", "toggle");

  @Override
  public void onEnable() {
    // Set plugin instance.
    pluginInstance = this;
    // Command manager and annotation parser.
    PaperCommandManager<CommandSourceStack> commandManager =
        PaperCommandManager.builder()
            .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
            .buildOnEnable(this);
    AnnotationParser<CommandSourceStack> annotationParser =
        new AnnotationParser<>(commandManager, CommandSourceStack.class);
    // Register containers.
    try {
      annotationParser.parseContainers();
    } catch (Exception exc) {
      getLogger().warning("Failed to parse command containers. Commands will not work!");
      exc.printStackTrace();
    }
    // Register death event.
    getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
    // Register join event to send a reminder if player is muted.
    getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
    // Ready message
    getLogger().info("MuteDeaths is good to go!");
  }

  @Override
  public void onDisable() {
    getLogger().info("MuteDeaths is shutting down, goodbye!");
  }
}
