package pl.kamlax.knockout.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Kamil "kamlax" Okoń
 */

public class UserCache {
    private final Map<UUID, User> userMap = new HashMap<>();

    public void createUser(User user) {
        userMap.put(user.getPlayerUUID(), user);
    }

    public User getUser(UUID uuid) {
        return userMap.get(uuid);
    }

    public Optional<User> findUser(UUID uuid) {
        return Optional.ofNullable(getUser(uuid));
    }
}
