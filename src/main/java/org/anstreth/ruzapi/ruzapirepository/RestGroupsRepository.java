package org.anstreth.ruzapi.ruzapirepository;

import org.anstreth.ruzapi.response.Groups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Repository
class RestGroupsRepository implements GroupsRepository {
    private final RestTemplate restTemplate;
    private final String findByNameUrl;

    @Autowired
    RestGroupsRepository(@Value("${ruzapi.group_search}") String findByNameUrl, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.findByNameUrl = findByNameUrl;
    }

    @Override
    public Groups findGroupsByName(String groupName) {
        Map<String, String> queryParams = Collections.singletonMap("group_name", groupName);
        return restTemplate.getForObject(findByNameUrl, Groups.class, queryParams);
    }
}
