package org.anstreth.ruzapi.ruzapiservice;

import org.anstreth.ruzapi.response.WeekSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;

@Component
public class RestRuzApiRepository implements RuzApiRepository {

    private final String myGroupScheduleURL;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final RestTemplate restTemplate;

    @Autowired
    public RestRuzApiRepository(@Value("${ruzapi.myschedule}") String myGroupScheduleURL, RestTemplate restTemplate) {
        this.myGroupScheduleURL = myGroupScheduleURL;
        this.restTemplate = restTemplate;
    }


    @Override
    public WeekSchedule getWeekScheduleForDate(Calendar date) {
        return restTemplate.getForObject(
                myGroupScheduleURL,
                WeekSchedule.class,
                Collections.singletonMap("date", simpleDateFormat.format(date.getTime())));
    }
}
