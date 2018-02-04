package org.anstreth.schedulebot.commands;

import org.junit.Test;

import static org.anstreth.schedulebot.commands.UserCommand.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserCommandParserTest {

    private UserCommandParser parser = new UserCommandParser();

    @Test
    public void parserCorrectlyParsesTomorrowCommand() throws Exception {
        assertThat(parser.parse("/tomorrow").getName(), is(TOMORROW));
        assertThat(parser.parse("Tomorrow").getName(), is(TOMORROW));
    }

    @Test
    public void parserCorrectlyParsesTodayCommand() throws Exception {
        assertThat(parser.parse("/today").getName(), is(TODAY));
        assertThat(parser.parse("Today").getName(), is(TODAY));
    }

    @Test
    public void parserCorrectlyParsesWeekCommand() throws Exception {
        assertThat(parser.parse("/week").getName(), is(WEEK));
        assertThat(parser.parse("Week").getName(), is(WEEK));
    }

    @Test
    public void parserCorrectlyParsesMenuCommand() throws Exception {
        assertThat(parser.parse("/menu").getName(), is(MENU));
        assertThat(parser.parse("Menu").getName(), is(MENU));
    }

    @Test
    public void ifCommandIsWierdThen_UNKNOWN_IsReturned() throws Exception {
        assertThat(parser.parse("testtest").getName(), is(UNKNOWN));
    }

    @Test
    public void commandWithNotTrimmedSpacesIscorrectlyParsed() throws Exception {
        assertThat(parser.parse("  /today  ").getName(), is(TODAY));
    }

    @Test
    public void commandFromGroupChatIsCorrectlyParsed() throws Exception {
        assertThat(parser.parse("/today@test_bot_bot").getName(), is(TODAY));
    }

    @Test
    public void fullCommandFromGroupChatIsCorrectlyParsed() throws Exception {
        FullCommand<UserCommand> fullCommand = parser.parse("/set_group@test_bot_bot 12345/6");
        assertThat(fullCommand.getName(), is(SET_GROUP));
        assertThat(fullCommand.getArgument(), is("12345/6"));
    }
}
