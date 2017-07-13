package org.anstreth.schedulebot.commands;

import org.springframework.stereotype.Component;

@Component
public class UserCommandParser extends CommandParser<UserCommand> {
    {
        possibleNames.put("/tomorrow", UserCommand.TOMORROW);
        possibleNames.put("Tomorrow", UserCommand.TOMORROW);

        possibleNames.put("/today", UserCommand.TODAY);
        possibleNames.put("Today", UserCommand.TODAY);

        possibleNames.put("/week", UserCommand.WEEK);
        possibleNames.put("Week", UserCommand.WEEK);

        possibleNames.put("/menu", UserCommand.MENU);
        possibleNames.put("Menu", UserCommand.MENU);
    }

    @Override
    protected UserCommand getDefault() {
        return UserCommand.UNKNOWN;
    }
}
