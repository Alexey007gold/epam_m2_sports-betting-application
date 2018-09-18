package com.epam.trainning.sportsbetting.domain.user;

public class Admin extends User {
    public Admin(String email, char[] password, boolean enabled) {
        super(email, password, enabled);
    }
}
