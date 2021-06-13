package pl.kamlax.knockout.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Kamil "kamlax" Okoń
 */

@RequiredArgsConstructor
@Getter
@Setter
public final class User {
    private final UUID playerUUID;
    private long time, rescueTime;
    private boolean knockout, rescue;
}