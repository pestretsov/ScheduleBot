package org.anstreth.schedulebot.commands;

import org.springframework.stereotype.Component;

@Component
class MenuCommandParser extends CommandParser<MenuCommand> {

    {
        addCommand(MenuCommand.BACK, "/back", "Back");
        addCommand(MenuCommand.RESET_GROUP, "/reset_group", "Reset group");
    }

    @Override
    protected MenuCommand getDefault() {
        return MenuCommand.UNKNOWN;
    }

}
