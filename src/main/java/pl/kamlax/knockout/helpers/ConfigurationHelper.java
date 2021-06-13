package pl.kamlax.knockout.helpers;

import org.bukkit.configuration.Configuration;

import java.util.Objects;

/**
 * @author Kamil "kamlax" Okoń
 */

public final class ConfigurationHelper {
    public int
            time,
            resurrectionTime,
            fadeIn,
            stay,
            fadeOut;

    public String isNotKnockedOut;
    public String
            knockedOutTitle,
            knockedOutSubTitle;
    public String
            resuscitationTitle,
            resuscitationSubTitle;
    public String
            resurrectedTitle,
            resurrectedSubTitle;
    public String
            resurrectedToResuscitatorTitle,
            resurrectedToResuscitatorSubTitle;
    public String
            resuscitationToResuscitatorTitle,
            resuscitationToResuscitatorSubTitle;
    public String
            surrenderTitle,
            surrenderSubTitle,
            surrenderMessage;
    public boolean surrenderMessageToPlayers;

    public ConfigurationHelper(Configuration configuration) {
        time = configuration.getInt("options.time");
        resurrectionTime = configuration.getInt("options.resurrectionTime");
        fadeIn = configuration.getInt("options.title.fadeIn");
        stay = configuration.getInt("options.title.stay");
        fadeOut = configuration.getInt("options.title.fadeOut");

        isNotKnockedOut = StringHelper.fixText(Objects.requireNonNull(configuration.getString("messages.isNotKnockedOut")));
        knockedOutTitle = StringHelper.fixText(Objects.requireNonNull(configuration.getString("messages.knockedout.title")));
        knockedOutSubTitle = StringHelper.fixText(Objects.requireNonNull(configuration.getString("messages.knockedout.subtitle")));
        resuscitationTitle = StringHelper.fixText(Objects.requireNonNull(configuration.getString("messages.resuscitation.title")));
        resuscitationSubTitle = StringHelper.fixText(Objects.requireNonNull(configuration.getString("messages.resuscitation.subtitle")));
        resurrectedTitle = StringHelper.fixText(Objects.requireNonNull(configuration.getString("messages.resurrected.title")));
        resurrectedSubTitle = StringHelper.fixText(Objects.requireNonNull(configuration.getString("messages.resurrected.subtitle")));
        resurrectedToResuscitatorTitle = StringHelper.fixText(Objects.requireNonNull(configuration.getString("messages.resurrectedToResuscitator.title")));
        resurrectedToResuscitatorSubTitle = StringHelper.fixText(Objects.requireNonNull(configuration.getString("messages.resurrectedToResuscitator.subtitle")));
        resuscitationToResuscitatorTitle = StringHelper.fixText(Objects.requireNonNull(configuration.getString("messages.resuscitationToResuscitator.title")));
        resuscitationToResuscitatorSubTitle = StringHelper.fixText(Objects.requireNonNull(configuration.getString("messages.resuscitationToResuscitator.subtitle")));
        surrenderTitle = StringHelper.fixText(Objects.requireNonNull(configuration.getString("messages.surrender.title")));
        surrenderSubTitle = StringHelper.fixText(Objects.requireNonNull(configuration.getString("messages.surrender.subtitle")));
        surrenderMessage = StringHelper.fixText(Objects.requireNonNull(configuration.getString("messages.surrender.message")));
        surrenderMessageToPlayers = configuration.getBoolean("messages.surrender.messageToOnlinePlayers");
    }

}
