package com.foxminded.chendev.schoolconsoleapp.datagenerator.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.impl.GroupDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.StudentDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.datagenerator.DataGenerator;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class StudentDataGenerator implements DataGenerator {
    private static final String[] FIRST_NAMES = {
            "John", "Michael", "Emma", "Olivia", "Sophia",
            "Mia", "Charlotte", "Amelia", "Harper", "Evelyn",
            "Abigail", "Emily", "Avery", "Ella", "Scarlett",
            "Grace", "Chloe", "Victoria", "Riley", "Aria"
    };

    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Williams", "Brown", "Jones",
            "Garcia", "Miller", "Davis", "Rodriguez", "Martinez",
            "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson",
            "Thomas", "Taylor", "Moore", "Jackson", "Martin"
    };
    private static final Random random = new Random();
    private final StudentDaoImpl studentDao;
    private final GroupDaoImpl groupDao;
    private final int amountOfStudents;

    public StudentDataGenerator(StudentDaoImpl studentDao, GroupDaoImpl groupDao, int amountOfStudents) {
        this.studentDao = studentDao;
        this.groupDao = groupDao;
        this.amountOfStudents = amountOfStudents;
    }

    @Override
    public void generateData() {
        List<Group> groups = groupDao.findAll();

        for (int i = 0; i < amountOfStudents; i++) {
            String firstName = generateFirstName();
            String lastName = generateLastName();
            int groupId = getRandomGroup(groups);

            Student student = Student.builder()
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withGroupId(groupId)
                    .build();

            studentDao.save(student);
        }

    }

    private String generateFirstName() {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
    }

    private String generateLastName() {
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }

    private int getRandomGroup(List<Group> groups) {
        return random.nextInt(groups.size());
    }
}
