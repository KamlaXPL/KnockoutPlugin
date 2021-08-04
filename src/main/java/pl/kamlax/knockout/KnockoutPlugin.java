package pl.kamlax.knockout;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import pl.kamlax.knockout.helpers.ConfigurationHelper;
import pl.kamlax.knockout.user.UserCache;

import java.util.Objects;
import java.util.logging.Level;

/**
 * @author Kamil "kamlax" Okoń
 */

@Getter
public final class KnockoutPlugin extends JavaPlugin {
    private UserCache userCache;
    private ConfigurationHelper configurationHelper;

    @Override
    public void onLoad() {
        saveDefaultConfig();
        configurationHelper = new ConfigurationHelper(getConfig());
        userCache = new UserCache();
    }

    @Override
    public void onEnable() {
        if (getJavaVersion() < 15) {
            getServer().getLogger().log(Level.SEVERE, "Use java 15 or higher.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(new KnockoutListeners(this), this);
        getServer().getScheduler().runTaskTimer(this, new KnockoutRunnable(this), 10*20L, 25L);
        Objects.requireNonNull(getCommand("knockout")).setExecutor(new KnockoutCommand(this));
    }

    //not mine
    private int getJavaVersion() {
        String version = System.getProperty("java.version");
        if (version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            int dot = version.indexOf(".");
            if (dot != -1)
                version = version.substring(0, dot);
        }
        return Integer.parseInt(version);
    }
}
