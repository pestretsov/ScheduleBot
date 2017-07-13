package org.anstreth.schedulebot.commands;

class MenuCommandParser extends CommandParser<MenuCommand> {

    {
        possibleNames.put("/back", MenuCommand.BACK);
        possibleNames.put("Back", MenuCommand.BACK);

        possibleNames.put("/reset_group", MenuCommand.RESET_GROUP);
        possibleNames.put("Reset group", MenuCommand.RESET_GROUP);
    }

    @Override
    protected MenuCommand getDefault() {
        return MenuCommand.UNKNOWN;
    }

}
