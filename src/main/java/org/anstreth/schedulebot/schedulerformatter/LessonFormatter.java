package org.anstreth.schedulebot.schedulerformatter;

import org.anstreth.ruzapi.Lesson;

interface LessonFormatter {
    String formatLesson(Lesson lesson);
}
