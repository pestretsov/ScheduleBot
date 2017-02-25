package org.anstreth.ruzapi.ruzapirepository;

import org.anstreth.ruzapi.response.Groups;

public interface GroupsRepository {
    Groups findGroupsByName(String name);
}
