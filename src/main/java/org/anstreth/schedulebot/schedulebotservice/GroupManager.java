package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.ruzapi.response.Group;
import org.anstreth.ruzapi.response.Groups;
import org.anstreth.ruzapi.ruzapirepository.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Component
class GroupManager {

    private final GroupsRepository groupsRepository;

    @Autowired
    GroupManager(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

    Optional<Group> findGroupByName(String groupName) {
        return Optional.ofNullable(groupsRepository.findGroupsByName(groupName))
                .map(Groups::getGroups)
                .filter(not(List::isEmpty))
                .map(groups -> groups.get(0));
    }

    private <T> Predicate<T> not(Predicate<T> predicate) {
        return predicate.negate();
    }
}
