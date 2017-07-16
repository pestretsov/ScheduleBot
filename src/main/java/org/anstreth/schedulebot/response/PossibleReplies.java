package org.anstreth.schedulebot.response;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class PossibleReplies {
    private PossibleReplies() {
    }

    public static final List<List<String>> WITH_GROUP_REPLIES = Arrays.asList(
            Arrays.asList("Today", "Tomorrow", "Week"),
            Collections.singletonList("Menu")
    );
    public static final List<List<String>> MENU_REPLIES = Collections.singletonList(
            Arrays.asList("Reset group", "Back")
    );
}
