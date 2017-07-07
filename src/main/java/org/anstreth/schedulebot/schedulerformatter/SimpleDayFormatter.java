package org.anstreth.schedulebot.schedulerformatter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.anstreth.ruzapi.response.Day;
import org.anstreth.ruzapi.response.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleDayFormatter implements DayFormatter {

    private final LessonFormatter lessonFormatter;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, yyyy-MM-dd");

    @Autowired
    public SimpleDayFormatter(LessonFormatter lessonFormatter) {
        this.lessonFormatter = lessonFormatter;
    }

    @Override
    public String formatDay(Day scheduleForToday) {
        String lessons = getLessonsString(scheduleForToday.getLessons());
        return String.format("Schedule for %s:\n\n%s", formatDate(scheduleForToday), lessons);
    }

    private String formatDate(Day scheduleForToday) {
        return simpleDateFormat.format(scheduleForToday.getDate());
    }

    private String getLessonsString(List<Lesson> lessons) {
        if (lessons.isEmpty()) {
            return "There are no lessons for this day!";
        }

        return lessons.stream()
            .map(lessonFormatter::formatLesson)
            .collect(Collectors.joining("\n\n"));
    }

}
