package com.foxminded.chendev.schoolconsoleapp.controller;

import com.foxminded.chendev.schoolconsoleapp.controller.validator.Validator;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.service.CourseService;
import com.foxminded.chendev.schoolconsoleapp.service.GroupService;
import com.foxminded.chendev.schoolconsoleapp.service.StudentService;
import com.foxminded.chendev.schoolconsoleapp.view.ConsoleHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MenuOptionsTest {

    @Mock
    private StudentService studentServiceMock;

    @Mock
    private GroupService groupServiceMock;

    @Mock
    private CourseService courseServiceMock;

    @Mock
    private Validator validatorMock;

    @Mock
    private ConsoleHandler consoleHandlerMock;

    @Test
    void shouldExecuteFindStudentsByCourse() {

        when(consoleHandlerMock.readUserInputString()).thenReturn("Math");

        List<Student> students = new ArrayList<>();
        students.add(Student.builder()
                .withUserId(1)
                .withFirstName("John")
                .withLastName("Doe")
                .build());
        students.add(Student.builder()
                .withUserId(2)
                .withFirstName("Jane")
                .withLastName("Doe")
                .build());
        students.add(Student.builder()
                .withUserId(3)
                .withFirstName("Bob")
                .withLastName("Smith")
                .build());

        when(courseServiceMock.findAllStudentsByCourseName("Math")).thenReturn(students);

        MenuOptions.FIND_STUDENTS_BY_COURSE.execute(studentServiceMock, groupServiceMock, courseServiceMock,
                validatorMock, consoleHandlerMock);

        InOrder inOrder = inOrder(consoleHandlerMock);
        inOrder.verify(consoleHandlerMock).printMessage("Enter course name: ");
        for (Student student : students) {
            inOrder.verify(consoleHandlerMock).printMessage(student.toString());
        }
    }

    @Test
    void shouldExecuteAddNewStudent() {

        when(consoleHandlerMock.readUserInputString()).thenReturn("John", "Doe");

        MenuOptions.ADD_NEW_STUDENT.execute(studentServiceMock, groupServiceMock, courseServiceMock,
                validatorMock, consoleHandlerMock);

        verify(studentServiceMock).save(any(Student.class));
        verify(consoleHandlerMock, times(2)).printMessage(anyString());
    }

    @Test
    void shouldExecuteDeleteStudentById() {

        when(consoleHandlerMock.readUserInputString()).thenReturn("1");

        MenuOptions.DELETE_STUDENT_BY_ID.execute(studentServiceMock, groupServiceMock, courseServiceMock,
                validatorMock, consoleHandlerMock);

        verify(studentServiceMock).deleteById(1);
        verify(consoleHandlerMock).printMessage(anyString());
    }

    @Test
    void shouldExecuteAddStudentToCourse() {

        when(consoleHandlerMock.readUserInputNumber()).thenReturn(1L, 1L);
        when(studentServiceMock.findById(1L)).thenReturn(Optional.ofNullable(Student.builder().build()));

        List<Course> courses = new ArrayList<>();
        courses.add(Course.builder().withCourseId(1).withCourseName("Math").build());
        courses.add(Course.builder().withCourseId(2).withCourseName("Physics").build());
        courses.add(Course.builder().withCourseId(3).withCourseName("Chemistry").build());

        when(courseServiceMock.findAllCourses()).thenReturn(courses);

        MenuOptions.ADD_STUDENT_TO_COURSE.execute(studentServiceMock, groupServiceMock, courseServiceMock,
                validatorMock, consoleHandlerMock);

        InOrder inOrder = inOrder(consoleHandlerMock);
        inOrder.verify(consoleHandlerMock).printMessage("Enter student ID: ");
        inOrder.verify(consoleHandlerMock).printMessage("Available courses: ");
        for (Course course : courses) {
            inOrder.verify(consoleHandlerMock).printMessage(course.toString());
        }
        inOrder.verify(consoleHandlerMock).printMessage("\nEnter course ID: ");

        verify(studentServiceMock).addStudentToCourse(anyLong(), eq(1L));
    }

    @Test
    void shouldExecuteRemoveStudentFromCourse() {

        when(consoleHandlerMock.readUserInputNumber()).thenReturn(1L, 1L);

        MenuOptions.REMOVE_STUDENT_FROM_COURSE.execute(studentServiceMock, groupServiceMock, courseServiceMock,
                validatorMock, consoleHandlerMock);

        verify(studentServiceMock).removeStudentFromCourse(1L, 1L);
        verify(consoleHandlerMock, times(2)).printMessage(anyString());
    }

    @Test
    void shouldExecuteFindGroupsWithLessOrEqualStudents() {

        when(consoleHandlerMock.readUserInputNumber()).thenReturn(10L);

        List<Group> groups = new ArrayList<>();
        groups.add(Group.builder()
                .withGroupId(1)
                .withGroupName("A1")
                .build());
        groups.add(Group.builder()
                .withGroupId(2)
                .withGroupName("B1")
                .build());
        groups.add(Group.builder()
                .withGroupId(3)
                .withGroupName("C1")
                .build());

        when(groupServiceMock.findGroupsWithLessOrEqualStudents(10)).thenReturn(groups);

        MenuOptions.FIND_GROUPS_WITH_LESS_OR_EQUAL_STUDENTS.execute(studentServiceMock, groupServiceMock, courseServiceMock,
                validatorMock, consoleHandlerMock);

        InOrder inOrder = inOrder(consoleHandlerMock);
        inOrder.verify(consoleHandlerMock).printMessage("Enter value: ");
        for (Group group : groups) {
            inOrder.verify(consoleHandlerMock).printMessage(group.toString());
        }
    }

    @Test
    void getCodeShouldGetCode() {

        assertEquals("a", MenuOptions.FIND_GROUPS_WITH_LESS_OR_EQUAL_STUDENTS.getCode());
        assertEquals("b", MenuOptions.FIND_STUDENTS_BY_COURSE.getCode());
        assertEquals("c", MenuOptions.ADD_NEW_STUDENT.getCode());
        assertEquals("d", MenuOptions.DELETE_STUDENT_BY_ID.getCode());
        assertEquals("e", MenuOptions.ADD_STUDENT_TO_COURSE.getCode());
        assertEquals("f", MenuOptions.REMOVE_STUDENT_FROM_COURSE.getCode());
    }

    @Test
    void getDescriptionShouldGetDescription() {

        assertEquals("Find all groups with less or equal students’ number", MenuOptions.FIND_GROUPS_WITH_LESS_OR_EQUAL_STUDENTS.getDescription());
        assertEquals("Find all students related to the course with the given name", MenuOptions.FIND_STUDENTS_BY_COURSE.getDescription());
        assertEquals("Add a new student", MenuOptions.ADD_NEW_STUDENT.getDescription());
        assertEquals("Delete a student by the STUDENT_ID", MenuOptions.DELETE_STUDENT_BY_ID.getDescription());
        assertEquals("Add a student to the course (from a list)", MenuOptions.ADD_STUDENT_TO_COURSE.getDescription());
        assertEquals("Remove the student from one of their courses", MenuOptions.REMOVE_STUDENT_FROM_COURSE.getDescription());
    }
}
