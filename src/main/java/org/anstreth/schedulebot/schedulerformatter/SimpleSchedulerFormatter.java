package org.anstreth.schedulebot.schedulerformatter;

import java.util.Calendar;
import org.anstreth.ruzapi.response.Day;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class SimpleSchedulerFormatter implements SchedulerFormatter {

    private final DayFormatter dateFormatter;

    @Autowired
    public SimpleSchedulerFormatter(LessonFormatter lessonFormatter, DayFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    @Override
    public String getNoScheduleForDateMessage(Calendar calendar) {
        return "There are no lessons!";
    }

    @Override
    public String getNoScheduleForWeekMessage(Calendar calendar) {
        return "There are no schedule for this week!";
    }

    @Override
    public String formatDay(Day scheduleForToday) {
        return dateFormatter.formatDay(scheduleForToday);
    }

}
