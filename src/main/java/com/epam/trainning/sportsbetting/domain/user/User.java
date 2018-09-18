package com.epam.trainning.sportsbetting.domain.user;

public class User {

    private String email;
    private char[] password;
    private boolean enabled;

    public User(String email, char[] password, boolean enabled) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public char[] getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
