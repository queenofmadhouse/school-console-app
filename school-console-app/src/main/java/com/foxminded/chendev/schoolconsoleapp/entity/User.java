package com.foxminded.chendev.schoolconsoleapp.entity;

public class User {

    private long userId;
    private String firstName;
    private String lastName;

    protected User(UserBuilder<? extends UserBuilder> userBuilder) {
        this.userId = userBuilder.userId;
        this.firstName = userBuilder.firstName;
        this.lastName = userBuilder.lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder<SELF extends UserBuilder<SELF>> {

        private long userId;
        private String firstName;
        private String lastName;

        public SELF self() {
            return (SELF) this;
        }

        public User build() {
            return new User(self());
        }

        public SELF withUserId(long userId) {
            this.userId = userId;
            return self();
        }

        public SELF withFirstName(String firstName) {
            this.firstName = firstName;
            return self();
        }

        public SELF withLastName(String lastName) {
            this.lastName = lastName;
            return self();
        }
    }
}
