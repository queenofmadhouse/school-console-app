package com.foxminded.chendev.schoolconsoleapp.entity;

public class StudentCourseRelation {

    private long studentId;
    private long courseId;

    private StudentCourseRelation(Builder builder) {
        this.studentId = builder.studentId;
        this.courseId = builder.courseId;
    }

    @Override
    public String toString() {
        return "StudentCourseRelation{" +
                "studentID=" + studentId +
                ", courseID=" + courseId +
                '}';
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private long studentId;
        private long courseId;

        public Builder withStudentId(long studentID) {
            this.studentId = studentID;
            return this;
        }

        public Builder withCourseId(long courseID) {
            this.courseId = courseID;
            return this;
        }

        public StudentCourseRelation build() {
            return new StudentCourseRelation(this);
        }
    }
}
