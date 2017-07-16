package org.anstreth.schedulebot.response;

import java.util.Arrays;
import java.util.List;

public final class PossibleReplies {
    private PossibleReplies() {}
    public static final List<String> WITH_GROUP_REPLIES
            = Arrays.asList("Today", "Tomorrow", "Week", "Menu");
    public static final List<String> MENU_REPLIES
            = Arrays.asList("Reset group", "Back");
}
