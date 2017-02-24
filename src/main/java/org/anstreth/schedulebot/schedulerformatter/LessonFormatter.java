package org.anstreth.schedulebot.schedulerformatter;

import org.anstreth.ruzapi.response.Lesson;

interface LessonFormatter {
    String formatLesson(Lesson lesson);
}
