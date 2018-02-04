package org.anstreth.schedulebot.commands;

import org.junit.Test;

import static org.anstreth.schedulebot.commands.MenuCommand.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MenuCommandParserTest {
    private MenuCommandParser parser = new MenuCommandParser();

    @Test
    public void parserCorrectlyParsesBackCommand() throws Exception {
        assertThat(parser.parse("/back").getName(), is(BACK));
        assertThat(parser.parse("Back").getName(), is(BACK));
    }

    @Test
    public void parserCorrectlyParsesResetCommand() throws Exception {
        assertThat(parser.parse("/reset_group").getName(), is(RESET_GROUP));
        assertThat(parser.parse("Reset group").getName(), is(RESET_GROUP));
    }
}
