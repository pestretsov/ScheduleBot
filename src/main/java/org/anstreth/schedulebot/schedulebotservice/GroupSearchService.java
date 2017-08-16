package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.ruzapi.response.Group;
import org.anstreth.schedulebot.model.UserRoute;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.response.PossibleReplies;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerrepository.UserGroupRepository;
import org.anstreth.schedulebot.schedulerrepository.UserRouteRepository;

import java.util.Optional;

class GroupSearchService {
    private final GroupSearcher groupSearcher;
    private final UserGroupRepository userGroupRepository;
    private final UserRouteRepository userRouteRepository;

    GroupSearchService(GroupSearcher groupSearcher, UserGroupRepository userGroupRepository, UserRouteRepository userRouteRepository) {
        this.groupSearcher = groupSearcher;
        this.userGroupRepository = userGroupRepository;
        this.userRouteRepository = userRouteRepository;
    }

    BotResponse handleRequest(UserRequest userRequest) {
        Optional<Group> group = groupSearcher.findGroupByName(userRequest.getMessage());
        if (group.isPresent()) {
            userGroupRepository.save(userRequest.getUserId(), group.get().getId());
            userRouteRepository.save(userRequest.getUserId(), UserRoute.HOME);
            return groupIsFound(group.get());
        }

        return null;
    }

    private BotResponse groupIsFound(Group groupName) {
        return new BotResponse(
                String.format("Your group is set to '%s'.", groupName.getName()),
                PossibleReplies.WITH_GROUP_REPLIES
        );
    }
}
