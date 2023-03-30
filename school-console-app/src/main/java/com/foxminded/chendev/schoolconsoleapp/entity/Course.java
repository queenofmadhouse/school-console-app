package com.foxminded.chendev.schoolconsoleapp.entity;

public class Course {

    private long courseID;
    private String courseName;
    private String courseDescription;

    private Course(Builder builder) {
        this.courseID = builder.courseID;
        this.courseName = builder.courseName;
        this.courseDescription = builder.courseDescription;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + courseID +
                ", courseName='" + courseName + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                '}';
    }

    public long getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private long courseID;
        private String courseName;
        private String courseDescription;

        public Builder withCourseID(long courseID) {
            this.courseID = courseID;
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
