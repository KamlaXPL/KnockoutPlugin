package pl.kamlax.knockout;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import pl.kamlax.knockout.formatter.TimeFormatter;

/**
 * @author Kamil "kamlax" Okoń
 */

@RequiredArgsConstructor
public class KnockoutRunnable implements Runnable {
    private final KnockoutPlugin plugin;

    @Override
    public void run() {
        for (var player : Bukkit.getOnlinePlayers())
            plugin.getUserCache().findUser(player.getUniqueId()).ifPresent(user -> {
                if (user.isKnockout())
                    if (!user.isRescue())
                        player.sendTitle(
                                plugin.getConfigurationHelper().knockedOutTitle.replace("{TIME}", TimeFormatter.getSecondsFromLong(user.getTime() - System.currentTimeMillis())),
                                plugin.getConfigurationHelper().knockedOutSubTitle.replace("{TIME}", TimeFormatter.getSecondsFromLong(user.getTime() - System.currentTimeMillis())),
                                plugin.getConfigurationHelper().fadeIn,
                                plugin.getConfigurationHelper().stay,
                                plugin.getConfigurationHelper().fadeOut
                        );
                if (user.getTime() < System.currentTimeMillis())
                    player.setHealth(0.0D);
            });
    }
}
