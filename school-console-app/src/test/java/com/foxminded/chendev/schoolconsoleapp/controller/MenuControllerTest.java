package com.foxminded.chendev.schoolconsoleapp.controller;

import com.foxminded.chendev.schoolconsoleapp.controller.validator.Validator;
import com.foxminded.chendev.schoolconsoleapp.service.CourseService;
import com.foxminded.chendev.schoolconsoleapp.service.GroupService;
import com.foxminded.chendev.schoolconsoleapp.service.StudentService;
import com.foxminded.chendev.schoolconsoleapp.view.ConsoleHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuControllerTest {

    @Mock
    private StudentService studentService;

    @Mock
    private GroupService groupService;

    @Mock
    private CourseService courseService;

    @Mock
    private Validator validator;

    @Mock
    private ConsoleHandler consoleHandler;

    @InjectMocks
    private MenuController menuController;

    @Test
    void provideMenuShouldProvideMenuWhenInputValid() {

        when(consoleHandler.readUserInputString())
                .thenReturn("c")
                .thenReturn("Jane")
                .thenReturn("Testova")
                .thenReturn("exit");

        menuController.provideMenu();

        verify(consoleHandler, times(4)).readUserInputString();
    }

    @Test
    void provideMenuShouldCatchExceptionWhenInputInvalid() {
        
        when(consoleHandler.readUserInputString())
                .thenReturn("invalid_input")
                .thenReturn("exit");

        menuController.provideMenu();

        verify(consoleHandler, times(2)).readUserInputString();
    }
}
