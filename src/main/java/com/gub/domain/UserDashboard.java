package com.gub.domain;

import java.util.List;

/**
 * Created by GUILLAUME.INGUIMBERT on 05/01/2017.
 */
public class UserDashboard {

    private int nbUsers;
    private List<User> users;

    public UserDashboard() {
    }

    public UserDashboard(int nbUsers, List<User> users) {
        this.nbUsers = nbUsers;
        this.users = users;
    }

    public int getNbUsers() {
        return nbUsers;
    }

    public void setNbUsers(int nbUsers) {
        this.nbUsers = nbUsers;
    }

    public List<User> getUsers() {
        return users;
    }
}
