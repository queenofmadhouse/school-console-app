package com.foxminded.chendev.schoolconsoleapp.entity;

public class Student {

    private long studentId;
    private long groupId;
    private String firstName;
    private String lastName;

    public Student(Builder builder) {
        this.studentId = builder.studentId;
        this.groupId = builder.groupId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentID=" + studentId +
                ", groupID=" + groupId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(Group groupId) {
        this.groupId = groupId.getGroupId();
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private long studentId;
        private long groupId;
        private String firstName;
        private String lastName;

        public Builder withStudentId(long studentID) {
            this.studentId = studentID;
            return this;
        }

        public Builder withGroupId(long groupId) {
            this.groupId = groupId;
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
}
