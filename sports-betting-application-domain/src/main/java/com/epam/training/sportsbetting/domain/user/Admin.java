package com.epam.training.sportsbetting.domain.user;

public class Admin extends User {
    public Admin(String email, char[] password, boolean enabled) {
        super(email, password, enabled);
    }
}
