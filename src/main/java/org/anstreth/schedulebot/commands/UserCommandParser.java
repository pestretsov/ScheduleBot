package org.anstreth.schedulebot.commands;

import org.springframework.stereotype.Component;

@Component
public class UserCommandParser extends CommandParser<UserCommand> {

    {
        addCommand(UserCommand.TOMORROW, "/tomorrow", "Tomorrow");
        addCommand(UserCommand.TODAY, "/today", "Today");
        addCommand(UserCommand.WEEK, "/week", "Week");
        addCommand(UserCommand.MENU, "/menu", "Menu");
        addCommand(UserCommand.SET_GROUP, "/set_group", "SetGroup");
    }

    @Override
    protected UserCommand getDefault() {
        return UserCommand.UNKNOWN;
    }
}
