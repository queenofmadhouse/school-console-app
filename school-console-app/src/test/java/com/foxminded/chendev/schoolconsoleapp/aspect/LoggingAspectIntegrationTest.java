package com.foxminded.chendev.schoolconsoleapp.aspect;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LoggingAspectIntegrationTest {

    private Logger studentRepositorylogger;
    private ListAppender<ILoggingEvent> listAppender;

    @SpyBean
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepositorylogger = (ch.qos.logback.classic.Logger) LoggerFactory
                .getLogger(StudentRepository.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        studentRepositorylogger.addAppender(listAppender);
    }

    @AfterEach
    void tearDown() {
        studentRepositorylogger.detachAppender(listAppender);
    }

    @Test
    void testLoggingAspect() {

        Student student = Student.builder()
                .withFirstName("Jaze")
                .withLastName("Hezi")
                .build();

        studentRepository.save(student);

        assertThat(listAppender.list).hasSizeGreaterThanOrEqualTo(2);
        assertThat(listAppender.list.get(0).getLevel()).isEqualTo(Level.INFO);
        assertThat(listAppender.list.get(1).getLevel()).isEqualTo(Level.INFO);
    }

    @Test
    void exception() {

        doThrow(new RuntimeException()).when(studentRepository).deleteByUserId(1);
        assertThrows(RuntimeException.class, () -> studentRepository.deleteByUserId(1));

        assertThat(listAppender.list).hasSizeGreaterThanOrEqualTo(1);
        assertThat(listAppender.list.get(3).getLevel()).isEqualTo(Level.ERROR);
    }
}
