package org.anstreth.schedulebot.schedulerformatter;

import org.anstreth.ruzapi.Auditory;
import org.anstreth.ruzapi.Building;
import org.anstreth.ruzapi.Lesson;
import org.anstreth.ruzapi.LessonType;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SimpleLessonFormatterTest {
    private SimpleLessonFormatter simpleLessonFormatter = new SimpleLessonFormatter();

    @Test
    public void formatterConcatenatesSubjectAndTimesAndAuditoryName() throws Exception {
        String subjectName = "subject name";
        String timeStart = "time start";
        String timeEnd = "time end";
        String auditoryName = "auditory name";
        String auditoryBuilding = "auditory building";
        String lessonTypeName = "lesson type short name";

        Building building = getBuildingWithName(auditoryBuilding);
        Auditory auditory = getAuditoryWithNameAndBuilding(auditoryName, building);
        LessonType lessonType = getLessonTypeWithName(lessonTypeName);

        Lesson lesson = new Lesson();
        lesson.setSubject(subjectName);
        lesson.setTimeStart(timeStart);
        lesson.setTimeEnd(timeEnd);
        lesson.setAuditories(Collections.singletonList(auditory));
        lesson.setLessonType(lessonType);

        String expectedFormat = String.format(
                "%s (%s), %s - %s, %s, %s",
                subjectName,
                lessonTypeName,
                timeStart,
                timeEnd,
                auditoryName,
                auditoryBuilding
        );

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

    private LessonType getLessonTypeWithName(String lessonTypeName) {
        LessonType lessonType = new LessonType();
        lessonType.setName(lessonTypeName);
        return lessonType;
    }

    private Building getBuildingWithName(String auditoryBuilding) {
        Building building = new Building();
        building.setName(auditoryBuilding);
        return building;
    }

    private Auditory getAuditoryWithNameAndBuilding(String auditoryName, Building building) {
        Auditory auditory = new Auditory();
        auditory.setName(auditoryName);
        auditory.setBuilding(building);
        return auditory;
    }
}