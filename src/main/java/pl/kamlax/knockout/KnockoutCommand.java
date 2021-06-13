package pl.kamlax.knockout;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

/**
 * @author Kamil "kamlax" Okoń
 */

@RequiredArgsConstructor
public class KnockoutCommand implements CommandExecutor {
    private final KnockoutPlugin plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            Bukkit.getLogger().log(Level.SEVERE, "Command only for players!");
            return false;
        }
        plugin.getUserCache().findUser(player.getUniqueId()).ifPresent(user -> {
            if (!user.isKnockout()) {
                sender.sendMessage(plugin.getConfigurationHelper().isNotKnockedOut);
                return;
            }
            player.setHealth(0.0D);
            String surrenderMessage = plugin.getConfigurationHelper().surrenderMessage.replace("{PLAYER}", player.getName());
            if (plugin.getConfigurationHelper().surrenderMessageToPlayers)
                for (var players : Bukkit.getOnlinePlayers())
                    players.sendMessage(surrenderMessage);
            player.sendTitle(
                    plugin.getConfigurationHelper().surrenderTitle,
                    plugin.getConfigurationHelper().surrenderSubTitle,
                    plugin.getConfigurationHelper().fadeIn,
                    plugin.getConfigurationHelper().stay,
                    plugin.getConfigurationHelper().fadeOut
            );
        });
        return false;
    }
}
