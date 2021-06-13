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
public final class PlayerEndRescueEvent extends Event {
    private final HandlerList handlerList = new HandlerList();
    private final Player resuscitator, resuscitated;

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
