package org.anstreth.ruzapi.ruzapirepository;

import org.anstreth.ruzapi.response.WeekSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestRuzApiRepository implements RuzApiRepository {

    private final String weekScheduleURL;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final RestTemplate restTemplate;

    @Autowired
    public RestRuzApiRepository(@Value("${ruzapi.weekschedule}") String weekScheduleURL, RestTemplate restTemplate) {
        this.weekScheduleURL = weekScheduleURL;
        this.restTemplate = restTemplate;
    }


    @Override
    public WeekSchedule getWeekScheduleForGroupForDate(int groupId, Calendar date) {
        return restTemplate.getForObject(
                weekScheduleURL,
                WeekSchedule.class,
                getMapOfParams(groupId, date));
    }

    private Map<String, String> getMapOfParams(int groupId, Calendar date) {
        Map<String, String> params = new HashMap<>();
        params.put("group_id", Integer.toString(groupId));
        params.put("date", simpleDateFormat.format(date.getTime()));
        return params;
    }
}
