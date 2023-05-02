package com.foxminded.chendev.schoolconsoleapp.datagenerator.impl;

import com.foxminded.chendev.schoolconsoleapp.datagenerator.DataGenerator;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.repository.GroupRepository;
import com.foxminded.chendev.schoolconsoleapp.repository.StudentRepository;
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
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final int amountOfStudents;

    public StudentDataGenerator(StudentRepository studentRepository, GroupRepository groupRepository, int amountOfStudents) {

        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.amountOfStudents = amountOfStudents;
    }

    @Override
    public void generateData() {

        List<Group> groups = groupRepository.findAll();

        for (int i = 0; i < amountOfStudents; i++) {
            String firstName = generateFirstName();
            String lastName = generateLastName();

            Student student = Student.builder()
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .build();

            if(!groups.isEmpty()) {
                int groupId = getRandomGroup(groups);
                student.setGroupId(groupId);
            }

            studentRepository.save(student);
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
