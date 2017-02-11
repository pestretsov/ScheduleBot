package org.anstreth.schedulebot.schedulerformatter;

import org.anstreth.ruzapi.Auditory;
import org.anstreth.ruzapi.Building;
import org.anstreth.ruzapi.Lesson;
import org.junit.Test;

import javax.security.auth.Subject;

import java.util.Collections;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleLessonFormatterTest {
    private SimpleLessonFormatter simpleLessonFormatter = new SimpleLessonFormatter();

    @Test
    public void formatterConcatenatesSubjectAndTimesAndAuditoryName() throws Exception {
        String subjectName = "subject name";
        String timeStart = "time start";
        String timeEnd = "time end";
        String auditoryName = "auditory name";
        String auditoryBuilding = "auditory building";

        Building building = new Building();
        building.setName(auditoryBuilding);

        Auditory auditory = new Auditory();
        auditory.setName(auditoryName);
        auditory.setBuilding(building);

        Lesson lesson = new Lesson();
        lesson.setSubject(subjectName);
        lesson.setTimeStart(timeStart);
        lesson.setTimeEnd(timeEnd);
        lesson.setAuditories(Collections.singletonList(auditory));

        String expectedFormat = String.format("%s, %s - %s, %s, %s", subjectName, timeStart, timeEnd, auditoryName, auditoryBuilding);

        String formattedLesson = simpleLessonFormatter.formatLesson(lesson);

        assertThat(formattedLesson, is(expectedFormat));
    }

    @Test
    public void ifAuditoriesListIsEmptyPlaceholderIsPlacedInstead() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setAuditories(Collections.emptyList());

        String formattedLesson = simpleLessonFormatter.formatLesson(lesson);

        assertThat(formattedLesson, endsWith("no auditory"));
    }
}