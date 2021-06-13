package pl.kamlax.knockout.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * @author Kamil "kamlax" Okoń
 */

@RequiredArgsConstructor
@Getter
public final class PlayerStartRescueEvent extends Event implements Cancellable {
    private final HandlerList handlerList = new HandlerList();
    private final Player resuscitator, resuscitated;
    private boolean cancelled;

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
