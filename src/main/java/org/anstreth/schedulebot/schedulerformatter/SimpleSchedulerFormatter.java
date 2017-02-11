package org.anstreth.schedulebot.schedulerformatter;

import org.anstreth.ruzapi.Auditory;
import org.anstreth.ruzapi.Day;
import org.anstreth.ruzapi.Lesson;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class SimpleSchedulerFormatter implements SchedulerFormatter {

    @Override
    public String formatDay(Day scheduleForToday) {
        String lessons = scheduleForToday.getLessons().stream()
                .map(this::formatLesson)
                .collect(Collectors.joining("\n\n"));

        return String.format("Schedule for day %s:\n\n%s", scheduleForToday.getDate(), lessons);
    }

    private String formatLesson(Lesson lesson) {
        return String.format("%s, %s - %s, %s",
                lesson.getSubject(),
                lesson.getTimeStart(),
                lesson.getTimeEnd(),
                getAuditory(lesson));
    }

    private String getAuditory(Lesson lesson) {
        if (lesson.getAuditories().isEmpty()) {
            return "no auditory";
        }

        Auditory auditory = lesson.getAuditories().get(0);
        return String.format("%s, %s", auditory.getName(), auditory.getBuilding().getName());
    }
}
