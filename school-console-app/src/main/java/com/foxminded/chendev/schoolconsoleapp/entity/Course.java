package com.foxminded.chendev.schoolconsoleapp.entity;

public class Course {

    private long courseId;
    private String courseName;
    private String courseDescription;

    private Course(Builder builder) {
        this.courseId = builder.courseId;
        this.courseName = builder.courseName;
        this.courseDescription = builder.courseDescription;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                '}';
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public static class Builder {

        private long courseId;
        private String courseName;
        private String courseDescription;

        public Builder withCourseId(long courseID) {
            this.courseId = courseID;
            return this;
        }

        public Builder withCourseName(String courseName) {
            this.courseName = courseName;
            return this;
        }

        public Builder withCourseDescription(String courseDescription) {
            this.courseDescription = courseDescription;
            return this;
        }

        public Course build() {
            return new Course(this);
        }
    }
}
