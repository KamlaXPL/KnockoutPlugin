package pl.kamlax.knockout;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kamlax.knockout.events.PlayerEndRescueEvent;
import pl.kamlax.knockout.events.PlayerKnockoutEvent;
import pl.kamlax.knockout.events.PlayerStartRescueEvent;
import pl.kamlax.knockout.events.PlayerStopRescueEvent;
import pl.kamlax.knockout.formatter.TimeFormatter;
import pl.kamlax.knockout.user.User;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Kamil "kamlax" Okoń
 */

@RequiredArgsConstructor
public class KnockoutListeners implements Listener {
    private final KnockoutPlugin plugin;
    private final Set<Block> blocks = new HashSet<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();
        plugin.getUserCache().findUser(playerUUID).ifPresentOrElse(user -> {
        }, () -> plugin.getUserCache().createUser(new User(playerUUID)));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Location
                to = event.getTo(),
                from = event.getFrom();

        if (to.getBlockX() != from.getBlockX() || to.getBlockY() != from.getBlockY() || to.getBlockZ() != from.getBlockZ()) {
            Player player = event.getPlayer();
            plugin.getUserCache().findUser(player.getUniqueId())
                    .filter(User::isKnockout)
                    .ifPresent(user -> createBarrierBlock(player, to));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent event) {
        var player = event.getEntity().getPlayer();
        if (Objects.isNull(player))
            return;

        plugin.getUserCache().findUser(player.getUniqueId()).ifPresent(user -> {
            if (!user.isKnockout()) {
                event.setCancelled(true);
                PlayerKnockoutEvent playerKnockoutEvent = new PlayerKnockoutEvent(player);
                Bukkit.getPluginManager().callEvent(playerKnockoutEvent);
                if (playerKnockoutEvent.isCancelled())
                    return;
                user.setKnockout(true);
                user.setTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(plugin.getConfigurationHelper().time));
                createBarrierBlock(player, player.getLocation());
                return;
            }
            user.setRescue(false);
            user.setTime(0L);
            user.setKnockout(false);
        });
    }

    @EventHandler
    public void onRescue(PlayerInteractAtEntityEvent event) {
        if (!(event.getRightClicked() instanceof Player player))
            return;
        if (!player.isSneaking())
            return;
        var target = ((Player) event.getRightClicked()).getPlayer();
        if (Objects.isNull(target))
            return;
        var user = plugin.getUserCache().getUser(player.getUniqueId());
        var targetUser = plugin.getUserCache().getUser(target.getUniqueId());
        if (!targetUser.isKnockout() || user.isKnockout() || targetUser.isRescue())
            return;

        PlayerStartRescueEvent playerStartRescueEvent = new PlayerStartRescueEvent(player, target);
        Bukkit.getPluginManager().callEvent(playerStartRescueEvent);
        if (playerStartRescueEvent.isCancelled())
            return;
        targetUser.setRescue(true);
        targetUser.setRescueTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(plugin.getConfigurationHelper().resurrectionTime));
        startRescueTask(player, target);
    }

    private void startRescueTask(Player player, Player target) {
        String
                resurrectedToResuscitatorTitle = plugin.getConfigurationHelper().resurrectedToResuscitatorTitle.replace("{PLAYER}", target.getName()),
                resurrectedToResuscitatorSubTitle = plugin.getConfigurationHelper().resurrectedToResuscitatorSubTitle.replace("{PLAYER}", target.getName()),
                resurrectedTitle = plugin.getConfigurationHelper().resurrectedTitle.replace("{PLAYER}", player.getName()),
                resurrectedSubTitle = plugin.getConfigurationHelper().resurrectedSubTitle.replace("{PLAYER}", player.getName());

        new BukkitRunnable() {

            @Override
            public void run() {
                plugin.getUserCache().findUser(target.getUniqueId()).ifPresent(user -> {
                    if (!(player.isSneaking() || user.isRescue()) || player.getLocation().distance(target.getLocation()) > 3) {
                        Bukkit.getPluginManager().callEvent(new PlayerStopRescueEvent(player, target, user.getRescueTime()));
                        user.setRescue(false);
                        cancel();
                    }
                    player.sendTitle(
                            plugin.getConfigurationHelper().resuscitationToResuscitatorTitle.replace("{TIME}", TimeFormatter.getSecondsFromLong(user.getRescueTime() - System.currentTimeMillis())),
                            plugin.getConfigurationHelper().resuscitationToResuscitatorSubTitle.replace("{TIME}", TimeFormatter.getSecondsFromLong(user.getRescueTime() - System.currentTimeMillis())),
                            plugin.getConfigurationHelper().fadeIn,
                            plugin.getConfigurationHelper().stay,
                            plugin.getConfigurationHelper().fadeOut
                    );
                    target.sendTitle(
                            plugin.getConfigurationHelper().resuscitationTitle.replace("{TIME}", TimeFormatter.getSecondsFromLong(user.getRescueTime() - System.currentTimeMillis())),
                            plugin.getConfigurationHelper().resuscitationSubTitle.replace("{TIME}", TimeFormatter.getSecondsFromLong(user.getRescueTime() - System.currentTimeMillis())),
                            plugin.getConfigurationHelper().fadeIn,
                            plugin.getConfigurationHelper().stay,
                            plugin.getConfigurationHelper().fadeOut
                    );
                    if (user.getRescueTime() < System.currentTimeMillis()) {
                        Bukkit.getPluginManager().callEvent(new PlayerEndRescueEvent(player, target));
                        user.setRescue(false);
                        user.setKnockout(false);
                        user.setRescueTime(0L);
                        player.sendTitle(
                                resurrectedToResuscitatorTitle,
                                resurrectedToResuscitatorSubTitle,
                                plugin.getConfigurationHelper().fadeIn,
                                plugin.getConfigurationHelper().stay,
                                plugin.getConfigurationHelper().fadeOut
                        );
                        target.sendTitle(
                                resurrectedTitle,
                                resurrectedSubTitle,
                                plugin.getConfigurationHelper().fadeIn,
                                plugin.getConfigurationHelper().stay,
                                plugin.getConfigurationHelper().fadeOut
                        );
                        for (var barriers : blocks)
                            barriers.getState().update();
                        cancel();
                    }
                });
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    private void createBarrierBlock(Player player, Location location) {
        Block block = location.clone().add(0.0D, 1.0D, 0.0D).getBlock();
        player.sendBlockChange(block.getLocation(), Bukkit.createBlockData(Material.BARRIER));
        blocks.add(block);
        for (var barriers : blocks)
            if (!barriers.equals(block))
                barriers.getState().update();
    }
}
