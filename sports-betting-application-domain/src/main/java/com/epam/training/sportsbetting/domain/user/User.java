package com.epam.training.sportsbetting.domain.user;

import java.util.Arrays;

public class User {

    private String email;
    private char[] password;
    private boolean enabled;

    protected User() {
    }

    protected User(String email, char[] password, boolean enabled) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public char[] getPassword() {
        if (password == null) return null;
        return Arrays.copyOf(password, password.length);
    }

    public boolean isEnabled() {
        return enabled;
    }


    public static class Builder<T extends Builder<T>> {

        protected User user;

        public Builder() {
            this.user = new User();
        }

        protected Builder(User user) {
            this.user = user;
        }

        public T withEmail(String email) {
            user.email = email;
            return self();
        }

        public T withPassword(char[] password) {
            user.password = password;
            return self();
        }

        public T withEnabled(boolean enabled) {
            user.enabled = enabled;
            return self();
        }

        @SuppressWarnings("unchecked")
        protected T self() {
            return (T) this;
        }
    }
}
