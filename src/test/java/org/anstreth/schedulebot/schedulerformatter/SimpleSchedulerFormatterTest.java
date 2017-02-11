package org.anstreth.schedulebot.schedulerformatter;

import org.anstreth.ruzapi.Day;
import org.anstreth.ruzapi.Lesson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class SimpleSchedulerFormatterTest {
    @InjectMocks
    private SimpleSchedulerFormatter schedulerFormatter;

    @Mock
    private LessonFormatter lessonFormatter;

    @Test
    public void formatterProducesStringStartingFromCertainWordsAndDateOfTheDay() throws Exception {
        Day scheduleForToday = new Day();
        scheduleForToday.setLessons(Collections.emptyList());
        String dateStringInDay = "date string";
        scheduleForToday.setDate(dateStringInDay);
        String expectedPrefix = String.format("Schedule for day %s:\n\n", dateStringInDay);

        String formattedResult = schedulerFormatter.formatDay(scheduleForToday);

        assertThat(formattedResult, startsWith(expectedPrefix));
    }

    @Test
    public void everyLessonIsFromattedWithLessonFromatter() throws Exception {
        Day dayWithLessons = new Day();
        Lesson lesson = mock(Lesson.class);
        dayWithLessons.setLessons(Collections.singletonList(lesson));
        String formattedLessonString = "formatted lesson string";
        given(lessonFormatter.formatLesson(lesson)).willReturn(formattedLessonString);

        String formattedResult = schedulerFormatter.formatDay(dayWithLessons);

        assertThat(formattedResult, endsWith(formattedLessonString));
    }

    @Test
    public void ifThereAreNoLessonsThereAreExpectedPlaceholder() throws Exception {
        Day dayWithoutLessons = new Day();
        dayWithoutLessons.setLessons(Collections.emptyList());
        String expectedPlaceholder = "There are no lessons for this day!";

        String formattedReslut = schedulerFormatter.formatDay(dayWithoutLessons);

        assertThat(formattedReslut, endsWith(expectedPlaceholder));
    }
}