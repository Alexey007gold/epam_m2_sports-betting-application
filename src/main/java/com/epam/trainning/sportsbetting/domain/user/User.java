package com.epam.trainning.sportsbetting.domain.user;

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
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }


    public static class Builder<T extends Builder<T>> {

        private User user;

        public Builder() {
            this.user = new User();
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
