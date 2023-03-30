package com.foxminded.chendev.schoolconsoleapp.entity;

public class Student {

    private long studentID;
    private long groupID;
    private String firstName;
    private String lastName;

    public Student(Builder builder) {
        this.studentID = builder.studentID;
        this.groupID = builder.groupID;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentID=" + studentID +
                ", groupID=" + groupID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private long studentID;
        private long groupID;
        private String firstName;
        private String lastName;

        public Builder withStudentID(long studentID) {
            this.studentID = studentID;
            return this;
        }

        public Builder withGroupID(Group group) {
            this.groupID = group.getGroupID();
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Student build() {
            return new Student(this);
        }

    }

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public long getGroupID() {
        return groupID;
    }

    public void setGroupID(Group groupID) {
        this.groupID = groupID.getGroupID();
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
}
