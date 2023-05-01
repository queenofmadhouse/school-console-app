package com.foxminded.chendev.schoolconsoleapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "students", schema = "school")
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User{

    @Column(name = "group_id")
    private long groupId;

    @ManyToMany
    @JoinTable(
            name = "students_courses_relation",
            schema = "school",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses;

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Student(StudentBuilder userStudentBuilder) {
        super(userStudentBuilder);
        this.groupId = userStudentBuilder.groupId;
    }

    public Student() {
        super();
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

        @Override
        public Student build() {
            return new Student(self());
        }

        public StudentBuilder withGroupId(long groupId) {
            this.groupId = groupId;
            return this;
        }
    }
}
