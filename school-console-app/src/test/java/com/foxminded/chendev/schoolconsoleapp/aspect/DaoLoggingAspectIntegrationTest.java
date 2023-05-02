package com.foxminded.chendev.schoolconsoleapp.aspect;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.StudentDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DaoLoggingAspectIntegrationTest {

    private Logger studentDaoLogger;
    private ListAppender<ILoggingEvent> listAppender;

    @Autowired
    private StudentDao studentDao;

    @BeforeEach
    void setUp() {
        studentDaoLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(StudentDaoImpl.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        studentDaoLogger.addAppender(listAppender);
    }

    @AfterEach
    void tearDown() {
        studentDaoLogger.detachAppender(listAppender);
    }

    @Test
    void testLoggingAspect() {
        Student student = Student.builder()
                .withFirstName("Jaze")
                .withLastName("Hezi")
                .build();

        studentDao.save(student);

        assertThat(listAppender.list).hasSizeGreaterThanOrEqualTo(2);
        assertThat(listAppender.list.get(0).getLevel()).isEqualTo(Level.INFO);
        assertThat(listAppender.list.get(1).getLevel()).isEqualTo(Level.INFO);
    }
}
