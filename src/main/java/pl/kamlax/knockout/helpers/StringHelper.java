package pl.kamlax.knockout.helpers;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author Kamil "kamlax" Okoń
 */

public final class StringHelper {

    @Contract(pure = true)
    private StringHelper() { }

    @NotNull
    public static String fixText(@NotNull String message){
        return ChatColor.translateAlternateColorCodes('&', message
                .replace(">>", "\u00BB")
                .replace("<<", "\u00AB"));
    }
}
