package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.ruzapi.response.Group;
import org.anstreth.schedulebot.model.UserRoute;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.response.PossibleReplies;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerrepository.UserGroupRepository;
import org.anstreth.schedulebot.schedulerrepository.UserRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class GroupSearchService {
    private final GroupSearcher groupSearcher;
    private final UserGroupRepository userGroupRepository;
    private final UserRouteRepository userRouteRepository;

    @Autowired
    GroupSearchService(GroupSearcher groupSearcher, UserGroupRepository userGroupRepository, UserRouteRepository userRouteRepository) {
        this.groupSearcher = groupSearcher;
        this.userGroupRepository = userGroupRepository;
        this.userRouteRepository = userRouteRepository;
    }

    BotResponse handleRequest(UserRequest userRequest) {
        Optional<Group> group = groupSearcher.findGroupByName(userRequest.getMessage());
        if (group.isPresent()) {
            saveUserGroup(userRequest.getUserId(), group.get());
            setUserToHomeRoute(userRequest.getUserId());
            return groupIsFoundBotResponse(group.get());
        } else {
            return groupNotFoundBotResponse(userRequest);
        }
    }

    private void saveUserGroup(long userId, Group group) {
        userGroupRepository.save(userId, group.getId());
    }

    private void setUserToHomeRoute(long userId) {
        userRouteRepository.save(userId, UserRoute.HOME);
    }

    private BotResponse groupIsFoundBotResponse(Group groupName) {
        return new BotResponse(
                String.format("Your group is set to '%s'.", groupName.getName()),
                PossibleReplies.WITH_GROUP_REPLIES
        );
    }

    private BotResponse groupNotFoundBotResponse(UserRequest userRequest) {
        return new BotResponse(String.format(
                "No group by name '%s' is found! Try again.",
                userRequest.getMessage()
        ));
    }
}
