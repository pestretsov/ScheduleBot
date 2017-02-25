package org.anstreth.ruzapi.ruzapirepository;

import org.anstreth.ruzapi.response.Groups;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestGroupsRepositoryTest {

    private RestGroupsRepository restGroupRepository;

    @Mock
    private RestTemplate restTemplate;

    private String searchQueryUrl = "query_url";

    @Before
    public void setUp() throws Exception {
        restGroupRepository = new RestGroupsRepository(searchQueryUrl, restTemplate);
    }

    @Test
    public void repositoryUsesRestTemplateToFindGroupByName() throws Exception {
        String groupName = "group_name";
        Map<String, String> mapWithGroupName = Collections.singletonMap("group_name", groupName);
        Groups expectedGroups = mock(Groups.class);
        when(restTemplate.getForObject(searchQueryUrl, Groups.class, mapWithGroupName)).thenReturn(expectedGroups);

        Groups foundGroup = restGroupRepository.findGroupsByName(groupName);

        assertThat(foundGroup, is(expectedGroups));
    }

}