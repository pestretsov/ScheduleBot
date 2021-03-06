package org.anstreth.schedulebot.schedulerformatter;

import org.anstreth.ruzapi.response.Auditory;
import org.anstreth.ruzapi.response.Lesson;
import org.springframework.stereotype.Component;

@Component
class SimpleLessonFormatter implements LessonFormatter{

    @Override
    public String formatLesson(Lesson lesson) {
        return String.format("%s - %s: %s (%s)\n%s",
                lesson.getTimeStart(),
                lesson.getTimeEnd(),
                lesson.getSubject(),
                lesson.getLessonType().getName(),
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
