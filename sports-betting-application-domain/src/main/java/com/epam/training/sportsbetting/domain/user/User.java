package com.epam.training.sportsbetting.domain.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Arrays;

public class User {

    @NotNull
    @Positive
    private Integer id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public char[] getPassword() {
        if (password == null) {
            return null;
        }
        return Arrays.copyOf(password, password.length);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public static class Builder<T extends Builder<T>> {

        protected User user;

        public Builder() {
            this.user = new User();
        }

        protected Builder(User user) {
            this.user = user;
        }

        public T withId(Integer id) {
            user.id = id;
            return self();
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
