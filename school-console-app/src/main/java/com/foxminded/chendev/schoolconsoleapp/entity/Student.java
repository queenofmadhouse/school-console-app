package com.foxminded.chendev.schoolconsoleapp.entity;

public class Student extends User{

    private long groupId;


    public Student(StudentBuilder userStudentBuilder) {
        super(userStudentBuilder);
        this.groupId = userStudentBuilder.groupId;
    }

    public static StudentBuilder builder() {
        return new StudentBuilder();
    }

    @Override
    public String toString() {
        return "Student{" +
                "userID=" + getUserId() +
                ", groupID=" + groupId +
                ", firstName='" + super.getFirstName() + '\'' +
                ", lastName='" + super.getLastName() + '\'' +
                '}';
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public static class StudentBuilder extends UserBuilder<StudentBuilder> {

        private long groupId;

        @Override
        public StudentBuilder self() {
            return this;
        }

        public Student build() {
            return new Student(self());
        }

        public StudentBuilder withGroupId(long groupId) {
            this.groupId = groupId;
            return this;
        }
    }
}
