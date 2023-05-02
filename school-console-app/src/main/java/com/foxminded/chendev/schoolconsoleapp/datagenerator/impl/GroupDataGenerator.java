package com.foxminded.chendev.schoolconsoleapp.datagenerator.impl;

import com.foxminded.chendev.schoolconsoleapp.datagenerator.DataGenerator;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GroupDataGenerator implements DataGenerator {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int ALPHABET_SIZE = ALPHABET.length();
    private final Random random = new Random();
    private final GroupRepository groupRepository;
    private final int amountOfGroups;

    public GroupDataGenerator(GroupRepository groupRepository, int amountOfGroups) {

        this.groupRepository = groupRepository;
        this.amountOfGroups = amountOfGroups;
    }

    @Override
    public void generateData() {

        for (int i = 0; i < amountOfGroups; i++) {

            String groupName = generateGroupName();
            Group group = Group.builder()
                    .withGroupName(groupName)
                    .build();

            groupRepository.save(group);
        }

    }

    private String generateGroupName() {

        char character1 = ALPHABET.charAt(random.nextInt(ALPHABET_SIZE));
        char character2 = ALPHABET.charAt(random.nextInt(ALPHABET_SIZE));
        int number1 = random.nextInt(10);
        int number2 = random.nextInt(10);

        return String.format("%c%c-%d%d", character1, character2, number1, number2);
    }
}
