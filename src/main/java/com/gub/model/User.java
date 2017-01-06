package com.gub.model;

/**
 * Created by GUILLAUME.INGUIMBERT on 05/01/2017.
 */
public class User {

    String login;
    String session;
    String ip;

    public User() {
    }

    public User(String login, String session, String ip) {
        this.login = login;
        this.session = session;
        this.ip = ip;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
