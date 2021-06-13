package pl.kamlax.knockout.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * @author Kamil "kamlax" Okoń
 */

@RequiredArgsConstructor
@Getter
public final class PlayerStopRescueEvent extends Event {
    private final HandlerList handlerList = new HandlerList();
    private final Player resuscitator, resuscitated;
    private final long remainingTime;

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
