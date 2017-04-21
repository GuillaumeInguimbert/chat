package com.gub.domain;

import java.util.List;
import java.util.Objects;

/**
 * Created by GUILLAUME.INGUIMBERT on 05/01/2017.
 */
public class UserDashboard {

    private List<User> users;

    public UserDashboard() {
    }

    public UserDashboard(List<User> users) {
        this.users = users;
    }

    public int getNbUsers() {
        return Objects.nonNull(users)? users.size(): 0;
    }

    public List<User> getUsers() {
        return users;
    }
}
