package com.foxminded.chendev.schoolconsoleapp.entity;

public class StudentCourseRelation {

    private long studentID;
    private long courseID;

    private StudentCourseRelation(Builder builder) {
        this.studentID = builder.studentID;
        this.courseID = builder.courseID;
    }

    @Override
    public String toString() {
        return "StudentCourseRelation{" +
                "studentID=" + studentID +
                ", courseID=" + courseID +
                '}';
    }

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public long getCourseID() {
        return courseID;
    }

    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long studentID;
        private long courseID;

        public Builder withStudentID(long studentID) {
            this.studentID = studentID;
            return this;
        }

        public Builder withCourseID(long courseID) {
            this.courseID = courseID;
            return this;
        }

        public StudentCourseRelation build() {
            return new StudentCourseRelation(this);
        }
    }
}
