package org.anstreth.schedulebot.commands;

import org.springframework.stereotype.Component;

@Component
public class UserCommandParser extends CommandParser<UserCommand> {

    {
        addCommand(UserCommand.TOMORROW, "/tomorrow", "Tomorrow");
        addCommand(UserCommand.TODAY, "/today", "Today");
        addCommand(UserCommand.WEEK, "/week", "Week");
        addCommand(UserCommand.MENU, "/menu", "Menu");
    }

    @Override
    protected UserCommand getDefault() {
        return UserCommand.UNKNOWN;
    }
}
