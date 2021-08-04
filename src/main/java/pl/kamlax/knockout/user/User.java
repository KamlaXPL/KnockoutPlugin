package pl.kamlax.knockout.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Data;

import java.util.UUID;

/**
 * @author Kamil "kamlax" Okoń
 */

@RequiredArgsConstructor
@Getter
@Setter
@Data
public final class User {
    private final UUID playerUUID;
    private long time, rescueTime;
    private boolean knockout, rescue;
}
